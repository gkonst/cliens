package adjutrix.cliens.cli

import adjutrix.cliens.model._
import adjutrix.cliens.adapter.ReaderAdapter
import org.mockito.Mockito._
import org.mockito.Matchers._

abstract class ReaderCLISpec[M <: Model] extends CLISpec[M] {

  def produceCLI: (ListCLI[M] with MockedIO, ReaderAdapter[M])

  def expectedHeader: String

  def expectedNoneForRow: String = "Not found\n"

  def expectedSomeForRow: String

  def expectedSomeForRowVerbose: String = expectedSomeForRow

  def expectedNoneForList: String = "No data found\n"

  def expectedSomeForList: String

  "printHeader" should "return correct result" in new CLIContext {
    cli.printHeader()
    cli.outToString must be(expectedHeader)
  }

  "detail" should "return correct result in case of None" in new CLIContext {
    when(adapter.findById(anyInt())).thenReturn(Right(None))
    cli.detail(anyInt())(NoOption())
    cli.outToString must be(expectedHeader + expectedNoneForRow)
  }

  "detail" should "return correct result in case of Some" in new CLIContext {
    when(adapter.findById(anyInt())).thenReturn(Right(Some(givenModel(1))))
    cli.detail(anyInt())(NoOption())
    cli.outToString must be(expectedHeader + expectedSomeForRow)
  }

  "detail" should "return correct result in case of Some and Verbose option" in new CLIContext {
    when(adapter.findById(anyInt())).thenReturn(Right(Some(givenModel(1))))
    cli.detail(anyInt())(Verbose())
    cli.outToString must be(expectedHeader + expectedSomeForRowVerbose)
  }

  "list" should "return correct result in case of None" in new CLIContext {
    when(adapter.findAll()).thenReturn(Right(None))
    cli.list(NoOption())
    cli.outToString must be(expectedHeader + expectedNoneForList)
  }

  "list" should "return correct result in case of Some" in new CLIContext {
    when(adapter.findAll()).thenReturn(Right(Some(List(givenModel(1), givenModel(2)))))
    cli.list(NoOption())
    cli.outToString must be(expectedHeader + expectedSomeForList)
  }

  trait CLIContext {
    val (cli, adapter) = produceCLI
  }

}
