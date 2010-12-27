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
        "result must have amount equal " + amount in {
            result.amount must beEqualTo(amount)
        }
        "result must have name equal " + name in {
            result.name must beEqualTo(name)
        }
    }

    val storageType = new StorageType("Bill", Some(1))
    val currencyType = new CurrencyType("Dollar", "$", 1.0, Some(1))

    def storage(id: Option[Int] = Some(1)) = new Storage("Parex", storageType, currencyType, 10.0, id)

    "deserialize" should {
        val data = Map("name" -> "Parex", "id" -> 1d, "amount" -> "10.0",
            "type" -> Serializer(classOf[StorageType]).serialize(storageType),
            "currency_type" -> Serializer(classOf[CurrencyType]).serialize(currencyType))
        val result = StorageSerializer.deserialize(data)
        checkFields(result, 1, "Parex", 10.0)
    }

    "serialize" should {
        "not fail if id is Some" in {
            val result = StorageSerializer.serialize(storage())
            "result must not be empty" in {
                result must notBeEmpty
            }
            checkPair(result, "id", 1)
            checkPair(result, "name", "Parex")
            checkPair(result, "amount", "10.0")
        }
        "not fail if id is None" in {
            val result = StorageSerializer.serialize(storage(None))
            "result must not be empty" in {
                result must notBeEmpty
            }
            result must notHaveKey("id")
            checkPair(result, "name", "Parex")
            checkPair(result, "amount", "10.0")
        }

    }
}