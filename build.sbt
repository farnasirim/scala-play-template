name := """pesteh"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.11",
  "de.svenkubiak" % "jBCrypt" % "0.4",
  "io.john-ky" %% "hashids-scala" % "1.1.1-7d841a8",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "dl-john-ky" at "http://dl.john-ky.io/maven/releases"
)
