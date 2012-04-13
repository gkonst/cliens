package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
abstract class CLI[T <: Model](configuration: Configuration, options: CLIOption) {

  def header: String = String.format("%-5s", "Id")

  val headerLine = "".padTo(header.size, "-").reduceLeft(_ + "" + _)

  def list(items: Seq[T]) {
    items.foreach(item => row(item))
  }

  def optionRow(item: Option[T]) {
    printHeader()
    item match {
      case Some(x) => row(x)
      case None => println("Not found")
    }
  }

  def row(item: T) {
    options match {
      case Verbose => println(rowFull(item))
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

  def optionList(data: Option[Seq[T]]) {
    printHeader()
    data match {
      case Some(items) => list(items)
      case None => println("No data found")
    }
  }
}

sealed trait CLIOption

case class NoOption() extends CLIOption

case class Verbose(anotherOption: CLIOption) extends CLIOption