package adjutrix.cliens.cli

import adjutrix.cliens.model.Category
import adjutrix.cliens.model.ModelsFactory._

class CategoryCLISpec extends CLISpec[Category] {

  lazy val cli = new CategoryCLI()

  def givenModel = category(Some(4))

  def expectedRowSummaryValues = List("4", "Food", "INCOME")

  def expectedHeaderValues = List("Id", "Name", "Type", "Default Storage")
}