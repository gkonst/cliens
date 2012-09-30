package adjutrix.cliens.cli.cash

import adjutrix.cliens.cli.ReaderCLISpec
import adjutrix.cliens.model.{CategoryType, Category}
import adjutrix.cliens.adapter.cash.CategoryAdapterComponent
import adjutrix.cliens.model.ModelsFactory._


class CategoryCLISpec extends ReaderCLISpec[Category] {

  def produceCLI = {
    val cli = new CategoryCLI with MockedIO with CategoryAdapterComponent {
      override lazy val adapter = mock[CategoryAdapter]
    }
    (cli, cli.adapter)
  }

  def givenModel(id: Int) = category(Some(id), CategoryType.EXPENSE)

  val expectedHeader = "---------------------------------------------------------\n" +
    "Id   Type       Name                 Default Storage     \n" +
    "---------------------------------------------------------\n"

  val expectedSomeForRow = "1    EXPENSE    Food                 None                \n"

  val expectedSomeForList = "1    EXPENSE    Food                 None                \n" +
    "2    EXPENSE    Food                 None                \n"
}