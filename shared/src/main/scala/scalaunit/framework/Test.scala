package scalaunit.framework

import scala.scalajs.js.annotation.JSExportDescendentClasses

@JSExportDescendentClasses
abstract class Test {
  private[scalaunit] val _tests = new scala.collection.mutable.ListBuffer[TestCase]

  lazy val test = new TestSpecifier()

  final class TestSpecifier() {
    def update(name: String, body: => Unit) = _tests += new TestCase(name, body)
  }
}

final class TestCase(val name: String, body: => Unit) {
  def run() = body
}
