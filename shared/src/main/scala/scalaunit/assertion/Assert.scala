package scalaunit.assertion

import scala.reflect.macros.blackbox.Context
import scalaunit.framework._
import scalaunit.matchers.Matcher

object Assert {

  def assert(c: Context)(expr: c.Expr[Boolean]): c.Expr[Unit] = {
    import c.universe._
    val func = q"scalaunit.assertion.Assert.runAssertion"
    val fileName = expr.tree.pos.source.file.name
    val lineNumber = expr.tree.pos.line
    val line = expr.tree.pos.source.content.mkString.split("\n").drop(lineNumber - 1).headOption.getOrElse("")
    c.Expr[Unit](c.untypecheck(q"""$func(scalaunit.framework.AssertionContext($fileName, $lineNumber, $line), ${expr.tree})"""))
  }


  def runAssertion(context: AssertionContext, assertion: => Boolean): Unit = {
    if (assertion)
      ()
    else throw new AssertionFailure(context, "Expression is not true")
  }


  def assertThat[A](c: Context)(expr: c.Expr[A], matcher: c.Expr[Matcher[A]]): c.Expr[Unit] = {
    import c.universe._
    val func = q"scalaunit.assertion.Assert.runAssertThat"
    val fileName = expr.tree.pos.source.file.name
    val lineNumber = expr.tree.pos.line
    val line = expr.tree.pos.source.content.mkString.split("\n").drop(lineNumber - 1).headOption.getOrElse("")
    c.Expr[Unit](c.untypecheck(q"""$func(scalaunit.framework.AssertionContext($fileName, $lineNumber, $line), ${expr.tree}, ${matcher.tree})"""))
  }


  def runAssertThat[A](context: AssertionContext, item: A, matcher: Matcher[A]): Unit = {
    if (matcher.matches(item))
      ()
    else {
      val message =
        s"Expected: ${matcher.description}\n" +
        s"     but: ${matcher.describeMismatch(item)}"

      throw new AssertionFailure(context, message)
    }
  }


}
