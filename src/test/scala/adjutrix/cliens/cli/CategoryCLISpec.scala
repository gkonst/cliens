package adjutrix.cliens.cli

import adjutrix.cliens.model.Category
import adjutrix.cliens.model.ModelsFactory._

class CategoryCLISpec extends CLISpec[Category] {

  def produceCLI = new CategoryCLI with MockedIO

  def givenModel(id: Int) = category(Some(id))

  val expectedHeader = "---------------------------------------------------------\n" +
    "Id   Type       Name                 Default Storage     \n" +
    "---------------------------------------------------------\n"

  val expectedSomeForRow = "1    INCOME     Food                 None                \n"

  val expectedSomeForList = "1    INCOME     Food                 None                \n" +
    "2    INCOME     Food                 None                \n"
}