package adjutrix.cliens.adapter.cash

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Category
import adjutrix.cliens.model.serializer.Serializer
import adjutrix.cliens.adapter.Adapter

/**
 * Adapter impl for working with 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryAdapter(implicit configuration: Configuration,
                      serializer: Serializer[Category]) extends Adapter[Category] {
  val baseUrl = "category"

  def findExpenseCategories = find(Some(Map("type" -> 0)))
}