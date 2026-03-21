import Dependencies._
import sbt.Keys._

val Scala_2_12 = "2.12.19"
val Scala_2_13 = "2.13.14"

organization       := "com.yannmoisan"
scalaVersion       := Scala_2_12
crossScalaVersions := Seq(Scala_2_12, Scala_2_13)

scalafmtOnCompile := true

libraryDependencies ++= Seq(
  catsEffectKernel,
  scalaLogging,
  catsEffect % Test,
  scalaTest  % Test
)

startYear := Some(2018)
licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/yannmoisan/graphite4s"))
scmInfo  := Some(
  ScmInfo(
    url("https://github.com/yannmoisan/graphite4s"),
    "git@github.com:yannmoisan/graphite4s.git"
  )
)

Test / publishArtifact := false
pomIncludeRepository   := { _ =>
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
