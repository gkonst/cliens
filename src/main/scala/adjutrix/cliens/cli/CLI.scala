package adjutrix.cliens.cli

import adjutrix.cliens.model.Model
import scala.Predef._
import adjutrix.cliens.conf.Configuration
import java.io.{BufferedReader, PrintStream}

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
abstract class CLI[T <: Model](implicit configuration: Configuration) extends SystemIO {

  private val headerLine = "-" * header.size

  protected[cli] def header: String = String.format("%-5s", "Id")

  def optionList(data: Option[Seq[T]], options: CLIOption) {
    printHeader()
    data match {
      case Some(items) => list(items, options)
      case None => out.println("No data found")
    }
  }

  private def list(items: Seq[T], options: CLIOption) {
    items.foreach(item => row(item, options))
  }

  def optionRow(item: Option[T], options: CLIOption) {
    printHeader()
    item match {
      case Some(x) => row(x, options)
      case None => out.println("Not found")
    }
  }

  def create(): T

  def readLine(prompt: String, args: Any*) = {
    out.printf(prompt, args)
    in.readLine()
  }

  private def row(item: T, options: CLIOption) {
    options match {
      case Verbose(other) => out.println(rowVerbose(item))
      case _ => out.println(rowSummary(item))
    }
  }

  protected[cli] def rowSummary(item: T) = item.id match {
    case Some(x) => String.format("%-5s", x.toString)
    case None => ""
  }

  protected[cli] def rowVerbose(item: T) = rowSummary(item)

  protected[cli] def printHeaderLine() {
    out.println(headerLine)
  }

  protected[cli] def printHeader() {
    printHeaderLine()
    out.println(header)
    printHeaderLine()
  }
}

trait SystemIO {
  def in: BufferedReader = Console.in

  def out: PrintStream = Console.out
}
