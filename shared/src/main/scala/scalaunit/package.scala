import scalaunit.framework._
import scalaunit.matchers._

package object scalaunit {

  type AssertionFailure = framework.AssertionFailure

  def fail(message: String): Unit = throw new TestFailure(message)

  def assert(expr: Boolean): Unit = macro assertion.Assert.assert

  // TODO variance
  def assertThat[A](expr: A, matcher: Matcher[A]): Unit = macro assertion.Assert.assertThat[A]


}
