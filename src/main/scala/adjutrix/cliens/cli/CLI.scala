package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
abstract class CLI(configuration: Configuration) {
    val summary = Array("id")

    def printList(items: List[Any]) =
        items.foreach(item => row(item.asInstanceOf[Map[Any, Any]]))

    def row(item: Map[Any, Any]) =
        if (configuration.showFull) {
            println(item)
        } else {
            rowSummary(item)
        }

    def rowSummary(item: Map[Any, Any]) {
        def getAttr(attr: Array[String], subitem: Map[Any, Any]): Any = attr.size match {
            case 0 => throw new IllegalArgumentException("Wrong attribute definition")
            case 1 => subitem.get(attr.head) match {
                case Some(x) => x
                case None => null
            }
            case _ => subitem.get(attr.head) match {
                case Some(x) => getAttr(attr.tail, x.asInstanceOf[Map[Any, Any]])
                case None => null
            }
        }
        println(summary.map(attr => attr + " -> " + getAttr(attr.split("\\."), item)).reduceLeft(_ + ", " + _))
    }

    def list(data: Option[Any]) =
        data match {
            case Some(items) => printList(items.asInstanceOf[List[Any]])
        }
}