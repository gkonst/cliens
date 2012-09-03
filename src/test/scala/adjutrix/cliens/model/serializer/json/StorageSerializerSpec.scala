package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer.SerializerSpec
import adjutrix.cliens.model.ModelsFactory._

class StorageSerializerSpec extends SerializerSpec {

  lazy val storageJSON = loadFileFromClasspathToString("/json/storage.json")
  lazy val storageWithoutIdJSON = loadFileFromClasspathToString("/json/storageWithoutId.json")

  "deserialize" should {
    "return correct result" in {
      StorageSerializer.deserialize(storageJSON) must beEqualTo(storageUnfilled())
    }
  }

  "serialize" should {
    "not fail if id is Some" in {
      StorageSerializer.serializePretty(storage()) must equalTo(storageJSON)
    }
    "not fail if id is None" in {
      StorageSerializer.serializePretty(storage(id = None)) must equalTo(storageWithoutIdJSON)
    }
  }
}
