package adjutrix.cliens.adapter.cash

import adjutrix.cliens.model._
import adjutrix.cliens.model.ModelsFactory._
import adjutrix.cliens.adapter.AdapterSpec
import adjutrix.cliens.conf.PropertiesConfigurable

class StorageAdapterSpec extends AdapterSpec[Storage] {

  lazy val adapter = new StorageAdapter with PropertiesConfigurable with StorageProtocol

  it should behave like findAllIsDefined()
  it should behave like findByIdIsDefined(fixtureId = 1)
  it should behave like createIsDefined(given = storage(None))
  it should behave like deleteIsDefined(given = storage(None))

  "create" should "return Left with ValidationError if validation fails" in {
    val result = adapter.create(storage(None, null))
    result must be('left)
    result must be(Left(ValidationError("{\"name\": [\"This field is required.\"]}")))
  }

  def specifyFields(result: Storage) {
    result.name must not be null
    result.storageType must not be null
    result.currencyType must not be null
    result.amount must not be null
  }
}