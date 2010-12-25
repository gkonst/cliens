package adjutrix.cliens.adapter

import adjutrix.cliens.adapter.AdapterSpec._
import adjutrix.cliens.model.{CategoryType, Category}

/**
 * Integration test for {@link CategoryAdapter}.
 *
 * @author Konstantin_Grigoriev
 */
object CategoryAdapterSpec extends AdapterSpecification[Category, CategoryAdapter]("category") {


    override val fixtureId = 40

    def checkFields(result: Category) = null

    def createModel = new Category(name = "TestCategory",
        categoryType = CategoryType.EXPENSE)

    "CategoryAdapter.findExpenseCategories" should {
        val result = adapter.findExpenseCategories
        "return Some of List" in {
            result must beSome[List[_]]
        }
        "return entities with EXPENSE CategoryType" in {
            result.get.foreach(entity => entity.categoryType must beEqualTo(CategoryType.EXPENSE))
        }
    }
}
