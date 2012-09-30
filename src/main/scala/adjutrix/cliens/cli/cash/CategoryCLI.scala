package adjutrix.cliens.cli.cash

import adjutrix.cliens.model.Category
import adjutrix.cliens.cli.CLI
import adjutrix.cliens.adapter.cash.CategoryAdapterComponent

/**
 * CLI implementation for 'Category'.
 *
 * @author konstantin_grigoriev
 */
trait CategoryCLI extends CLI[Category] {
  self: CategoryAdapterComponent =>

  override def header = super.header + String.format("%-10s %-20s %-20s", "Type", "Name", "Default Storage")

  override def rowSummary(item: Category) = {
    super.rowSummary(item) + String.format("%-10s %-20s %-20s", item.categoryType, item.name,
      item.defaultStorage match {
        case Some(x) => x.name
        case None => None
      })
  }

  def create() = Category(readLine("Name: "), readLine("Type: ").toInt)
}