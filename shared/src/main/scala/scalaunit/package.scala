import java.lang.annotation.Annotation

import org.junit.Test

import scalaunit.framework._
import scalaunit.matchers._

package object scalaunit {

  type AssertionFailure = framework.AssertionFailure

  class Test(val expected: Class[_ <: Throwable],
             val timeout: Long) extends org.junit.Test with scala.annotation.StaticAnnotation {

    def this(expected: Class[_ <: Throwable]) = this(expected, 0L)
    def this(timeout: Long) = this(classOf[Test.None], timeout)
    def this() = this(0L)

    def annotationType(): Class[_ <: Annotation] =
      classOf[Test]
  }

  def fail(message: String): Unit = throw new TestFailure(message)

  def assert(expr: Boolean): Unit = macro assertion.Assert.assert

  // TODO variance
  def assertThat[A](expr: A, matcher: Matcher[A]): Unit = macro assertion.Assert.assertThat[A]


}
