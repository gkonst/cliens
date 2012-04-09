package adjutrix.cliens.model

import serializer.StorageSerializer
import ModelsFactory._

class StorageSerializerSpec extends ModelSpec {
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
      val data = """{"name" : "Parex", "id" : 1, "amount" : 10.0,
                     "storageType" : {"name" : "Bill", "id" : 1},
                     "currencyType" : {"name" : "Dollar", "abbr" : "$", "rate" : 1.0, "id" : 1}}"""
      val result = StorageSerializer.deserialize(data)
      modelShouldHaveFields(result, 1, "Parex", 10.0)
    }
  }

  "serialize" should {
    "not fail if id is Some" in {
      val result = StorageSerializer.serialize(storage())
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo("""{"name":"Parex","storageType":{"name":"Bill","id":1},"currencyType":{"name":"Dollar","abbr":"$","rate":1.0,"id":1},"amount":10.0,"id":1}""")
      }
    }
    "not fail if id is None" in {
      val result = StorageSerializer.serialize(storage(id = None))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo("""{"name":"Parex","storageType":{"name":"Bill","id":1},"currencyType":{"name":"Dollar","abbr":"$","rate":1.0,"id":1},"amount":10.0}""")
      }
    }
  }
}
