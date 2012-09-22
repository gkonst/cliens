package adjutrix.cliens.adapter

import adjutrix.cliens.model.{Model, CategoryType, Category}
import adjutrix.cliens.model.ModelsFactory._

class CategoryAdapterSpec extends AdapterSpec[Category] {

  lazy val adapter = new CategoryAdapter()

  "CategoryAdapter.findExpenseCategories" should {
    val result = adapter.findExpenseCategories
    "return Some of List" in {
      result must beSome[Seq[_ <: Model]]
    }
    "return entities with EXPENSE CategoryType" in {
      result.get must have(_.categoryType == CategoryType.EXPENSE)
    }
  }

  def createModel = category()

  def specifyFields(result: Category) = {
    "result must have name" in {
      result.name must not beNull
    }
    "result must have type" in {
      result.categoryType must not beNull
    }
  }
}
