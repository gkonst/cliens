package adjutrix.cliens.adapter

import adjutrix.cliens.adapter.AdapterSpecification._
import adjutrix.cliens.model.Storage

/**
 * Integration test for {@link StorageAdapter}.
 *
 * @author Konstantin_Grigoriev
 */
object StorageAdapterSpec extends AdapterSpecification[Storage, StorageAdapter]("storage") {


    def checkFields(result: Storage) {
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
        storageType = 46,
        currencyType = 1,
        amount = 0)
}