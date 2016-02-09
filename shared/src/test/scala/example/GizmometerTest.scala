package example

import scalaunit._
import scalaunit.matchers._

class GizmometerTest extends Test {

  test("passes") = {
    assert(true)
  }

  test("assertions using a matcher") = {
    assertThat(5 + 5, equalTo(9))
  }

  test("assertions using a boolean condition") = {
    assert(false)
  }

  test("unexpected exceptions result in an error") = {
    throw new RuntimeException("Bad bad test")
  }
}
