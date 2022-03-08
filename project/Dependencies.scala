import sbt._

object Dependencies {
  lazy val scalaTest    = "org.scalatest"              %% "scalatest"          % "3.2.11"
  lazy val catsEffect   = "org.typelevel"              %% "cats-effect-kernel" % "3.3.7"
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging"      % "3.9.4"
}
