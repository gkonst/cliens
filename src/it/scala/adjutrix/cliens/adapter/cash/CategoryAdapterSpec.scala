package adjutrix.cliens.adapter.cash

import adjutrix.cliens.model.{CategoryType, Category}
import adjutrix.cliens.model.ModelsFactory._
import adjutrix.cliens.adapter.AdapterSpec
import adjutrix.cliens.conf.PropertiesConfigurable

class CategoryAdapterSpec extends AdapterSpec[Category] {

  lazy val adapter = new CategoryAdapter with PropertiesConfigurable with CategoryProtocol

  it should behave like findAllIsDefined()

  it should behave like findByIdIsDefined(fixtureId = 1)

  "findExpenseCategories" should "return categories with type EXPENCE" in {
    val result = adapter.findExpenseCategories
    result must be('right)
    result.right.get must be('defined)
    result.right.get.get foreach (_.categoryType === CategoryType.EXPENSE)
  }

  it should behave like createIsDefined(given = category(None))

  it should behave like deleteIsDefined(given = category(None))

  def specifyFields(result: Category) {
    result.name must not be null
    result.categoryType must not be null
  }
}
