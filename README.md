ScalaUnit is a modern testing framework for Scala.

[![Join the chat at https://gitter.im/rhyskeepence/scalaunit](https://badges.gitter.im/rhyskeepence/scalaunit.svg)](https://gitter.im/rhyskeepence/scalaunit?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Features

- A single way of defining a test (the test method)
- A simple assertion syntax, that doesn't require your tests to be littered with thousands of implicit conversions that slow down IntelliJ and confuse the user
- Matchers use exceptions, rather than return values, to control failure
- An assertion macro, that shows as much context as possible (for example, printing the code exactly as written, rather than the line number or stack trace of the error)
- A hamcrest-inspired matcher framework, that produces helpful error messages designed for humans, allowing you to make the mental leap from **"The test is angry"** to fixing the problem in the shortest space of time.
- Matchers are designed to be composible, while still maintaining human readable messages
- Matchers are type-safe, preventing successful compilation of tests that will never pass
- Tests can be filtered easily using patterns specified from command line arguments
- SBT integration, of course, with a focus on experience. This means succinct output for passing tests and lots of help for failing tests.
- IntelliJ integration
- Allow the combination of ScalaUnit tests, ScalaCheck, and other Scala testing frameworks into a single test suite.


## Example

```
  import scalaunit._
  
  object GizmometerTest extends Test {
  
    test("Addition (using a simple Boolean check)") = {
      assert( (2 + 2) == 5 )
    }
    
    test("Addition (using the equalTo Matcher)") = {
      assert(2 + 2, equalTo(5))
    }

    test("Throws an exception") = {
      throw new RuntimeException("Oops")
    }
    
  }
```

## Output 

(imagine this in full technicolour)

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
    
    
  1 test passed, 1 test failed
```
