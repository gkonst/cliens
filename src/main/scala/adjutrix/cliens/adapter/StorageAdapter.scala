package adjutrix.cliens.adapter

import adjutrix.cliens.model.Storage
import adjutrix.cliens.conf.Configurable

/**
 * Adapter impl for working with 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageAdapter extends Adapter[Storage] {
  this: Configurable =>
  val baseUrl = "storage"
}