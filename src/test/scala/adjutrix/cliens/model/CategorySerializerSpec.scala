package adjutrix.cliens.model

import CategoryType._
import serializer.CategorySerializer
import ModelsFactory._
import net.liftweb.json._

class CategorySerializerSpec extends ModelSpec {

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
      val result = CategorySerializer.deserialize("""{"name" : "Food", "id" : 1, "categoryType" : 1}""")
      modelShouldHaveFields(result, 1, "Food", CategoryType.INCOME)
    }
    "not fail if defaultStorage is Some" in {
      val data = """{"name" : "Food", "id" : 1, "categoryType" : 1,
                     "defaultStorage" : {"name" : " Parex", "storageType" : {"name" : "Bill", "id" : 1},
                                         "currencyType" : {"name" : "Dollar", "abbr" : "$", "rate" : 1.0, "id" : 1},
                                         "amount" : 10.0,
                                         "id" : 1}}"""
      val result = CategorySerializer.deserialize(data)
      modelShouldHaveFields(result, 1, "Food", CategoryType.INCOME)
      "result must have defaultStorage and be Some[Storage]" in {
        result.defaultStorage must beSome[Storage]
        "with id equal 1" in {
          result.defaultStorage.get.id must beEqualTo(Some(1))
        }
      }
    }
  }

  "serialize" should {
    "not fail if id is Some" in {
      val result = CategorySerializer.serialize(category(Some(1)))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo("""{"name":"Food","categoryType":1,"id":1}""")
      }
    }
    "not fail if id is None" in {
      val result = CategorySerializer.serialize(category())
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo("""{"name":"Food","categoryType":1}""")
      }
    }
    "not fail if id is None and defaultStorage is Some" in {
      val result = CategorySerializer.serialize(category(id = None, defaultStorage = Some(storage(Some(2)))))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo(compact(render(parse("""{"name" : "Food", "categoryType" : 1,
                                "defaultStorage" : {"name" : "Parex", "storageType" : {"name" : "Bill", "id" : 1},
                                                    "currencyType" : {"name" : "Dollar", "abbr" : "$", "rate" : 1.0, "id" : 1},
                                                    "amount" : 10.0,
                                                    "id" : 2}}"""))))
      }
    }
  }
}