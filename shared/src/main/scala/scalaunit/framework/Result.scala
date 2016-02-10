package scalaunit.framework

sealed trait Result {
  val name: String
}
case class Success(name: String, durationMs: Long) extends Result
case class Failure(name: String, durationMs: Long, context: Option[AssertionContext], message: String) extends Result
case class Error(name: String, durationMs: Long, error: Throwable) extends Result

case class AssertionContext(fileName: String, lineNumber: Int, line: String)
case class AssertionFailure(context: AssertionContext, message: String) extends AssertionError(message)
case class TestFailure(message: String) extends AssertionError(message)

