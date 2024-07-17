package graphite4s

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io._
import java.net.{ServerSocket, Socket}
import java.time.Instant
import scala.collection.mutable.ArrayBuffer

class GraphiteIntegrationTest extends AnyFlatSpec with Matchers {
  private val p1 = GraphitePoint("root.1", 1, Instant.ofEpochSecond(11))

  class MockTcpServer(port: Int) {
    @volatile private var running          = true
    private var serverSocket: ServerSocket = _
    var handler: ClientHandler             = _

    def start(): Unit = {
      serverSocket = new ServerSocket(port)
      new Thread(() =>
        while (running)
          try {
            val clientSocket = serverSocket.accept()
            handler = new ClientHandler(clientSocket)
            handler.start()
          } catch {
            case e: IOException =>
              if (!running) {
                // Server was stopped, so we break the loop
                // break
              } else {
                e.printStackTrace()
              }
          }
      ).start()
    }

    def stop(): Unit = {
      running = false
      if (serverSocket != null && !serverSocket.isClosed) {
        serverSocket.close()
      }
    }

    class ClientHandler(socket: Socket) extends Thread {
      val messages = new ArrayBuffer[String]()
      override def run(): Unit =
        try {
          val in  = new BufferedReader(new InputStreamReader(socket.getInputStream))
          val out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream), true)
          var message: String = null

          while ({ message = in.readLine(); message != null }) {
            println(s"Received: $message")
            messages += message
            out.println(s"Echo: $message")
          }
        } catch {
          case e: IOException => e.printStackTrace()
        } finally
          try
            socket.close()
          catch {
            case e: IOException => e.printStackTrace()
          }
    }
  }

  "BaseGraphite" should "send one message" in {
    val server = new MockTcpServer(2003)
    server.start()

    val graphite =
      new BaseGraphite(new JavaTCPClient[IO]("localhost", 2003), new Prefixer("production"))

    graphite.send(p1).unsafeRunSync()

    server.stop()

    server.handler.messages should contain theSameElementsInOrderAs Seq("production.root.1 1.0 11")
  }

}
