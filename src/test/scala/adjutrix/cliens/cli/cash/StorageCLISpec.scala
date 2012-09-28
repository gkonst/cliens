package adjutrix.cliens.cli.cash

import adjutrix.cliens.model.Storage
import adjutrix.cliens.model.ModelsFactory._
import adjutrix.cliens.cli.CLISpec

class StorageCLISpec extends CLISpec[Storage] {

  def produceCLI = new StorageCLI with MockedIO

  def givenModel(id: Int) = storage(Some(id))

  val expectedHeader = "------------------------------------------------------------------------\n" +
    "Id   Name                      Amount               Type                \n" +
    "------------------------------------------------------------------------\n"

  val expectedSomeForRow = "1    Parex                     10.0 $               Bill                \n"

  val expectedSomeForList = "1    Parex                     10.0 $               Bill                \n" +
    "2    Parex                     10.0 $               Bill                \n"
}