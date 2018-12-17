package graphite4s

trait Transformer {
  def transform(point: GraphitePoint): GraphitePoint
}

case object NoTransformer extends Transformer {
  override def transform(point: GraphitePoint): GraphitePoint = point
}

class Prefixer(prefix: String) extends Transformer {
  override def transform(point: GraphitePoint): GraphitePoint =
    point.copy(path = s"$prefix.${point.path}")
}
