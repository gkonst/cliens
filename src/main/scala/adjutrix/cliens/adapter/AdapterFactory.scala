package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

/**
 * Factory object for adapters. Injects configuration.
 *
 * @author konstantin_grigoriev
 */
object AdapterFactory {
    def apply(configuration: Configuration, adapter: String): Adapter[Model] = adapter match {
        case "storage" => new StorageAdapter(configuration).asInstanceOf[Adapter[Model]]
        case "category" => new CategoryAdapter(configuration).asInstanceOf[Adapter[Model]]
        case "expense" => new ExpenseAdapter(configuration).asInstanceOf[Adapter[Model]]
        case _ => throw new UnsupportedOperationException("Unknown adapter : " + adapter)
    }
}