package example

import scalaunit._
import scalaunit.matchers._

class GizmometerTest extends Test {

  test("passes") = {
    assert(true)
  }

  test("triggered failure") = {
    fail("Oh no.")
  }

  test("assertions using a matcher") = {
    assertThat(5 + 5, equalTo(9))
  }

  test("assertions using a negated matcher") = {
    assertThat(5 + 5, not(equalTo(10)))
  }

  test("assertions using a boolean condition") = {
    assert(false)
  }

  test("unexpected exceptions result in an error") = {
    throw new RuntimeException("Bad bad test")
  }
}
