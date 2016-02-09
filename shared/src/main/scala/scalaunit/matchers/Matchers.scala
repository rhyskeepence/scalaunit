package scalaunit.matchers

trait Matchers {

  def equalTo[A](expected: A) = new Matcher[A] {
    def description: String = expected.toString

    def matches(actual: A) =
      if (expected == actual) ok()
      else mismatch(s"was ${actual.toString}")
  }

}
