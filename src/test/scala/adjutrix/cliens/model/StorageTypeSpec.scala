package adjutrix.cliens.model

import serializer.StorageTypeSerializer

/**
 * Specification for 'StorageType'.
 *
 * @author Konstantin_Grigoriev
 */

object StorageTypeSpec extends ModelSpec {
    def checkFields(result: StorageType, id: Int, name: String) {
        checkId(result, id)
        "result must have name and equal " + name in {
            result.name must (notBeNull and equalTo(name))
        }
    }

    "deserialize" should {
        val result = StorageTypeSerializer.deserialize(Map("name" -> "Bill", "id" -> 1d))
        checkFields(result, 1, "Bill")
    }

    "serialize" should {
        "not fail if id is Some" in {
            var result = StorageTypeSerializer.serialize(new StorageType("Bill", Some(1)))
            "result must not be empty" in {
                result must notBeEmpty
            }
            checkPair(result, "id", 1)
            checkPair(result, "name", "Bill")
        }
        "not fail if id is None" in {
            var result = StorageTypeSerializer.serialize(new StorageType("Bill"))
            "result must not be empty" in {
                result must notBeEmpty
            }
            result must notHaveKey("id")
            checkPair(result, "name", "Bill")
        }
    }
}