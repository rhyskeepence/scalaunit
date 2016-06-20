lazy val buildSettings = Seq(
	organization := "org.scalaunit",
	version := "0.1.1",
	name := "scalaunit",

  scalaVersion := "2.11.8",
	scalacOptions ++= commonScalacOptions,

  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  ),

  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  publishMavenStyle := false
)

lazy val jsSettings = Seq(
  scalaJSStage in Test := FastOptStage,
  libraryDependencies ++= Seq(
    "org.scala-js"    %% "scalajs-test-interface"     % scalaJSVersion
  )
)

lazy val jvmSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scala-sbt"   %  "test-interface"             % "1.0",
    "org.scala-js"    %% "scalajs-stubs"              % scalaJSVersion  % "provided",
    "com.novocode"    %  "junit-interface"            % "0.11"          % "test"
  )
)

lazy val docSettings = site.settings ++ ghpages.settings ++ site.includeScaladoc() ++ Seq(
  git.remoteRepo := "git@github.com:rhyskeepence/scalaunit.git"
)

lazy val scalaunit = crossProject
  .settings(buildSettings:_*)
  .settings(docSettings:_*)
  .jvmSettings(jvmSettings:_*)
  .jsConfigure(_.enablePlugins(ScalaJSJUnitPlugin))
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
