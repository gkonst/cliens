package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Category

/**
 * Adapter impl for working with 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryAdapter(configuration: Configuration) extends Adapter[Category](configuration: Configuration) {
    val baseUrl = "category"

    def findExpenseCategories = executeGet(absoluteBaseUrl, Option(Map[String, Any]("type" -> 0)))
}