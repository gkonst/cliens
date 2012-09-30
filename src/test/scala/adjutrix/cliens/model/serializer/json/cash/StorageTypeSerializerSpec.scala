package adjutrix.cliens.model.serializer.json.cash

import adjutrix.cliens.model.StorageType
import org.specs2.mutable.Specification

class StorageTypeSerializerSpec extends Specification {

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
      StorageTypeSerializer.serialize(StorageType("Bill", Some(1), Some("/api/v1/storagetype/1/"))) must equalTo( """{"name":"Bill","id":"1","resource_uri":"/api/v1/storagetype/1/"}""")
    }
    "not fail if id is None" in {
      StorageTypeSerializer.serialize(StorageType("Bill")) must equalTo( """{"name":"Bill"}""")
    }
  }
}