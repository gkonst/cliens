package adjutrix.cliens.adapter

import adjutrix.cliens.adapter.AdapterSpec._
import adjutrix.cliens.model.{Model, CategoryType, Category}

/**
 * Integration test for {@link CategoryAdapter}.
 *
 * @author Konstantin_Grigoriev
 */
object CategoryAdapterSpec extends AdapterSpecification[Category, CategoryAdapter]("category") {

    def checkFields(result: Category) = {
        "result must have name" in {
            result.name must notBeNull
        }
        "result must have type" in {
            result.categoryType must notBeNull
        }
    }

    def createModel = new Category(name = "TestCategory",
        categoryType = CategoryType.EXPENSE)

    "CategoryAdapter.findExpenseCategories" should {
        val result = adapter.findExpenseCategories
        "return Some of List" in {
            result must beSome[List[_ <: Model]]
        }
        "return entities with EXPENSE CategoryType" in {
            result.get.foreach(entity => entity.categoryType must beEqualTo(CategoryType.EXPENSE))
        }
    }
}
