import sbt._,Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.{scalaJSSettings, scalaJSVersion}

object Build extends Build {
  val htmldaVersion = "0.1.1"
  val htmldaJsVersion = s"$htmldaVersion-JS"

  val baseSettings = Seq(
    scalaVersion := "2.10.3",
    organization := "com.github.hexx",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    initialCommands in console += "import com.github.hexx.htmlda._"
  )

  val jvmSettings = baseSettings ++ Seq(
    libraryDependencies += "org.scalatest" %% "scalatest" % "2.0" % "test",
    version := htmldaVersion
  )

  val jsSettings = baseSettings ++ scalaJSSettings ++ Seq(
    version := htmldaJsVersion
  )

  lazy val htmlda = project.in(file(".")).settings(
    jvmSettings ++ Seq(
      name := "htmlda",
      unmanagedSourceDirectories in Compile <+= baseDirectory(_ / "shared" / "main" / "scala")
    ) : _*
  )

  lazy val playHtmlda = project.in(file("play-htmlda")).settings(
    jvmSettings ++ Seq(
      name := "play-htmlda",
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play" % "2.2.1"
      ),
      libraryDependencies <+= version("com.github.hexx" %% "htmlda" % _)
    ) : _*
  ).dependsOn(htmlda)

  lazy val unfilteredHtmlda = project.in(file("unfiltered-htmlda")).settings(
    jvmSettings ++ Seq(
      name := "unfiltered-htmlda",
      libraryDependencies ++= Seq(
        "net.databinder" %% "unfiltered" % "0.7.1"
      ),
      libraryDependencies <+= version("com.github.hexx" %% "htmlda" % _)
    ) : _*
  ).dependsOn(htmlda)

  lazy val unfilteredHtmldaSample = project.in(file("unfiltered-htmlda-sample")).settings(
    jvmSettings ++ Seq(
      libraryDependencies ++= Seq(
        "net.databinder" %% "unfiltered-filter" % "0.7.1",
        "net.databinder" %% "unfiltered-jetty" % "0.7.1"
      ),
      libraryDependencies <+= version("com.github.hexx" %% "unfiltered-htmlda" % _)
    ) : _*
  ).dependsOn(unfilteredHtmlda)

  lazy val htmldaJs = project.in(file("js")).settings(
    jsSettings ++ Seq(
      name := "htmlda",
      unmanagedSourceDirectories in Compile <+= baseDirectory(_ / ".." / "shared" / "main" / "scala"),
      libraryDependencies ++= Seq(
        "com.lihaoyi.utest" % "utest_2.10" % "0.1.1" % "test",
        "com.lihaoyi.utest" % "utest-runner_2.10" % "0.1.1" % "test",
        "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test",
        "org.webjars" % "jquery" % "2.1.0-2" % "test",
        "org.webjars" % "envjs" % "1.2" % "test"
      )
    ) : _*
  )
}
