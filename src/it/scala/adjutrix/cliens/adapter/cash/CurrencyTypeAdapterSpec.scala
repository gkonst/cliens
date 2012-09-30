package adjutrix.cliens.adapter.cash

import adjutrix.cliens.model.CurrencyType
import adjutrix.cliens.adapter.AdapterSpec
import adjutrix.cliens.conf.PropertiesConfigurable

class CurrencyTypeAdapterSpec extends AdapterSpec[CurrencyType] {
  lazy val adapter = new CurrencyTypeAdapter with PropertiesConfigurable with CurrencyTypeProtocol

  it should behave like findAllIsDefined()
  it should behave like findByIdIsDefined(fixtureId = 1)

  def specifyFields(result: CurrencyType) {
    result.name must not be null
    result.abbr must not be null
    result.rate must not be null
  }

}