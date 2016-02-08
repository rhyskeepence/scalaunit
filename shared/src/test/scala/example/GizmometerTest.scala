package example

import scalaunit._

class GizmometerTest extends Test {

  test("Do the thing") = {
    println("yay!")
  }

  test("Oh NO!!") = {
    throw new framework.AssertionFailure("doesn't pass sorry")
  }

  test("Random exception test") = {
    throw new RuntimeException("Bad bad test")
  }
}
