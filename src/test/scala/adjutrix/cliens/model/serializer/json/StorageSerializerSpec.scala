package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer.SerializerSpec
import adjutrix.cliens.model.Storage
import adjutrix.cliens.model.ModelsFactory._

class StorageSerializerSpec extends SerializerSpec {

  lazy val storageJSON = loadFileFromClasspathToString("/storage.json")
  lazy val storageSerializedJSON = loadFileFromClasspathToString("/storageSerialized.json")
  lazy val storageWithoutIdJSON = loadFileFromClasspathToString("/storageWithoutId.json")
  lazy val storageWithoutIdSerializedJSON = loadFileFromClasspathToString("/storageWithoutIdSerialized.json")

  "deserialize" should {
    "return correct result" in {
      StorageSerializer.deserialize(storageJSON) must beEqualTo(Storage("Parex", storageType, currencyType, 10.0, Some(1)))
    }
  }

  "serialize" should {
    "not fail if id is Some" in {
      StorageSerializer.serializePretty(storage()) must equalTo(storageSerializedJSON)
    }
    "not fail if id is None" in {
      StorageSerializer.serializePretty(storage(id = None)) must equalTo(storageWithoutIdSerializedJSON)
    }
  }
}
