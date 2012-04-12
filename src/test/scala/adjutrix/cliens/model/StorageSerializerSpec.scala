package adjutrix.cliens.model

import serializer.StorageSerializer
import ModelsFactory._

class StorageSerializerSpec extends ModelSpec {

  lazy val storageJSON = loadFileFromClasspathToString("/storage.json")
  lazy val storageWithoutIdJSON = loadFileFromClasspathToString("/storageWithoutId.json")

  def modelShouldHaveFields(result: Storage, id: Int, name: String, amount: BigDecimal) = {
    "result must have storageType" in {
      result.storageType must not beNull
    }
    "result must have currencyType" in {
      result.currencyType must not beNull
    }
    "result must have amount equal " + amount in {
      result.amount must equalTo(amount)
    }
    "result must have name equal " + name in {
      result.name must equalTo(name)
    }
  }

  "deserialize" should {
    "return correct result" in {
      val result = StorageSerializer.deserialize(storageJSON)
      modelShouldHaveFields(result, 1, "Parex", 10.0)
    }
  }

  "serialize" should {
    "not fail if id is Some" in {
      val result = StorageSerializer.serializePretty(storage())
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo(storageJSON)
      }
    }
    "not fail if id is None" in {
      val result = StorageSerializer.serializePretty(storage(id = None))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo(storageWithoutIdJSON)
      }
    }
  }
}
