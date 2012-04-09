package adjutrix.cliens.cli

import CLISpec.CLISpec
import adjutrix.cliens.model.Storage
import adjutrix.cliens.model.ModelsFactory._

object StorageCLISpec extends CLISpec[Storage]("storage") {
  def givenModel = storage(Some(5))

  def expectedRowSummaryValues = List("5", "Parex", "10.0", "$", "Bill")

  def expectedHeaderValues = List("Id", "Name", "Amount", "Type")
}