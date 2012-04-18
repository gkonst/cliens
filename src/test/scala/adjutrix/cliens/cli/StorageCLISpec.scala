package adjutrix.cliens.cli

import adjutrix.cliens.model.Storage
import adjutrix.cliens.model.ModelsFactory._

class StorageCLISpec extends CLISpec[Storage] {

  lazy val cli = new StorageCLI()

  def givenModel = storage(Some(5))

  def expectedRowSummaryValues = List("5", "Parex", "10.0", "$", "Bill")

  def expectedHeaderValues = List("Id", "Name", "Amount", "Type")
}