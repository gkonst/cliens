package adjutrix.cliens.adapter

import adjutrix.cliens.model.Category
import adjutrix.cliens.conf.Configurable

/**
 * Adapter impl for working with 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryAdapter extends Adapter[Category] {
  this: Configurable =>
  val baseUrl = "category"

  def findExpenseCategories = find(Option(Map[String, Any]("type" -> 0)))
}