package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer.SerializerSpec
import adjutrix.cliens.model.StorageType

class StorageTypeSerializerSpec extends SerializerSpec {

  "deserialize" should {
    "return correct result" in {
      StorageTypeSerializer.deserialize(
        """{"name" : "Bill",
            "id" :"1",
            "resource_uri": "/api/v1/storagetype/1/"}""") must beEqualTo(StorageType("Bill", Some(1), Some("/api/v1/storagetype/1/")))
    }
  }
  "serialize" should {
    "not fail if id is Some" in {
      StorageTypeSerializer.serialize(StorageType("Bill", Some(1), Some("/api/v1/storagetype/1/"))) must equalTo( """{"id":"1","name":"Bill","resource_uri":"/api/v1/storagetype/1/"}""")
    }
    "not fail if id is None" in {
      StorageTypeSerializer.serialize(StorageType("Bill")) must equalTo( """{"name":"Bill"}""")
    }
  }
}