# graphite4s

![Build status](https://github.com/YannMoisan/graphite4s/actions/workflows/build.yml/badge.svg)
[![graphite4s Scala version support](https://index.scala-lang.org/yannmoisan/graphite4s/graphite4s/latest.svg)](https://index.scala-lang.org/yannmoisan/graphite4s/graphite4s)
[![codecov](https://codecov.io/github/YannMoisan/graphite4s/graph/badge.svg?token=37WNLGADEH)](https://codecov.io/github/YannMoisan/graphite4s)
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
