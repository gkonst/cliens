package adjutrix.cliens.adapter.cash

import adjutrix.cliens.model.CurrencyType
import adjutrix.cliens.conf.Configurable
import adjutrix.cliens.adapter.Adapter
import adjutrix.cliens.adapter.protocol.Protocol

trait CurrencyTypeAdapter extends Adapter[CurrencyType] {
  self: Configurable with Protocol[CurrencyType] =>

  protected val baseUrl = "currencytype"
}