package graphite4s

import java.io.{DataOutputStream, OutputStream}
import java.net.{InetAddress, Socket}

import cats.effect.kernel.{Clock, Resource, Sync}
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging

trait TCPClient[M[_]] {
  def send(message: Array[Byte]): M[Unit]
}

class JavaTCPClient[F[_]](
    host: String,
    port: Int
)(implicit sync: Sync[F], clock: Clock[F])
    extends TCPClient[F]
    with LazyLogging {
  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  private def outputStreamFor(): Resource[F, OutputStream] =
    for {
      socket <- Resource.fromAutoCloseable(
        Sync[F].delay(new Socket(InetAddress.getByName(host), port))
      )
      os <- Resource.fromAutoCloseable(
        Sync[F].delay(new DataOutputStream(socket.getOutputStream))
      )
    } yield os

  def send(message: Array[Byte]): F[Unit] =
    for {
      start  <- Clock[F].monotonic
      _      <- outputStreamFor().use(outputStream => Sync[F].delay(outputStream.write(message)))
      finish <- Clock[F].monotonic
    } yield logger.info(s"[send] duration: ${finish.minus(start).toMillis} ms.")
}
