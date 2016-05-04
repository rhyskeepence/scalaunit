package scalaunit.matchers

import scalaunit._
import scalaunit.framework.AssertionFailure

class MatchersTest extends Test {

  test("equalTo matches") = {
    assertThat(1 + 1, equalTo(2))
  }

  test("equalTo does not match") = {
    try assertThat(1 + 2, equalTo(1))
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: 1")
        assert(message contains "but: was 3")
    }
  }

  test("not(equalTo) matches") = {
    assertThat(1 + 1, not(equalTo(3)))
  }

  test("not(equalTo) does not match") = {
    try assertThat(1 + 2, not(equalTo(3)))
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: not 3")
        assert(message contains "but: was 3")
    }
  }

  test("allOf matches") = {
    assertThat(1 + 1, allOf(equalTo(2)))
    assertThat(1 + 1, allOf(List(equalTo(2))))
  }

  test("allOf does not match") = {
    try assertThat(1 + 1, allOf(equalTo(1), equalTo(2)))
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: 1 and 2")
        assert(message contains "but: was 2")
    }
  }

  test("anyOf matches") = {
    assertThat(1 + 1, anyOf(equalTo(2), equalTo(3)))
    assertThat(1 + 1, anyOf(List(equalTo(2), equalTo(3))))
  }

  test("anyOf does not match") = {
    try assertThat(1 + 1, anyOf(equalTo(1), equalTo(3)))
    catch {
      case AssertionFailure(context, message) =>
        assert(message contains "Expected: 1 or 3")
        assert(message contains "but: was 2")
    }
  }

}
