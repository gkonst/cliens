package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.{ReaderAdapter, ReaderAdapterComponent}
import adjutrix.cliens.model.StorageType
import adjutrix.cliens.adapter.protocol.{JSONProtocol, Protocol}
import adjutrix.cliens.conf.{PropertiesConfigurable, Configurable}
import adjutrix.cliens.model.serializer.json.cash.StorageTypeSerializer

trait StorageTypeAdapterComponent extends ReaderAdapterComponent[StorageType] {
  lazy val adapter: StorageTypeAdapter = new StorageTypeAdapter with PropertiesConfigurable with JSONProtocol[StorageType] {
    def serializer = StorageTypeSerializer
  }

  trait StorageTypeAdapter extends ReaderAdapter[StorageType] {
    self: Configurable with Protocol[StorageType] =>

    protected val baseUrl = "storagetype"
  }

}