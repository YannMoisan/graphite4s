import Dependencies._
import sbt.Keys._

organization := "com.yannmoisan"
crossScalaVersions := Seq("2.11.11", "2.12.8")

scalacOptions += "-Ypartial-unification"
scalafmtOnCompile := true

libraryDependencies ++= Seq(
  catsEffect,
  scalaLogging,
  scalaTest % Test
)

startYear := Some(2018)
licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/yannmoisan/graphite4s"))
scmInfo := Some(ScmInfo(url("https://github.com/yannmoisan/graphite4s"), "git@github.com:yannmoisan/graphite4s.git"))

publishTo := {
  Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  )
}
publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ =>
  false
}
pomExtra :=
  <developers>
    <developer>
      <id>yannmoisan</id>
      <name>Yann Moisan</name>
      <url>http://github.com/yannmoisan</url>
    </developer>
  </developers>

import sbtrelease.ReleaseStateTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(action = Command.process("publishSigned", _), enableCrossBuild = true),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
  pushChanges
)
