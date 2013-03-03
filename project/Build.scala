import sbt._,Keys._

object Build extends Build {
  lazy val baseSettings = Seq(
    scalaVersion := "2.10.1-RC2",
    organization := "com.github.hexx",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    initialCommands in console += "import com.github.hexx.htmlda._",
    libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"
  )

  lazy val htmlda = Project(
    id = "htmlda",
    base = file(".")
  ).settings(
    baseSettings ++ seq(
      name := "htmlda",
      version := "0.0.1"
    ) : _*
  )

  lazy val playHtmlda = Project(
    id = "play-htmlda",
    base = file("play-htmlda")
  ).settings(
    baseSettings ++ seq(
      name := "play-htmlda",
      version := "0.0.1",
      libraryDependencies ++= Seq(
        "com.github.hexx" %% "htmlda" % "0.0.1",
        "play" %% "play" % "2.1.0"
      )
    ) : _*
  ).dependsOn(htmlda)
}
