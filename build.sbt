import Dependencies._
import sbt.Keys._

organization       := "com.yannmoisan"
crossScalaVersions := Seq("2.12.19", "2.13.14")

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
scmInfo := Some(
  ScmInfo(
    url("https://github.com/yannmoisan/graphite4s"),
    "git@github.com:yannmoisan/graphite4s.git"
  )
)

Test / publishArtifact := false
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
