# graphite4s

![Build status](https://github.com/YannMoisan/graphite4s/actions/workflows/build.yml/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.yannmoisan/graphite4s_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.yannmoisan/graphite4s_2.12)
[![License](https://img.shields.io/github/license/yannmoisan/graphite4s)](http://www.apache.org/licenses/LICENSE-2.0.html)

A library for sending points to graphite.

## Project goals

The aim is to provide a lightweight graphite client.
 
This library respects referentially transparency and is agnostic of effect type.

## Getting started

Add the dependency to your project:

```scala
libraryDependencies += "com.yannmoisan" %% "graphite4s" % Version
```

## Usage

```scala
import java.time.Instant
import cats.effect._

object Example extends IOApp.Simple {
  override def run: IO[Unit] = {
    val graphite = new BatchGraphite(new JavaTCPClient[IO]("localhost", 2003))
    (for {
      now <- Clock[IO].realTime
      req <- graphite.send(GraphitePoint("path", 42.0, Instant.ofEpochMilli(now.toMillis)))
    } yield req)
  }
}
```
