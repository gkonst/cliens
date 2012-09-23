package adjutrix.cliens.adapter

import adjutrix.cliens.model.CurrencyType

class CurrencyTypeAdapterSpec extends AdapterSpec[CurrencyType] {
  lazy val adapter = new CurrencyTypeAdapter

  it should behave like findAllIsDefined()
  it should behave like findByIdIsDefined(fixtureId = 1)

  def specifyFields(result: CurrencyType) {
    result.name must not be null
    result.abbr must not be null
    result.rate must not be null
  }

}