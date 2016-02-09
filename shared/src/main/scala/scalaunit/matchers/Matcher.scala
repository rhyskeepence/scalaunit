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

  def description: String
  def matches(item: A): Matcher.MatchResult

  /**
    * Convenience method to return a MatchResult indicating a successful Match
    */
  protected def ok() = Matcher.Match

  /**
    * Convenience method to return a MatchResult indicating a mismatch.
    *
    * @param description A description of why the matcher did not match. This will be built up to be
    *                    part of a larger description, and should be concise.
    */
  protected def mismatch(description: String) = Matcher.Mismatch(description)

}
