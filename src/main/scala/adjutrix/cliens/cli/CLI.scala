package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
abstract class CLI[T <: Model](configuration: Configuration) {
    val summary = Array("id")

    def printList(items: List[T]) =
        items.foreach(item => row(item))

    def row(item: T) =
        if (configuration.showFull) {
            println(item)
        } else {
            rowSummary(item)
        }

    def rowSummary(item: T) {
        // TODO implement
        //        def getAttr(attr: Array[String], subitem: Map[Any, Any]): Any = subitem match {
        //            case null => null
        //            case _ => attr.size match {
        //                case 0 => throw new IllegalArgumentException("Wrong attribute definition")
        //                case 1 => subitem.get(attr.head) match {
        //                    case Some(x) => x
        //                    case None => null
        //                }
        //                case _ => subitem.get(attr.head) match {
        //                    case Some(x) => getAttr(attr.tail, x.asInstanceOf[Map[Any, Any]])
        //                    case None => null
        //                }
        //            }
        //        }
        //        println(summary.map(attr => attr + " -> " + getAttr(attr.split("\\."), item)).reduceLeft(_ + ", " + _))
    }

    def list(data: Option[List[T]]) =
        data match {
            case Some(items) => printList(items)
            case None => println("No data found")
        }
}