import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
  val appName         = "play-htmlda-sample"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.github.hexx" %% "play-htmlda" % "0.0.1",
    jdbc,
    anorm
  )

  lazy val playHtmlda = ProjectRef(base = file(".."), id = "play-htmlda")

  val main = play.Project(appName, appVersion, appDependencies).dependsOn(playHtmlda)
}
