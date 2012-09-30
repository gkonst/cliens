package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.{Adapter, AdapterComponent}
import adjutrix.cliens.model.Storage
import adjutrix.cliens.conf.{Configurable, PropertiesConfigurable}
import adjutrix.cliens.adapter.protocol.{JSONProtocol, Protocol}
import adjutrix.cliens.model.serializer.json.StorageSerializer

trait StorageAdapterComponent extends AdapterComponent[Storage] {

  lazy val adapter = new StorageAdapter with PropertiesConfigurable with JSONProtocol[Storage] {
    lazy val serializer = StorageSerializer
  }

  trait StorageAdapter extends Adapter[Storage] {
    self: Configurable with Protocol[Storage] =>

    val baseUrl = "storage"
  }

}