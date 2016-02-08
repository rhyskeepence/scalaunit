lazy val buildSettings = Seq(
	organization := "com.rhyskeepence",
	version := "0.1.1",

  scalaVersion := "2.11.7",
	scalacOptions ++= commonScalacOptions,

	testFrameworks += new TestFramework("scalaunit.runner.Framework")
)

lazy val scalaunit = crossProject.crossType(CrossType.Pure)
	.settings(moduleName := "scalaunit")
	.settings(buildSettings:_*)

lazy val scalaunitJVM = scalaunit.jvm
lazy val scalaunitJS = scalaunit.js

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
	"-Ywarn-unused-import",
	"-Ywarn-value-discard"
)
