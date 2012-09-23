package adjutrix.cliens.adapter

import adjutrix.cliens.model.Storage
import adjutrix.cliens.model.ModelsFactory._

class StorageAdapterSpec extends AdapterSpec[Storage] {

  lazy val adapter = new StorageAdapter

  it should behave like findAllIsDefined()
  it should behave like findByIdIsDefined(fixtureId = 1)
  it should behave like createIsDefined(given = storage(None))

  def specifyFields(result: Storage) {
    result.name must not be null
    result.storageType must not be null
    result.currencyType must not be null
    result.amount must not be null
  }
}