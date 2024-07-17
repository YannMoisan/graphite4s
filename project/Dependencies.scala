import sbt._

object Dependencies {
  lazy val scalaTest        = "org.scalatest"              %% "scalatest"          % "3.2.15"
  lazy val catsEffectKernel = "org.typelevel"              %% "cats-effect-kernel" % "3.4.11"
  lazy val catsEffect       = "org.typelevel"              %% "cats-effect"        % "3.4.11"
  lazy val scalaLogging     = "com.typesafe.scala-logging" %% "scala-logging"      % "3.9.5"
}
