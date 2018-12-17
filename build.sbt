import Dependencies._
import sbt.Keys.libraryDependencies

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.yannmoisan",
      crossScalaVersions := Seq("2.11.11", "2.12.8"),
      version := "0.1.0-SNAPSHOT"
    )),
  scalafmtOnCompile := true,
  name := "graphite4s",
  scalacOptions += "-Ypartial-unification",
  libraryDependencies ++= Seq(
    catsEffect,
    scalaLogging,
    scalaTest % Test
  )
)
