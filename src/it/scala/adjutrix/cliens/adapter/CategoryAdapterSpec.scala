package adjutrix.cliens.adapter

import adjutrix.cliens.model.{CategoryType, Category}
import adjutrix.cliens.model.ModelsFactory._

class CategoryAdapterSpec extends AdapterSpec[Category] {

  lazy val adapter = new CategoryAdapter()

  "findExpenseCategories" should "return categories with type EXPENCE" in {
    val result = adapter.findExpenseCategories
    result must be('defined)
    result.get foreach (_.categoryType === CategoryType.EXPENSE)
  }

  def createModel = category()

  def specifyFields(result: Category) {
    result.name must not be null
    result.categoryType must not be null
  }
}
