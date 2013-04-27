import sbt._,Keys._

object Build extends Build {
  lazy val baseSettings = Seq(
    scalaVersion := "2.10.1",
    organization := "com.github.hexx",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    initialCommands in console += "import com.github.hexx.htmlda._",
    libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test",
    version := "0.1.0"
  )

  lazy val htmlda = Project(
    id = "htmlda",
    base = file(".")
  ).settings(
    baseSettings ++ seq(
      name := "htmlda"
    ) : _*
  )

  lazy val playHtmlda = Project(
    id = "play-htmlda",
    base = file("play-htmlda")
  ).settings(
    baseSettings ++ seq(
      name := "play-htmlda",
      libraryDependencies ++= Seq(
        "play" %% "play" % "2.1.1"
      ),
      libraryDependencies <+= version("com.github.hexx" %% "htmlda" % _)
    ) : _*
  ).dependsOn(htmlda)

  lazy val unfilteredHtmlda = Project(
    id = "unfiltered-htmlda",
    base = file("unfiltered-htmlda")
  ).settings(
    baseSettings ++ seq(
      name := "unfiltered-htmlda",
      libraryDependencies ++= Seq(
        "net.databinder" %% "unfiltered" % "0.6.8"
      ),
      libraryDependencies <+= version("com.github.hexx" %% "htmlda" % _)
    ) : _*
  ).dependsOn(htmlda)

  lazy val unfilteredHtmldaSample = Project(
    id = "unfiltered-htmlda-sample",
    base = file("unfiltered-htmlda-sample")
  ).settings(
    baseSettings ++ seq(
      libraryDependencies ++= Seq(
        "net.databinder" %% "unfiltered-filter" % "0.6.8",
        "net.databinder" %% "unfiltered-jetty" % "0.6.8"
      ),
      libraryDependencies <+= version("com.github.hexx" %% "unfiltered-htmlda" % _)
    ) : _*
  ).dependsOn(unfilteredHtmlda)
}
