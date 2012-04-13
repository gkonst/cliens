package adjutrix.cliens.adapter

import adjutrix.cliens.model.Model
import adjutrix.cliens.conf.PropertiesConfigurable

/**
 * Factory object for adapters. Injects configuration.
 *
 * @author konstantin_grigoriev
 */
object AdapterFactory {
  def apply(adapter: String): Adapter[Model] = adapter match {
    case "storage" => (new StorageAdapter() with PropertiesConfigurable).asInstanceOf[Adapter[Model]]
    case "category" => (new CategoryAdapter() with PropertiesConfigurable).asInstanceOf[Adapter[Model]]
    case "expense" => (new ExpenseAdapter() with PropertiesConfigurable).asInstanceOf[Adapter[Model]]
    case _ => throw new UnsupportedOperationException("Unknown adapter : " + adapter)
  }
}