import Dependencies._
import sbt.Keys._

organization := "com.yannmoisan"
version := "0.1.0-SNAPSHOT"
crossScalaVersions := Seq("2.11.11", "2.12.8")

scalacOptions += "-Ypartial-unification"
scalafmtOnCompile := true

libraryDependencies ++= Seq(
  catsEffect,
  scalaLogging,
  scalaTest % Test
)
