package scalaunit

package object matchers {

  def equalTo[A](expected: A) = new Matcher[A] {
    def matches(actual: A) = expected == actual
    def description: String = expected.toString
  }

  def not[A](matcher: Matcher[A]) = new Matcher[A] {
    override def matches(item: A) = !matcher.matches(item)
    override def description = "not " + matcher.description
  }

  def allOf[A](matchers: Matcher[A]*) = new Matcher[A] {
    override def matches(item: A): Boolean = matchers.forall(_.matches(item))
    override def description: String = matchers.map(_.description).mkString(" and ")
  }

}
