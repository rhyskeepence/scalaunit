package scalaunit.matchers

import scalaunit._
import scalaunit.framework.AssertionFailure

class MatchersTest {

  @Test def equalToMatches() = {
    assertThat(1 + 1, equalTo(2))
  }

  @Test def equalToDoesNotMatch() = {
    try assertThat(1 + 2, equalTo(1))
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: 1")
        assert(message contains "but: was 3")
    }
  }

  @Test def notEqualToMatches() = {
    assertThat(1 + 1, not(equalTo(3)))
  }

  @Test def notEqualToDoesNotMatch() = {
    try assertThat(1 + 2, not(equalTo(3)))
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: not 3")
        assert(message contains "but: was 3")
    }
  }

  @Test def allOfMatches() = {
    assertThat(1 + 1, allOf(equalTo(2)))
    assertThat(1 + 1, allOf(List(equalTo(2))))
  }

  @Test def allOfDoesNotMatch() = {
    try assertThat(1 + 1, allOf(equalTo(1), equalTo(2)))
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: 1 and 2")
        assert(message contains "but: was 2")
    }
  }

  @Test def anyOfMatches() = {
    assertThat(1 + 1, anyOf(equalTo(2), equalTo(3)))
    assertThat(1 + 1, anyOf(List(equalTo(2), equalTo(3))))
  }

  @Test def anyOfDoesNotMatch() = {
    try assertThat(1 + 1, anyOf(equalTo(1), equalTo(3)))
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: 1 or 3")
        assert(message contains "but: was 2")
    }
  }

  @Test def isTrueMatches() = {
    assertThat(true, isTrue)
  }

  @Test def isTrueDoesNotMatch() = {
    try assertThat(false, isTrue)
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: is true")
        assert(message contains "but: was false")
    }
  }

  @Test def isFalseMatches() = {
    assertThat(false, isFalse)
  }

  @Test def isFalseDoesNotMatch() = {
    try assertThat(true, isFalse)
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: is false")
        assert(message contains "but: was true")
    }
  }

}
