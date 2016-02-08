package scalaunit.runner

import java.io.{StringWriter, PrintWriter}
import java.util.concurrent.atomic.AtomicInteger

import org.scalajs.testinterface.TestUtils
import sbt.testing._

import scalaunit._
import scalaunit.framework._

final class Framework extends sbt.testing.Framework {
  override def fingerprints(): Array[Fingerprint] = Array(
    new SubclassFingerprint {
      override def requireNoArgConstructor(): Boolean = true
      override def isModule: Boolean = false
      override def superclassName(): String = "scalaunit.framework.Test"
    }
  )

  override def name(): String =
    "ScalaUnit"

  override def runner(args: Array[String], remoteArgs: Array[String], classLoader: ClassLoader): Runner =
    new ScalaUnitMasterRunner(args, remoteArgs, classLoader)

  def slaveRunner(args: Array[String], remoteArgs: Array[String], classLoader: ClassLoader, send: String => Unit): Runner =
    new ScalaUnitSlaveRunner(args, remoteArgs, classLoader, send)
}


abstract class ScalaUnitRunner extends sbt.testing.Runner {

  val args: Array[String]
  val remoteArgs: Array[String]
  val classLoader: ClassLoader

  val successCount = new AtomicInteger(0)
  val failureCount = new AtomicInteger(0)
  val errorCount = new AtomicInteger(0)

  def printableNumberOfTests(atomicInteger: AtomicInteger) = atomicInteger.get() match {
    case i if i == 1 => "1 test"
    case i => i.toString + " tests"
  }

  override def tasks(taskDefs: Array[TaskDef]): Array[Task] = taskDefs.map(createTask)

  def createTask(outerTaskDef: TaskDef): sbt.testing.Task = {
    new Task {
      override def taskDef(): TaskDef = outerTaskDef
      override def tags(): Array[String] = Array()
      override def execute(eventHandler: EventHandler, loggers: Array[Logger]): Array[Task] = {
        val name = outerTaskDef.fullyQualifiedName()

        val suite = TestUtils.newInstance(name, classLoader)(Seq.empty[AnyRef]).asInstanceOf[Test]
        scalaunit.run(suite, new SbtTestLogger).map({
          case Success(_, _)    => successCount.incrementAndGet()
          case Failure(_, _, _) => failureCount.incrementAndGet()
          case Error(_, _, _)   => errorCount.incrementAndGet()
        })

        Array()
      }

      def execute(handler: EventHandler, loggers: Array[Logger], continuation: Array[Task] => Unit): Unit =
        continuation(execute(handler,loggers))

    }
  }

  def deserializeTask(task: String, deserializer: String => TaskDef) = {
    val taskDef = deserializer(task)
    createTask(taskDef)
  }

  def serializeTask(task: Task, serializer: TaskDef => String) =
    serializer(task.taskDef)

}

final class ScalaUnitMasterRunner(val args: Array[String],
                                  val remoteArgs: Array[String],
                                  val classLoader: ClassLoader)
                                  extends ScalaUnitRunner {


  def receiveMessage(msg: String): Option[String] = msg.split(",") match {
    case Array("done", s, f, e) =>
      successCount.addAndGet(s.toInt)
      failureCount.addAndGet(f.toInt)
      errorCount.addAndGet(e.toInt)
      None
  }

  override def done(): String = {
    s"${printableNumberOfTests(successCount)} passed, " +
      s"${printableNumberOfTests(failureCount)} failed, " +
      s"${printableNumberOfTests(errorCount)} threw an exception"
  }

}

final class ScalaUnitSlaveRunner(val args: Array[String],
                                 val remoteArgs: Array[String],
                                 val classLoader: ClassLoader,
                                 send: String => Unit)
                                 extends ScalaUnitRunner {

  def receiveMessage(msg: String) = None

  def done = {
    send(s"done,$successCount,$failureCount,$errorCount")
    ""
  }

}

final class SbtTestLogger extends TestLogger {

  override def onComplete(test: Test, result: Result): Unit = result match {
    case Success(name, duration) =>
      println(s"  ${printableName(name)}   ok       [${duration.toString} ms]")

    case Failure(name, duration, failure) =>
      println(s"  ${printableName(name)}   failure  [${duration.toString} ms]")
      println(s"    $failure")

    case Error(name, duration, error) =>
      println(s"  ${printableName(name)}   error    [${duration.toString} ms]")
      println(s"    ${asString(error)}")
  }

  def asString(t: Throwable) = {
    val sw = new StringWriter
    val pw = new PrintWriter(sw)

    t.printStackTrace(pw)
    sw.toString
  }

  // TODO: Recursively call, adding newlines, if the string is longer than 64 characters, to support long names.
  def printableName(name: String): String =
    name
      .replaceAll("\n", " ")
      .padTo(64, ' ')
}
