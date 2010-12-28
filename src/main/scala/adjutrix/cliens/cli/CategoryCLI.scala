package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Category

/**
 * CLI implementation for 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryCLI(configuration: Configuration) extends CLI[Category](configuration: Configuration) {
    override def header = super.header + String.format("%-10s %-20s %-20s", "Type", "Name", "Default Storage")

    override def rowSummary(item: Category) = {
        super.rowSummary(item) + String.format("%-10s %-20s %-20s", item.categoryType, item.name,
            item.defaultStorage match {
                case Some(x) => x.name
                case None => None
            })
    }
}