import Dependencies._
import sbt.Keys._

organization       := "com.yannmoisan"
crossScalaVersions := Seq("2.11.12", "2.12.15", "2.13.7")

def priorTo2_13(scalaVersion: String): Boolean =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, minor)) if minor < 13 => true
    case _                              => false
  }

scalacOptions ++= {
  if (priorTo2_13(scalaVersion.value)) Seq("-Ypartial-unification") else Nil
}
scalafmtOnCompile := true

libraryDependencies ++= Seq(
  catsEffect,
  scalaLogging,
  scalaTest % Test
)

startYear := Some(2018)
licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/yannmoisan/graphite4s"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/yannmoisan/graphite4s"),
    "git@github.com:yannmoisan/graphite4s.git"
  )
)

publishTo := {
  Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  )
}
publishMavenStyle       := true
Test / publishArtifact  := false
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

publishTo := sonatypePublishToBundle.value

import sbtrelease.ReleaseStateTransformations._

releaseCrossBuild := true

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
