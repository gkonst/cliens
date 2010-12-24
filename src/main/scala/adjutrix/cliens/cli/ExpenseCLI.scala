package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Expense

/**
 * CLI implementation for 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseCLI(configuration: Configuration) extends CLI[Expense](configuration: Configuration) {
    override val summary = Array("id", "sum", "category.name", "desc", "operation_date", "storage.name", "place")
}