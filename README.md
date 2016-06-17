ScalaUnit is a test assertion framework for Scala and Scala.JS.

It builds on top of JUnit, and is intended to be used as a lightweight alternative to specs2 and ScalaTest.

[![Join the chat at https://gitter.im/rhyskeepence/scalaunit](https://badges.gitter.im/rhyskeepence/scalaunit.svg)](https://gitter.im/rhyskeepence/scalaunit?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Features

- A simple assertion syntax, that doesn't require tests to be littered with thousands of implicit conversions that slow down IntelliJ and confuse the user
- Exceptions, rather than return values, to control failure, which allows assertions to be used anywhere
- An assertion macro, that shows as much context as possible (for example, printing the code exactly as written, rather than the line number or stack trace of the error)
- A hamcrest-inspired matcher framework, that produces helpful error messages designed for humans, allowing you to make the mental leap from **"The test is angry"** to fixing the problem in the shortest space of time.
- Composable matchers, which maintain human readable messages
- Type-safe matchers, preventing successful compilation of tests that will never pass
- Full IDE integration including the ability to run single tests (because JUnit)

## Example

```
  import org.junit._
  import scalaunit._
  
  object GizmometerTest {
  
    @Test addition() = {
      assert( (2 + 2) == 5 )
    }
    
    @Test additionUsingTheEqualToMatcher() = {
      assert(2 + 2, equalTo(5))
    }

    @Test throwsAnException() = {
      throw new RuntimeException("Oops")
    }

  }
```

## Output 

Notes:
- Context is important, so the assertion failure starts by showing the filename, line number and code that produced the failure.
- Layout makes a big difference. Whitespace is used to improve clarity.
- Colour can be used to make skimming much easier.

```
  GizmometerTest
    Addition (using a simple Boolean check)                            ok       [0 ms]
    Addition (using the equalTo Matcher)                               failure  [0 ms]
    
      Assertion failure:
      GizmometerTest.scala:5|   assert(2 + 2, equalTo(5))
    
      Expected: 
          4
          
      To Be Equal To:
          5
    
    
    Throws an exception                                                error    [0 ms]
      
      An unexpected exception was thrown:
      
         RuntimeException: Oops
         <stack trace>
    
  1 test passed, 1 test failed, 1 test threw an exception
```
