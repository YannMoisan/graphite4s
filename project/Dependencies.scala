import sbt._

object Dependencies {
  lazy val scalaTest    = "org.scalatest"              %% "scalatest"          % "3.2.12"
  lazy val catsEffect   = "org.typelevel"              %% "cats-effect-kernel" % "3.3.5"
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging"      % "3.9.4"
}
