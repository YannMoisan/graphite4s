package graphite4s

import cats.Applicative
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging

trait Graphite[M[_]] extends LazyLogging {
  def send(point: GraphitePoint): M[Unit]
  def send(points: List[GraphitePoint]): M[Unit]
}

class BaseGraphite[M[_]: Applicative](
    client: Client[M],
    transformer: Transformer = NoTransformer
) extends Graphite[M] {
  override def send(point: GraphitePoint): M[Unit] =
    client.send(
      GraphitePoint.format(transformer.transform(point)).getBytes("UTF-8"))

  override def send(points: List[GraphitePoint]): M[Unit] = {
    logger.info(s"#${points.length} points to send to graphite")
    points.traverse_(send)
  }
}

class BatchGraphite[M[_]: Applicative](
    client: Client[M],
    batchSize: Int = 1000,
    transformer: Transformer = NoTransformer
) extends BaseGraphite[M](client, transformer) {
  override def send(points: List[GraphitePoint]): M[Unit] =
    points
      .grouped(batchSize)
      .toList
      .traverse_ { grouped =>
        client.send(
          GraphitePoint
            .format(grouped.map(transformer.transform))
            .getBytes("UTF-8"))
      }
}
