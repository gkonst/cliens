package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.{ReaderAdapter, ReaderAdapterComponent}
import adjutrix.cliens.model._
import adjutrix.cliens.conf.{Configurable, PropertiesConfigurable}
import adjutrix.cliens.adapter.protocol.{JSONProtocol, Protocol}
import adjutrix.cliens.model.serializer.json.cash.StorageSerializer

trait StorageAdapterComponent extends ReaderAdapterComponent[Storage] {

  lazy val adapter: StorageAdapter = new StorageComplexAdapter with PropertiesConfigurable with JSONProtocol[Storage] {
    lazy val serializer = StorageSerializer
  }

  trait StorageAdapter extends ReaderAdapter[Storage] {
    self: Configurable with Protocol[Storage] =>

    val baseUrl = "storage"
  }

  trait StorageComplexAdapter extends StorageAdapter {
    self: Configurable with Protocol[Storage] =>

    lazy val currencyTypeAdapter = new CurrencyTypeAdapterComponent {}.adapter

    override def findAll() = {
      super.findAll() fold(l => Left(l), r => Right(r map {
        _.map(fullFill)
      }))
    }

    def fullFill(storage: Storage): Storage = {
      storage.copy(currencyType =
        currencyTypeAdapter.findById(uriToId(storage.currencyType.resourceURI)).right.get.get)
    }
  }

}