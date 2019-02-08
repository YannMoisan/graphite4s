package graphite4s

import java.io.{DataOutputStream, OutputStream}
import java.net.{DatagramPacket, DatagramSocket, InetAddress, Socket}

import cats.effect.{Clock, Resource, Sync}
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration.MILLISECONDS

trait Client[M[_]] {
  def send(message: Array[Byte]): M[Unit]
}

class JavaUDPClient[F[_]](
    host: String,
    port: Int
)(implicit F: Sync[F], clock: Clock[F])
    extends Client[F]
    with LazyLogging {

  private def socket: Resource[F, DatagramSocket] =
    for {
      socket <- Resource.make {
        F.delay(new DatagramSocket(port, InetAddress.getByName(host)))
      }(s => F.delay(s.close()))
    } yield socket

  def send(message: Array[Byte]): F[Unit] =
    for {
      start <- clock.monotonic(MILLISECONDS)
      _ <- socket.use { s =>
        F.delay(s.send(new DatagramPacket(message, message.length)))
      }
      finish <- clock.monotonic(MILLISECONDS)
    } yield logger.info(s"[send] duration: ${finish - start} ms.")
}
class JavaTCPClient[F[_]](
    host: String,
    port: Int
)(implicit F: Sync[F], clock: Clock[F])
    extends Client[F]
    with LazyLogging {

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  private def outputStreamFor(): Resource[F, OutputStream] =
    for {
      socket <- Resource.fromAutoCloseable(
        F.delay(new Socket(InetAddress.getByName(host), port))
      )
      os <- Resource.fromAutoCloseable(
        F.delay(new DataOutputStream(socket.getOutputStream))
      )
    } yield os

  def send(message: Array[Byte]): F[Unit] =
    for {
      start <- clock.monotonic(MILLISECONDS)
      _ <- outputStreamFor().use(outputStream =>
        F.delay(outputStream.write(message)))
      finish <- clock.monotonic(MILLISECONDS)
    } yield logger.info(s"[send] duration: ${finish - start} ms.")
}
