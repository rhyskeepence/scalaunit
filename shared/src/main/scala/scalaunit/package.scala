import scalaunit.framework._

package object scalaunit {

  type Test = framework.Test
  type AssertionFailure = framework.AssertionFailure

  def assert(expr: Boolean): Unit = macro assertion.Assert.assert



  def run(test: Test, logger: TestLogger): List[Result] = {

    test.tests.toList.map(testcase => {
      val timer = Timer.start()

      val result = try {
        testcase.run()
        Success(testcase.name, timer.elapsedMs)

      } catch {
        case failure: AssertionFailure =>
          Failure(testcase.name, timer.elapsedMs, failure)

        case throwable: Throwable =>
          Error(testcase.name, timer.elapsedMs, throwable)
      }

      logger.onComplete(test, result)
      result

    })
  }

}
