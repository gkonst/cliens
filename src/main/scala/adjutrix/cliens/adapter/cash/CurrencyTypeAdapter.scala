package adjutrix.cliens.adapter.cash

import adjutrix.cliens.model.CurrencyType
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.serializer.Serializer
import adjutrix.cliens.adapter.Adapter

class CurrencyTypeAdapter(implicit configuration: Configuration,
                          serializer: Serializer[CurrencyType]) extends Adapter[CurrencyType] {
  protected val baseUrl = "currencytype"
}