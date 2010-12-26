package adjutrix.cliens.model

import CategoryType._
import serializer.{CurrencyTypeSerializer, StorageTypeSerializer, CategorySerializer}

/**
 * Specification for {@link Category}.
 *
 * @author Konstantin_Grigoriev
 */

object CategorySpec extends ModelSpec {
    def checkFields(result: Category, id: Int, name: String, categoryType: CategoryType) {
        checkId(result, id)
        "result must have name and equal " + name in {
            result.name must (notBeNull and equalTo(name))
        }
    }

    "deserialize" should {
        "not fail if defaultStorage is None" in {
            val result = CategorySerializer.deserialize(Map("name" -> "Food", "id" -> 1d, "type" -> CategoryType.INCOME.id))
            checkFields(result, 1, "Food", CategoryType.INCOME)
        }
        "not fail if defaultStorage is Some" in {
            val storage = Map("name" -> " Parex", "type" -> StorageTypeSerializer.serialize(StorageSpec.storageType),
                "currency_type" -> CurrencyTypeSerializer.serialize(StorageSpec.currencyType),
                "amount" -> "10.0",
                "id" -> 1)
            val result = CategorySerializer.deserialize(Map("name" -> "Food", "id" -> 1d, "type" -> CategoryType.INCOME.id,
                "default_storage" -> storage))
            checkFields(result, 1, "Food", CategoryType.INCOME)
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
            var result = CategorySerializer.serialize(new Category("Food", CategoryType.INCOME, id = Some(1)))
            "result must not be empty" in {
                result must notBeEmpty
            }
            checkPair(result, "id", 1)
            checkPair(result, "name", "Food")
            checkPair(result, "type", CategoryType.INCOME.id)
        }
        "not fail if id is None" in {
            var result = CategorySerializer.serialize(new Category("Food", CategoryType.INCOME))
            "result must not be empty" in {
                result must notBeEmpty
            }
            result must notHaveKey("id")
            checkPair(result, "name", "Food")
            checkPair(result, "type", CategoryType.INCOME.id)
        }
        "not fail if id is None and defaultStorage is Some" in {
            val storage = new Storage("Parex", StorageSpec.storageType, StorageSpec.currencyType, 10.0, Some(2))
            val result = CategorySerializer.serialize(new Category("Food", CategoryType.INCOME, Some(storage)))
            "result must not be empty" in {
                result must notBeEmpty
            }
            result must notHaveKey("id")
            checkPair(result, "name", "Food")
            checkPair(result, "type", CategoryType.INCOME.id)
            checkPair(result, "default_storage", 2)
        }
    }
}