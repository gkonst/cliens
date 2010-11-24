package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration

/**
 * Adapter impl for working with 'Category'.
 *
 * @author konstantin_grigoriev
 */
case class CategoryAdapter(configuration: Configuration) extends Adapter(configuration: Configuration) {
    val url = "category"

    def findExpenseCategories = processGet(Array[String](), "?type=0")
}