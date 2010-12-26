package adjutrix.cliens.model

import serializer.StorageTypeSerializer

/**
 * Specification for 'StorageType'.
 *
 * @author Konstantin_Grigoriev
 */

object StorageTypeSpec extends ModelSpec {
    def checkFields(result: StorageType, id: Int, name: String) {
        "result must have id and equal " + id in {
            result.id must (notBeNull and equalTo(id))
        }
        "result must have name and equal " + name in {
            result.name must (notBeNull and equalTo(name))
        }
    }

    "deserialize" should {
        val result = StorageTypeSerializer.deserialize(Map("name" -> "Bill", "id" -> 1d))
        checkFields(result, 1, "Bill")
    }

    "serialize" should {
        var result = StorageTypeSerializer.serialize(new StorageType("Bill", 1))
        "result must be Some of Map[String, Any]" in {
            result must beSome[Map[String, Any]]
        }
        checkPair(result, "id", 1)
        checkPair(result, "name", "Bill")
    }
}