package adjutrix.cliens.cli

import adjutrix.cliens.model._
import scala.Predef._
import adjutrix.cliens.adapter.AdapterComponent

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
trait CLI[T <: Model] extends SystemIO {
  self: AdapterComponent[T] =>

  private val headerLine = "-" * header.size

  protected[cli] def header: String = String.format("%-5s", "Id")

  def list(options: CLIOption) {}

  def view(id: Int, options: CLIOption) {}

  def optionList(data: Either[Error, Option[Seq[T]]], options: CLIOption) {
    handleError(data) {
      result =>
        printHeader()
        result match {
          case Some(items) => list(items, options)
          case None => out.println("No data found")
        }
    }
  }

  private def handleError[A](data: Either[Error, Option[A]])(f: Option[A] => Unit) {
    data match {
      case Left(error) => printError(error)
      case Right(x) => f(x)
    }
  }

  private def list(items: Seq[T], options: CLIOption) {
    items.foreach(item => row(item, options))
  }

  def optionRow(data: Either[Error, Option[T]], options: CLIOption) {
    handleError(data) {
      result =>
        printHeader()
        result match {
          case Some(x) => row(x, options)
          case None => out.println("Not found")
        }
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

  protected[cli] def printError(error: Error) {
    out.println(error)
  }
}
