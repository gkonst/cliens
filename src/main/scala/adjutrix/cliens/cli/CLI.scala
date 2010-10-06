package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
abstract class CLI(configuration: Configuration) {
    def printList(items: List[Any]) = {
        items.foreach(item => row(item.asInstanceOf[Map[Any, Any]]))
    }

    def row(item: Map[Any, Any])

    def list(data: Option[Any]) = {
        data match {
            case Some(items) => printList(items.asInstanceOf[List[Any]])
        }
    }
}