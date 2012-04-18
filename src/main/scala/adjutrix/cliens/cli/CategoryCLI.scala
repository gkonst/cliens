package adjutrix.cliens.cli

import adjutrix.cliens.model.Category
import adjutrix.cliens.conf.Configuration

/**
 * CLI implementation for 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryCLI(implicit configuration: Configuration) extends CLI[Category](configuration) {

  override def header = super.header + String.format("%-10s %-20s %-20s", "Type", "Name", "Default Storage")

  override def rowSummary(item: Category) = {
    super.rowSummary(item) + String.format("%-10s %-20s %-20s", item.categoryType, item.name,
      item.defaultStorage match {
        case Some(x) => x.name
        case None => None
      })
  }
}