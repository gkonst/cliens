package adjutrix.cliens.cli

import adjutrix.cliens.model.Storage
import adjutrix.cliens.conf.Configurable

/**
 * CLI implementation for 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageCLI(options: CLIOption) extends CLI[Storage](options) {
  this: Configurable =>

  override def header = super.header + String.format("%-25s %-20s %-20s", "Name", "Amount", "Type")

  override def rowSummary(item: Storage) = {
    super.rowSummary(item) + String.format("%-25s %-20s %-20s", item.name, item.amount + " " + item.currencyType.abbr, item.storageType.name)
  }
}