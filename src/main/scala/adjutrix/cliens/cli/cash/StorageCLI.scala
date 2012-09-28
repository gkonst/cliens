package adjutrix.cliens.cli.cash

import adjutrix.cliens.model._
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.cli.CLI

/**
 * CLI implementation for 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageCLI(implicit configuration: Configuration) extends CLI[Storage] {

  override def header = super.header + String.format("%-25s %-20s %-20s", "Name", "Amount", "Type")

  override def rowSummary(item: Storage) = {
    super.rowSummary(item) + String.format("%-25s %-20s %-20s", item.name, item.amount + " " + item.currencyType.abbr, item.storageType.name)
  }

  def create() = throw new UnsupportedOperationException()
}