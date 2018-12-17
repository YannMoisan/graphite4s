package graphite4s

import java.time.{Instant, LocalDateTime, ZoneOffset}

import com.typesafe.scalalogging.LazyLogging

final case class GraphitePoint(
    path: String,
    value: Double,
    instant: Instant
)

object GraphitePoint extends LazyLogging {
  def apply(path: String, value: Double, dt: LocalDateTime): GraphitePoint =
    GraphitePoint(path, value, dt.toInstant(ZoneOffset.UTC))

  def format(point: GraphitePoint): String = {
    val message =
      s"${point.path} ${point.value} ${point.instant.getEpochSecond}\n"
    logger.info(s"data: $message")
    message
  }

  def format(point: Seq[GraphitePoint]): String =
    point.map(format).mkString
}
