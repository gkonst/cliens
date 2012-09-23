package adjutrix.cliens.adapter

import adjutrix.cliens.model.Storage
import adjutrix.cliens.model.ModelsFactory._

class StorageAdapterSpec extends AdapterSpec[Storage] {

  lazy val adapter = new StorageAdapter()

  def specifyFields(result: Storage) {
    result.name must not be null
    result.storageType must not be null
    result.currencyType must not be null
    result.amount must not be null
  }

  def createModel = storage(None)
}