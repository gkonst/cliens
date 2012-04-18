package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Storage

/**
 * Adapter impl for working with 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageAdapter(implicit configuration: Configuration) extends Adapter[Storage](configuration) {
  val baseUrl = "storage"
}