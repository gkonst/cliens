package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer.SerializerSpec
import adjutrix.cliens.model.CategoryType.CategoryType
import adjutrix.cliens.model.{Storage, CategoryType, Category}
import adjutrix.cliens.model.ModelsFactory.{storage, category}

class CategorySerializerSpec extends SerializerSpec {

  lazy val categoryWithoutDefaultStorageJSON = loadFileFromClasspathToString("/categoryWithoutDefaultStorage.json")
  lazy val categoryWithoutIdAndDefaultStorageJSON = loadFileFromClasspathToString("/categoryWithoutIdAndDefaultStorage.json")
  lazy val categoryJSON = loadFileFromClasspathToString("/category.json")
  lazy val categorySerializedJSON = loadFileFromClasspathToString("/categorySerialized.json")
  lazy val categoriesJSON = loadFileFromClasspathToString("/categories.json")

  def modelShouldHaveFields(result: Category, id: Int, name: String, categoryType: CategoryType) = {
    modelShouldHaveId(result, id)
    "result must have name equal " + name in {
      result.name must equalTo(name)
    }
    "result must have categoryType equal " + categoryType in {
      result.categoryType must equalTo(categoryType)
    }
  }

  "deserialize" should {
    "not fail if defaultStorage is None" in {
      val result = CategorySerializer.deserialize(categoryWithoutDefaultStorageJSON)
      modelShouldHaveFields(result, 1, "Food", CategoryType.INCOME)
    }
    "not fail if defaultStorage is Some" in {
      val result = CategorySerializer.deserialize(categoryJSON)
      modelShouldHaveFields(result, 1, "Food", CategoryType.INCOME)
      "result must have defaultStorage and be Some[Storage]" in {
        result.defaultStorage must beSome[Storage] and (result.defaultStorage.get.id must beEqualTo(Some(1)))
      }
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
      val result = CategorySerializer.serializePretty(category(Some(1)))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo(categoryWithoutDefaultStorageJSON)
      }
    }
    "not fail if id is None" in {
      val result = CategorySerializer.serializePretty(category())
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo(categoryWithoutIdAndDefaultStorageJSON)
      }
    }
    "not fail if id is Some and defaultStorage is Some" in {
      val result = CategorySerializer.serializePretty(category(id = Some(1), defaultStorage = Some(storage(Some(1)))))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo(categorySerializedJSON)
      }
    }
  }
}