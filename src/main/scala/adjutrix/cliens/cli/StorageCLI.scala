package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Storage

/**
 * CLI implementation for 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageCLI(configuration: Configuration) extends CLI[Storage](configuration: Configuration) {
    val summary = Array("id", "name", "amount", "currency_type.name", "type.name")


    override def header = super.header + String.format("%-25s %-20s %-20s", "Name", "Amount", "Type")

    override def rowSummary(item: Storage) = {
        super.rowSummary(item) + String.format("%-25s %-20s %-20s", item.name, item.amount + " " + item.currencyType.abbr, item.storageType.name)
    }
}