package scalaunit.matchers


object Matcher {
  sealed trait MatchResult
  case object Match extends MatchResult
  case class Mismatch(description: String) extends MatchResult
}

/**
  * A matcher that can decide if a value is acceptable, and if not, returns a description of the mismatch.
  */
abstract class Matcher[A] {

  /**
    * Decide if a value is an acceptable match
    *
    * @param item The value to check
    * @return true if the value matches, false otherwise
    */
  def matches(item: A): Boolean

  /**
    * A description of why the matcher did not match. This will be built up to be
    * part of a larger description, and should be concise.
    *
    * @param item The item that did not match
    */
  def describeMismatch(item: A): String = "was " + item.toString

  /**
    * A description of this matcher. This will be built up to be part of a
    * larger description, and should be concise.
    */
  def description: String

}
