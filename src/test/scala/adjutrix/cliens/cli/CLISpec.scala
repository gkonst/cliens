package adjutrix.cliens.cli


import adjutrix.cliens.model.Model
import java.io._
import scala.concurrent.ops._
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers
import org.scalatest.mock.MockitoSugar
import adjutrix.cliens.adapter.Adapter

abstract class CLISpec[M <: Model] extends FlatSpec with MustMatchers with MockitoSugar {

  trait MockedIO extends SystemIO {
    val baos = new ByteArrayOutputStream()
    val pis = new PipedInputStream()
    val pos = new PipedOutputStream(pis)

    override val out: PrintStream = new PrintStream(baos)
    override val in = new BufferedReader(new InputStreamReader(pis))

    def outToString = baos.toString

    def stringToIn(data: String) {
      spawn {
        pos.write(data.getBytes)
        pos.flush()
      }
    }
  }

  def givenModel(id: Int): M

  def produceCLI: (CLI[M] with MockedIO, Adapter[M])
}
