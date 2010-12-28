package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
abstract class CLI[T <: Model](configuration: Configuration) {

    def header: String = String.format("%-5s", "Id")

    val headerLine = "".padTo(header.size, "-").reduceLeft(_ + "" + _)

    def list(items: List[T]) =
        items.foreach(item => row(item))

    def optionRow(item: Option[T]) {
        printHeader
        item match {
            case Some(x) => row(x)
            case None => println("Not found")
        }
    }

    def row(item: T) {
        if (configuration.showFull) {
            println(rowFull(item))
        } else {
            println(rowSummary(item))
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

    def printHeaderLine: Unit = {
        println(headerLine)
    }

    def printHeader: Unit = {
        printHeaderLine
        println(header)
        printHeaderLine
    }

    def optionList(data: Option[List[T]]) = {
        printHeader
        data match {
            case Some(items) => list(items)
            case None => println("No data found")
        }
    }
}