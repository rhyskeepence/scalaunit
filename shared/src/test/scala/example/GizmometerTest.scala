package example

import org.junit._
import scalaunit._
import scalaunit.matchers._

class GizmometerTest {

  @Test def passes() = {
    assert(true)
  }

  @Test def triggeredFailure() = {
    fail("Oh no.")
  }

  @Test def assertionsUsingAMatcher() = {
    assertThat(5 + 5, equalTo(9))
  }

  @Test def assertionsUsingANegatedMatcher() = {
    assertThat(5 + 5, not(equalTo(10)))
  }

  @Test def assertionsUsingABooleanCondition() = {
    assert(false)
  }

}
