import scalaunit.framework._
import scalaunit.matchers._

package object scalaunit {

  type Test = framework.Test
  type AssertionFailure = framework.AssertionFailure

  object m extends matchers.Matchers

  def assert(expr: Boolean): Unit = macro assertion.Assert.assert

  def assertThat[A](expr: A, matcher: Matcher[A]): Unit = macro assertion.Assert.assertThat[A]



  def run(test: Test, logger: TestLogger): List[Result] = {

    logger.testBegin(test)

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

      logger.testCaseComplete(test, result)
      result

    })
  }

}
