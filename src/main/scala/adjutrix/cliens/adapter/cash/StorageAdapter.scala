package adjutrix.cliens.adapter.cash

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Storage
import adjutrix.cliens.model.serializer.Serializer
import adjutrix.cliens.adapter.Adapter

/**
 * Adapter impl for working with 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageAdapter(implicit configuration: Configuration,
                     serializer: Serializer[Storage]) extends Adapter[Storage] {
  val baseUrl = "storage"
}