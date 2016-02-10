import scalaunit.framework._
import scalaunit.matchers._

package object scalaunit {

  type Test = framework.Test
  type AssertionFailure = framework.AssertionFailure


  def fail(message: String) = throw new TestFailure(message)

  def assert(expr: Boolean): Unit = macro assertion.Assert.assert

  // TODO variance
  def assertThat[A](expr: A, matcher: Matcher[A]): Unit = macro assertion.Assert.assertThat[A]



  def run(test: Test, logger: TestLogger): List[Result] = {

    logger.testBegin(test)

    test._tests.toList.map(testcase => {
      val timer = Timer.start()

      val result = try {
        testcase.run()
        Success(testcase.name, timer.elapsedMs)

      } catch {
        case TestFailure(message) =>
          Failure(testcase.name, timer.elapsedMs, None, message)

        case AssertionFailure(context, message) =>
          Failure(testcase.name, timer.elapsedMs, Some(context), message)

        case throwable: Throwable =>
          Error(testcase.name, timer.elapsedMs, throwable)
      }

      logger.testCaseComplete(test, result)
      result

    })
  }

}
