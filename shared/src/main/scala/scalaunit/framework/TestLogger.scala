package scalaunit.framework

abstract class TestLogger {
  def onComplete(test: Test, result: Result): Unit
}
