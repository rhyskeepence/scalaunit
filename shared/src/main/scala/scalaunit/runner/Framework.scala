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
        val logger = new SbtTestLogger(loggers)

        scalaunit.run(suite, logger).map({
          case Success(_, _)        => successCount.incrementAndGet()
          case Failure(_, _, _, _)  => failureCount.incrementAndGet()
          case Error(_, _, _)       => errorCount.incrementAndGet()
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
    s"${Console.GREEN}${printableNumberOfTests(successCount)} passed${Console.RESET}, " +
      s"${Console.RED}${printableNumberOfTests(failureCount)} failed${Console.RESET}, " +
      s"${Console.RED}${printableNumberOfTests(errorCount)} threw an exception${Console.RESET}"
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

final class SbtTestLogger(loggers: Array[Logger]) extends TestLogger {

  def log(ansi: String, f: Logger => String => Unit) = {
    loggers.foreach(logger => {
      if (logger.ansiCodesSupported())
        f(logger)(ansi)
      else
        f(logger)(ansi.replaceAll(Console.GREEN, "").replaceAll(Console.RED_B, "").replaceAll(Console.RESET, ""))
    })
  }

  def info(ansi: String) = log(ansi, _.info)
  def warn(ansi: String) = log(ansi, _.warn)
  def logerror(ansi: String) = log(ansi, _.error)

  override def testBegin(test: scalaunit.Test): Unit = {
    info(test.getClass.getName)
  }

  override def testCaseComplete(test: Test, result: Result): Unit = result match {
    case Success(name, duration) =>
      info(s"  ${printableName(name)}   ${Console.GREEN}ok${Console.RESET}       [${duration.toString} ms]")

    case Failure(name, duration, maybeContext, message) =>
      warn(s"  ${printableName(name)}   ${Console.RED_B}failure${Console.RESET}  [${duration.toString} ms]")

      maybeContext.foreach(context =>
        warn(s"    ${context.fileName}:${context.lineNumber}|   ${context.line}")
      )

      warn( "")
      warn(s"    ${message.replaceAll("\n", "\n    ")}")
      warn( "")

    case Error(name, duration, error) =>
      logerror(s" ${printableName(name)}   ${Console.RED_B}error${Console.RESET}    [${duration.toString} ms]")
      logerror(s"   ${asString(error)}")
      logerror( "")
  }

  def asString(t: Throwable) = {
    val sw = new StringWriter
    val pw = new PrintWriter(sw)

    t.printStackTrace(pw)
    sw.toString.replaceAll("\n\\s+", "\n     ")
  }

  // TODO: Recursively call, adding newlines, if the string is longer than the limit, to support long names.
  def printableName(name: String): String =
    name
      .replaceAll("\n", " ")
      .padTo(80, ' ')
}
