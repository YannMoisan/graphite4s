package graphite4s

import java.time.Instant

import cats.Id

import scala.collection.mutable.ArrayBuffer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GraphiteTest extends AnyFlatSpec with Matchers {

  private class MockClient extends TCPClient[Id] {
    val messages = new ArrayBuffer[String]()
    def send(s: Array[Byte]): Id[Unit] = {
      val _ = messages += new String(s)
    }
  }

  private val p1 = GraphitePoint("root.1", 1, Instant.ofEpochSecond(11))
  private val p2 = GraphitePoint("root.2", 2, Instant.ofEpochSecond(22))
  private val p3 = GraphitePoint("root.3", 3, Instant.ofEpochSecond(33))

  "BaseGraphite" should "send one message" in {
    val mockClient = new MockClient()
    val graphite = new BaseGraphite(mockClient, new Prefixer("production"))

    graphite.send(p1)

    mockClient.messages should contain theSameElementsInOrderAs Seq(
      "production.root.1 1.0 11\n")
  }

  it should "send two messages sequentially" in {
    val mockClient = new MockClient()
    val graphite = new BaseGraphite(mockClient, new Prefixer("production"))

    graphite.send(List(p1, p2))

    mockClient.messages should contain theSameElementsInOrderAs Seq(
      "production.root.1 1.0 11\n",
      "production.root.2 2.0 22\n"
    )
  }

  "BatchGraphite" should "send two messages in batch" in {
    val mockClient = new MockClient()
    val graphite =
      new BatchGraphite(mockClient, transformer = new Prefixer("production"))

    graphite.send(List(p1, p2))

    mockClient.messages should contain theSameElementsInOrderAs Seq(
      "production.root.1 1.0 11\nproduction.root.2 2.0 22\n"
    )
  }

  it should "send 3 messages in batch of two messages" in {
    val mockClient = new MockClient()
    val graphite =
      new BatchGraphite(mockClient,
                        batchSize = 2,
                        transformer = new Prefixer("production"))

    graphite.send(List(p1, p2, p3))

    mockClient.messages should contain theSameElementsInOrderAs Seq(
      "production.root.1 1.0 11\nproduction.root.2 2.0 22\n",
      "production.root.3 3.0 33\n"
    )
  }
}
