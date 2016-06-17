package scalaunit.framework

case class AssertionContext(fileName: String, lineNumber: Int, line: String)

case class AssertionFailure(context: AssertionContext, message: String)
  extends AssertionError(s"${context.fileName}:${context.lineNumber}\n${context.line}\n$message")

case class TestFailure(message: String) extends AssertionError(message)

