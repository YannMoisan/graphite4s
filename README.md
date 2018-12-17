# graphite4s

A library for sending points to graphite.

## Project goals

The aim is to provide a lightweight graphite client.
 
This library respects referentially transparency and is agnostic of effect type.

## Usage

```
import java.time.Instant
import cats.effect._

object Example {
  implicit val clock: Clock[IO] = Clock.create[IO]
  val graphite = new BatchGraphite(new JavaTCPClient[IO]("localhost", 2003))
  graphite.send(GraphitePoint("path", 42.0, Instant.now))
}
```