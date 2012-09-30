package adjutrix.cliens.model.serializer.json.cash

import adjutrix.cliens.model.serializer._
import adjutrix.cliens.model.CategoryType
import adjutrix.cliens.model.ModelsFactory._
import org.specs2.mutable.Specification

class CategorySerializerSpec extends Specification {

  lazy val categoryWithoutDefaultStorageJSON =
    loadFileFromClasspathToString("/json/categoryWithoutDefaultStorage.json")
  lazy val categoryWithIncomeTypeWithoutDefaultStorageJSON =
    loadFileFromClasspathToString("/json/categoryWithIncomeTypeWithoutDefaultStorage.json")
  lazy val categoryWithoutIdAndDefaultStorageJSON =
    loadFileFromClasspathToString("/json/categoryWithoutIdAndDefaultStorage.json")
  lazy val categoryJSON = loadFileFromClasspathToString("/json/category.json")
  lazy val categorySerializedJSON = loadFileFromClasspathToString("/json/categorySerialized.json")
  lazy val categoriesJSON = loadFileFromClasspathToString("/json/categories.json")

  "deserialize" should {
    "not fail if defaultStorage is None" in {
      CategorySerializer.deserialize(categoryWithoutDefaultStorageJSON) must beEqualTo(category(Some(1), CategoryType.EXPENSE))
    }
    "not fail if defaultStorage is Some" in {
      CategorySerializer.deserialize(categoryJSON) must beEqualTo(categoryUnfilled(Some(1), CategoryType.EXPENSE, Some(storage())))
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
    "not fail if id is Some and type is INCOME" in {
      CategorySerializer.serializePretty(category(Some(1), categoryType = CategoryType.INCOME)) must
        equalTo(categoryWithIncomeTypeWithoutDefaultStorageJSON)
    }
    "not fail if id is None" in {
      CategorySerializer.serializePretty(category()) must equalTo(categoryWithoutIdAndDefaultStorageJSON)
    }
    "not fail if id is Some and defaultStorage is Some" in {
      CategorySerializer.serializePretty(category(id = Some(1), defaultStorage = Some(storage(Some(1))))) must equalTo(categorySerializedJSON)
    }
  }
}