package adjutrix.cliens.cli

import adjutrix.cliens.cli.CLISpec.CLISpec
import adjutrix.cliens.model.Category
import adjutrix.cliens.model.ModelsFactory._

object CategoryCLISpec extends CLISpec[Category]("category") {
  def givenModel = category(Some(4))

  def expectedRowSummaryValues = List("4", "Food", "INCOME")

  def expectedHeaderValues = List("Id", "Name", "Type", "Default Storage")
}