package scalaunit.framework

import scala.scalajs.js.annotation.JSExportDescendentClasses

@JSExportDescendentClasses
abstract class Test {
  private[scalaunit] val tests = new scala.collection.mutable.ListBuffer[TestCase]

  lazy val test = new TestSpecifier()

  final class TestSpecifier() {
    def update(name: String, body: => Unit) = tests += new TestCase(name, body)
  }
}

final class TestCase(val name: String, body: => Unit) {
  def run() = body
}

case class AssertionContext(fileName: String, lineNumber: Int, line: String)
case class AssertionFailure(context: AssertionContext, message: String) extends AssertionError(message)

sealed trait Result {
  val name: String
}
case class Success(name: String, durationMs: Long) extends Result
case class Failure(name: String, durationMs: Long, failure: AssertionFailure) extends Result
case class Error(name: String, durationMs: Long, error: Throwable) extends Result