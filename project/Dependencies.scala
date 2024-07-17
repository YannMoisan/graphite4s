import sbt._

object Dependencies {
  lazy val scalaTest        = "org.scalatest"              %% "scalatest"          % "3.2.19"
  lazy val catsEffectKernel = "org.typelevel"              %% "cats-effect-kernel" % "3.5.4"
  lazy val catsEffect       = "org.typelevel"              %% "cats-effect"        % "3.5.4"
  lazy val scalaLogging     = "com.typesafe.scala-logging" %% "scala-logging"      % "3.9.5"
}
