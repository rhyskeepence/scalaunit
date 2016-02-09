package scalaunit.framework

abstract class TestLogger {
  def testBegin(test: Test): Unit
  def testCaseComplete(test: Test, result: Result): Unit
}
