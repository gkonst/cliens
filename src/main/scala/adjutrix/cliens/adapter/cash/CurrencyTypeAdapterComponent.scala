package adjutrix.cliens.adapter.cash

import adjutrix.cliens.model.CurrencyType
import adjutrix.cliens.conf.{PropertiesConfigurable, Configurable}
import adjutrix.cliens.adapter.{AdapterComponent, Adapter}
import adjutrix.cliens.adapter.protocol.{JSONProtocol, Protocol}
import adjutrix.cliens.model.serializer.json.CurrencyTypeSerializer

trait CurrencyTypeAdapterComponent extends AdapterComponent[CurrencyType] {

  lazy val adapter = new CurrencyTypeAdapter with PropertiesConfigurable with JSONProtocol[CurrencyType] {
    lazy val serializer = CurrencyTypeSerializer
  }

  trait CurrencyTypeAdapter extends Adapter[CurrencyType] {
    self: Configurable with Protocol[CurrencyType] =>

    protected val baseUrl = "currencytype"
  }

}