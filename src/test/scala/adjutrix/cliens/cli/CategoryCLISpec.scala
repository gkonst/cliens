package adjutrix.cliens.cli

import adjutrix.cliens.model.ModelsFactory._
import adjutrix.cliens.model.{CategoryType, Category}

class CategoryCLISpec extends CLISpec[Category] {

  def produceCLI = new CategoryCLI with MockedIO

  def givenModel(id: Int) = category(Some(id), CategoryType.EXPENSE)

  val expectedHeader = "---------------------------------------------------------\n" +
    "Id   Type       Name                 Default Storage     \n" +
    "---------------------------------------------------------\n"

  val expectedSomeForRow = "1    EXPENSE    Food                 None                \n"

  val expectedSomeForList = "1    EXPENSE    Food                 None                \n" +
    "2    EXPENSE    Food                 None                \n"

  "create should return correct result" in new CLIScope {
    cli.stringToIn("Foo\n1\n")
    cli.create() must beEqualTo(Category("Foo", CategoryType.INCOME))
  }
}