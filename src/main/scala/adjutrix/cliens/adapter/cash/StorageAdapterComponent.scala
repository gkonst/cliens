package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.{ReaderAdapter, ReaderAdapterComponent}
import adjutrix.cliens.model.Storage
import adjutrix.cliens.conf.{Configurable, PropertiesConfigurable}
import adjutrix.cliens.adapter.protocol.{JSONProtocol, Protocol}
import adjutrix.cliens.model.serializer.json.cash.StorageSerializer

trait StorageAdapterComponent extends ReaderAdapterComponent[Storage] {

  lazy val adapter = new StorageAdapter with PropertiesConfigurable with JSONProtocol[Storage] {
    lazy val serializer = StorageSerializer
  }

  trait StorageAdapter extends ReaderAdapter[Storage] {
    self: Configurable with Protocol[Storage] =>

    val baseUrl = "storage"
  }

}