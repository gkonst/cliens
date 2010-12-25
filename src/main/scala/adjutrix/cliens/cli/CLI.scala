package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
abstract class CLI[T <: Model](configuration: Configuration) {

    def list(items: List[T]) =
        items.foreach(item => row(item))

    def row(item: T) {
        if (configuration.showFull) {
            println(rowFull(item))
        } else {
            println(rowSummary(item))
        }
    }

    def rowSummary(item: T) = {
        "id -> " + item.id
    }

    def rowFull(item: T) = {
        rowSummary(item)
    }

    def optionList(data: Option[List[T]]) =
        data match {
            case Some(items) => list(items)
            case None => println("No data found")
        }
}