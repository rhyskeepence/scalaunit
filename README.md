ScalaUnit is a modern testing framework for Scala.

## Features

- A simple assertion syntax, that doesn't require your tests to be littered with thousands of implicit conversions that slow down IntelliJ
- An assertion macro, that shows as much context as possible (printing the code exactly as written, rather than the line number or stack trace of the error)
- A hamcrest-inspired matcher framework, which print helpful error messages, allowing you to make the mental leap from **"The test is angry"** to fixing the problem in the shortest space of time.
- All matchers are type-safe, preventing successful compilation of tests that will never pass.
- Tests can be filtered easily from the command prompt using patterns
- Allow the combination of ScalaUnit tests, ScalaCheck, and other Scala testing frameworks into a single test suite.
- SBT integration, of course, with a focus on experience. This means succinct output for passing tests, lots of help for failing tests, and the use of colour and layout to help.
