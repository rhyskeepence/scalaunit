lazy val buildSettings = Seq(
	organization := "org.scalaunit",
	version := "0.1.1",
	name := "scalaunit",

  scalaVersion := "2.11.7",
	scalacOptions ++= commonScalacOptions,

  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
)

lazy val jsSettings = Seq(
  scalaJSStage in Test := FastOptStage,
  testFrameworks += new TestFramework("scalaunit.runner.Framework"),
  libraryDependencies ++= Seq(
    "org.scala-js"    %% "scalajs-test-interface"     % scalaJSVersion
  )
)

lazy val jvmSettings = Seq(
  testFrameworks += new TestFramework("scalaunit.runner.Framework"),
  libraryDependencies ++= Seq(
    "org.scala-sbt"   %  "test-interface"             % "1.0",
    "org.scala-js"    %% "scalajs-stubs"              % scalaJSVersion % "provided"
  )
)

lazy val scalaunit = crossProject
  .settings(buildSettings:_*)
  .jvmSettings(jvmSettings:_*)
  .jsSettings(jsSettings:_*)

lazy val jvm = scalaunit.jvm.in(file("jvm"))

lazy val js = scalaunit.js.in(file("js"))

lazy val commonScalacOptions = Seq(
	"-deprecation",
  "-encoding", "UTF-8",
	"-feature",
	"-language:experimental.macros",
	"-unchecked",
	"-Xfatal-warnings",
	"-Xlint",
	"-Yinline-warnings",
	"-Yno-adapted-args",
	"-Ywarn-dead-code",
	"-Ywarn-numeric-widen",
	"-Ywarn-unused-import"
//  ,
//	"-Ywarn-value-discard"
)
