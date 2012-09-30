package adjutrix.cliens.adapter.cash

import adjutrix.cliens.model.CurrencyType
import adjutrix.cliens.conf.{PropertiesConfigurable, Configurable}
import adjutrix.cliens.adapter.{ReaderAdapter, ReaderAdapterComponent}
import adjutrix.cliens.adapter.protocol.{JSONProtocol, Protocol}
import adjutrix.cliens.model.serializer.json.CurrencyTypeSerializer

trait CurrencyTypeAdapterComponent extends ReaderAdapterComponent[CurrencyType] {

  lazy val adapter = new CurrencyTypeAdapter with PropertiesConfigurable with JSONProtocol[CurrencyType] {
    lazy val serializer = CurrencyTypeSerializer
  }

  trait CurrencyTypeAdapter extends ReaderAdapter[CurrencyType] {
    self: Configurable with Protocol[CurrencyType] =>

    protected val baseUrl = "currencytype"
  }

}