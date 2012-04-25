package adjutrix.cliens.cli


import adjutrix.cliens.model.Model
import adjutrix.cliens.conf.PropertiesConfiguration
import org.specs2.mutable.Specification
import java.io.{PrintStream, ByteArrayOutputStream}
import org.specs2.specification.Scope

abstract class CLISpec[T <: Model] extends Specification {
  implicit val configuration = PropertiesConfiguration.loadFromDefault()

  trait MockedIO extends SystemIO {
    val baos = new ByteArrayOutputStream()

    override val out: PrintStream = new PrintStream(baos)
    //    override val in = _

    def outToString = baos.toString
  }

  def produceCLI: CLI[T] with MockedIO

  def givenModel(id: Int): T

  def expectedHeader: String

  def expectedNoneForRow: String = "Not found\n"

  def expectedSomeForRow: String

  def expectedSomeForRowVerbose: String = expectedSomeForRow

  def expectedNoneForList: String = "No data found\n"

  def expectedSomeForList: String

  "printHeader should be correct" in new CLIScope {
    cli.printHeader()
    cli.outToString must equalTo(expectedHeader)
  }

  "optionRow return correct result in case of None" in new CLIScope {
    cli.optionRow(None, NoOption())
    cli.outToString must equalTo(expectedHeader + expectedNoneForRow)
  }

  "optionRow return correct result in case of Some" in new CLIScope {
    cli.optionRow(Some(givenModel(1)), NoOption())
    cli.outToString must equalTo(expectedHeader + expectedSomeForRow)
  }

  "optionRow return correct result in case of Some and Verbose option" in new CLIScope {
    cli.optionRow(Some(givenModel(1)), Verbose())
    cli.outToString must equalTo(expectedHeader + expectedSomeForRowVerbose)
  }

  "optionList return correct result in case of None" in new CLIScope {
    cli.optionList(None, NoOption())
    cli.outToString must equalTo(expectedHeader + expectedNoneForList)
  }

  "optionList return correct result in case of Some" in new CLIScope {
    cli.optionList(Some(List(givenModel(1), givenModel(2))), NoOption())
    cli.outToString must equalTo(expectedHeader + expectedSomeForList)
  }

  trait CLIScope extends Scope {
    val cli = produceCLI
  }

}
