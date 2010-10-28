package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration

/**
 * CLI implementation for 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseCLI(configuration: Configuration) extends CLI(configuration: Configuration) {
    override val summary = Array("id", "sum", "category.name", "desc", "operation_date", "storage.name", "place")
}