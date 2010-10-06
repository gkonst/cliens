package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration

/**
 * Factory object for adapters. Injects configuration.
 *
 * @author konstantin_grigoriev
 */
object AdapterFactory {
    def apply(configuration: Configuration, adapter: String) = adapter match {
        case "storage" => new StorageAdapter(configuration)
        case "category" => new CategoryAdapter(configuration)
        case "expense" => new ExpenseAdapter(configuration)
        case _ => throw new UnsupportedOperationException("Unknown adapter : " + adapter)
    }
}