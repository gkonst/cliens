package adjutrix.cliens.adapter.cash

import adjutrix.cliens.conf.Configurable
import adjutrix.cliens.model.Storage
import adjutrix.cliens.adapter.Adapter
import adjutrix.cliens.adapter.protocol.Protocol

trait StorageAdapter extends Adapter[Storage] {
  self: Configurable with Protocol[Storage] =>

  val baseUrl = "storage"
}