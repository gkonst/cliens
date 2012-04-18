package adjutrix.cliens.cli

import adjutrix.cliens.model.Model
import scala.Predef._
import adjutrix.cliens.conf.Configuration

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
abstract class CLI[T <: Model](configuration: Configuration) {

  def header: String = String.format("%-5s", "Id")

  val headerLine = "".padTo(header.size, "-").reduceLeft(_ + "" + _)

  def list(items: Seq[T], options: CLIOption) {
    items.foreach(item => row(item, options))
  }

  def optionRow(item: Option[T], options: CLIOption) {
    printHeader()
    item match {
      case Some(x) => row(x, options)
      case None => println("Not found")
    }
  }

  def row(item: T, options: CLIOption) {
    options match {
      case Verbose(other) => println(rowFull(item))
      case _ => println(rowSummary(item))
    }
  }

  def rowSummary(item: T) = {
    item.id match {
      case Some(x) => String.format("%-5s", x.toString)
      case None => ""
    }
  }

  def rowFull(item: T) = {
    rowSummary(item)
  }

  def printHeaderLine() {
    println(headerLine)
  }

  def printHeader() {
    printHeaderLine()
    println(header)
    printHeaderLine()
  }

  def optionList(data: Option[Seq[T]], options: CLIOption) {
    printHeader()
    data match {
      case Some(items) => list(items, options)
      case None => println("No data found")
    }
  }
}

sealed trait CLIOption

case class NoOption() extends CLIOption

case class Verbose(anotherOption: CLIOption) extends CLIOption