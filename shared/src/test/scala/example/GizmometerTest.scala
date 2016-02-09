package example

import scalaunit._

class GizmometerTest extends Test {

  test("Do the thing") = {
    println("yay!")
  }

  test("Matchers") = {
    assertThat(5 + 5, m.equalTo(9))
  }

  test("Oh NO!!") = {
    assert(false)

//    throw new framework.AssertionFailure("doesn't pass sorry")
  }

  test("Random exception test") = {
    throw new RuntimeException("Bad bad test")
  }
}
