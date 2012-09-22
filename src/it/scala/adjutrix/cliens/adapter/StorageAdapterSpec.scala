package adjutrix.cliens.adapter

import adjutrix.cliens.model.Storage
import adjutrix.cliens.model.ModelsFactory._

class StorageAdapterSpec extends AdapterSpec[Storage] {

  lazy val adapter = new StorageAdapter()

  def specifyFields(result: Storage) = {
    "result must have name" in {
      result.name must not beNull
    }
    "result must have storageType" in {
      result.storageType must not beNull
    }
    "result must have currencyType" in {
      result.currencyType must not beNull
    }
    "result must have amount" in {
      result.amount must not beNull
    }
  }

  def createModel = storage()
}