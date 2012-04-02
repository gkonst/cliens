package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

/**
 * Factory object for adapters. Injects configuration.
 *
 * @author konstantin_grigoriev
 */
object AdapterFactory {
  def apply(implicit configuration: Configuration, adapter: String): Adapter[Model] = adapter match {
    case "storage" => new StorageAdapter().asInstanceOf[Adapter[Model]]
    case "category" => new CategoryAdapter().asInstanceOf[Adapter[Model]]
    case "expense" => new ExpenseAdapter().asInstanceOf[Adapter[Model]]
    case _ => throw new UnsupportedOperationException("Unknown adapter : " + adapter)
  }
}