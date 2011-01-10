package adjutrix.cliens.adapter

import adjutrix.cliens.adapter.AdapterSpec._
import adjutrix.cliens.model.{CurrencyType, StorageType, Storage}

/**
 * Integration test for {@link StorageAdapter}.
 *
 * @author Konstantin_Grigoriev
 */
object StorageAdapterSpec extends AdapterSpecification[Storage, StorageAdapter]("storage") {


    def checkFields(result: Storage) {
        "result must have name" in {
            result.name must notBeNull
        }
        "result must have storageType" in {
            result.storageType must notBeNull
        }
        "result must have currencyType" in {
            result.currencyType must notBeNull
        }
        "result must have amount" in {
            result.amount must notBeNull
        }
    }

    def createModel = new Storage(name = "TestStorage",
        storageType = new StorageType("Bill", Some(10)),
        currencyType = new CurrencyType("Dollar", "$", 1.0, Some(1)),
        amount = 0)
}