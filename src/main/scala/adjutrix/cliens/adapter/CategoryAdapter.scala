package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.{CategoryType, Category}

/**
 * Adapter impl for working with 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryAdapter(configuration: Configuration) extends Adapter[Category](configuration: Configuration) {
    val baseUrl = "category"


    def convertRequestData(entity: Category) = {
        Some(Map("name" -> entity.name, "type" -> entity.categoryType.id, "default_storage_id" -> entity.defaultStorageId))
    }

    def convertResponseData(data: Map[String, Any]) = {
        val id = data.get("id").get.asInstanceOf[Double].toInt
        val name = data.get("name").get.asInstanceOf[String]
        val categoryType = CategoryType.intToCategoryType(data.get("type").get.asInstanceOf[Double].toInt)
        new Category(name, categoryType, id = id)
    }

    def findExpenseCategories = executeGet(absoluteBaseUrl, Option(Map[String, Any]("type" -> 0)))
}