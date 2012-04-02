package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Category

/**
 * Adapter impl for working with 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryAdapter(implicit configuration: Configuration) extends Adapter[Category](configuration: Configuration) {
  val baseUrl = "category"

  def findExpenseCategories = find(Option(Map[String, Any]("type" -> 0)))
}