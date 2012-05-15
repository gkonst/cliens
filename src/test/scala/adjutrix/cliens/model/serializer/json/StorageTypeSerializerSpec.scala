package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer.SerializerSpec
import adjutrix.cliens.model.StorageType

class StorageTypeSerializerSpec extends SerializerSpec {

  "deserialize" should {
    "return correct result" in {
      StorageTypeSerializer.deserialize( """{"name" : "Bill", "id" : 1}""") must beEqualTo(StorageType("Bill", Some(1)))
    }
  }
  "serialize" should {
    "not fail if id is Some" in {
      StorageTypeSerializer.serialize(new StorageType("Bill", Some(1))) must equalTo( """{"name":"Bill","id":1}""")
    }
    "not fail if id is None" in {
      StorageTypeSerializer.serialize(new StorageType("Bill")) must equalTo( """{"name":"Bill"}""")
    }
  }
}