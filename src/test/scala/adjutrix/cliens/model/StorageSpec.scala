package adjutrix.cliens.model

import serializer.{Serializer, StorageSerializer}
import collection.immutable.Map

/**
 * Specification for {@link Storage}.
 *
 * @author Konstantin_Grigoriev
 */

object StorageSpec extends ModelSpec {
    def checkFields(result: Storage, id: Int, name: String, amount: BigDecimal) {
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

    val storageType = new StorageType("Bill", 1)
    val currencyType = new CurrencyType("Dollar", "$", 1.0, 1)

    "deserialize" should {
        val data = Map("name" -> "Parex", "id" -> 1d, "amount" -> "10.0",
            "type" -> Serializer(classOf[StorageType]).serialize(storageType),
            "currency_type" -> Serializer(classOf[CurrencyType]).serialize(currencyType))
        val result = StorageSerializer.deserialize(data)
        checkFields(result, 1, "Bill", 10.0)
    }

    "serialize" should {
        var result = StorageSerializer.serialize(new Storage("Bill", storageType, currencyType, 10.0, 1))
        "result must not be empty" in {
            result must notBeEmpty
        }
        checkPair(result, "id", 1)
        checkPair(result, "name", "Bill")
        checkPair(result, "amount", "10.0")
    }
}