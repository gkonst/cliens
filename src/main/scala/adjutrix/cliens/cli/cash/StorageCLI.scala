package adjutrix.cliens.cli.cash

import adjutrix.cliens.model._
import adjutrix.cliens.cli.ListCLI
import adjutrix.cliens.adapter.cash.StorageAdapterComponent

trait StorageCLI extends ListCLI[Storage] {
  self: StorageAdapterComponent =>

  override def header = super.header + String.format("%-25s %-20s %-20s", "Name", "Amount", "Type")

  override def rowSummary(item: Storage) = {
    super.rowSummary(item) + String.format("%-25s %-20s %-20s", item.name, item.amount + " " + item.currencyType.abbr, item.storageType.name)
  }
}