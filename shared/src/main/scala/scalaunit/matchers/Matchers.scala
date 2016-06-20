package scalaunit

/**
  * This package contains the most common Matchers for use with ScalaUnit.
  */
package object matchers {

  /**
    * A Matcher that matches when the expected value is equal to the value being matched.
    */
  def equalTo[A](expected: A): Matcher[A]  = new Matcher[A] {
    def matches(actual: A) = expected == actual
    def description: String = expected.toString
  }

  /**
    * A Matcher that inverts the given matcher.
    */
  def not[A](matcher: Matcher[A]): Matcher[A]  = new Matcher[A] {
    override def matches(item: A) = !matcher.matches(item)
    override def description = "not " + matcher.description
  }

  /**
    * A Matcher that matches if the value matches all of the given Matchers.
    */
  def allOf[A](matchers: Matcher[A]*): Matcher[A]  = new Matcher[A] {
    override def matches(item: A): Boolean = matchers.forall(_.matches(item))
    override def description: String = matchers.map(_.description).mkString(" and ")
  }

  /**
    * A Matcher that matches if the value matches all of the given Matchers.
    */
  def allOf[A](matchers: List[Matcher[A]]): Matcher[A] =
    allOf(matchers: _*)

  /**
    * A Matcher that matches if the value matches any of the given Matchers.
    */
  def anyOf[A](matchers: Matcher[A]*): Matcher[A]  = new Matcher[A] {
    override def matches(item: A): Boolean = matchers.exists(_.matches(item))
    override def description: String = matchers.map(_.description).mkString(" or ")
  }

  /**
    * A Matcher that matches if the value matches any of the given Matchers.
    */
  def anyOf[A](matchers: List[Matcher[A]]): Matcher[A] =
    anyOf(matchers: _*)

  /**
    * A Matcher that matches the Boolean value {{{true}}}
    */
  def isTrue: Matcher[Boolean] = new Matcher[Boolean] {
    override def matches(item: Boolean) = item
    override def description: String = "is true"
  }

  /**
    * A Matcher that matches the Boolean value {{{false}}}
    */
  def isFalse: Matcher[Boolean] = new Matcher[Boolean] {
    override def matches(item: Boolean) = !item
    override def description: String = "is false"
  }

}
