package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer._
import adjutrix.cliens.model.{CategoryType, Category}
import adjutrix.cliens.model.ModelsFactory.{storage, category}
import org.specs2.mutable.Specification

class CategorySerializerSpec extends Specification {

  lazy val categoryWithoutDefaultStorageJSON = loadFileFromClasspathToString("/json/categoryWithoutDefaultStorage.json")
  lazy val categoryWithoutIdAndDefaultStorageJSON = loadFileFromClasspathToString("/json/categoryWithoutIdAndDefaultStorage.json")
  lazy val categoryJSON = loadFileFromClasspathToString("/json/category.json")
  lazy val categorySerializedJSON = loadFileFromClasspathToString("/json/categorySerialized.json")
  lazy val categoriesJSON = loadFileFromClasspathToString("/json/categories.json")

  "deserialize" should {
    "not fail if defaultStorage is None" in {
      CategorySerializer.deserialize(categoryWithoutDefaultStorageJSON) must beEqualTo(Category("Food", CategoryType.INCOME, None, Some(1)))
    }
    "not fail if defaultStorage is Some" in {
      CategorySerializer.deserialize(categoryJSON) must beEqualTo(Category("Food", CategoryType.INCOME, Some(storage()), Some(1)))
    }
  }

  "deserializeAll" should {
    "not fail" in {
      val result = CategorySerializer.deserializeAll(categoriesJSON)
      result must not beEmpty
    }
  }

  "serialize" should {
    "not fail if id is Some" in {
      CategorySerializer.serializePretty(category(Some(1))) must equalTo(categoryWithoutDefaultStorageJSON)
    }
    "not fail if id is None" in {
      CategorySerializer.serializePretty(category()) must equalTo(categoryWithoutIdAndDefaultStorageJSON)
    }
    "not fail if id is Some and defaultStorage is Some" in {
      CategorySerializer.serializePretty(category(id = Some(1), defaultStorage = Some(storage(Some(1))))) must equalTo(categorySerializedJSON)
    }
  }
}