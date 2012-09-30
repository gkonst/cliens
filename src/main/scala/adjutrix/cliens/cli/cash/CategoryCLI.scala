package adjutrix.cliens.cli.cash

import adjutrix.cliens.model.Category
import adjutrix.cliens.cli.ListCLI
import adjutrix.cliens.adapter.cash.CategoryAdapterComponent

trait CategoryCLI extends ListCLI[Category] {
  self: CategoryAdapterComponent =>

  override def header = super.header + String.format("%-10s %-20s %-20s", "Type", "Name", "Default Storage")

  override def rowSummary(item: Category) = {
    super.rowSummary(item) + String.format("%-10s %-20s %-20s", item.categoryType, item.name,
      item.defaultStorage match {
        case Some(x) => x.name
        case None => None
      })
  }
}