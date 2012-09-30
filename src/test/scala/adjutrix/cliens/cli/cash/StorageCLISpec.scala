package adjutrix.cliens.cli.cash

import adjutrix.cliens.cli.ReaderCLISpec
import adjutrix.cliens.model.Storage
import adjutrix.cliens.adapter.cash.StorageAdapterComponent
import adjutrix.cliens.model.ModelsFactory._

class StorageCLISpec extends ReaderCLISpec[Storage] {

  def produceCLI = {
    val cli = new StorageCLI with MockedIO with StorageAdapterComponent {
      override lazy val adapter = mock[StorageAdapter]
    }
    (cli, cli.adapter)
  }

  def givenModel(id: Int) = storage(Some(id))

  val expectedHeader = "------------------------------------------------------------------------\n" +
    "Id   Name                      Amount               Type                \n" +
    "------------------------------------------------------------------------\n"

  val expectedSomeForRow = "1    Parex                     10.0 $               Bill                \n"

  val expectedSomeForList = "1    Parex                     10.0 $               Bill                \n" +
    "2    Parex                     10.0 $               Bill                \n"
}