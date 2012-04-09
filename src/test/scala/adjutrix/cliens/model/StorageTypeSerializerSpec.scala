package adjutrix.cliens.model

import serializer.StorageTypeSerializer

class StorageTypeSerializerSpec extends ModelSpec {
  def modelShouldHaveFields(result: StorageType, id: Int, name: String) = {
    modelShouldHaveId(result, id)
    "result must have name and equal " + name in {
      result.name must equalTo(name)
    }
  }


  "deserialize" should {
    "return correct result" in {
      val result = StorageTypeSerializer.deserialize("""{"name" : "Bill", "id" : 1}""")
      modelShouldHaveFields(result, 1, "Bill")
    }
  }
  "serialize" should {
    "not fail if id is Some" in {
      val result = StorageTypeSerializer.serialize(new StorageType("Bill", Some(1)))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo("""{"name":"Bill","id":1}""")
      }
    }
    "not fail if id is None" in {
      val result = StorageTypeSerializer.serialize(new StorageType("Bill"))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo("""{"name":"Bill"}""")
      }
    }
  }
}