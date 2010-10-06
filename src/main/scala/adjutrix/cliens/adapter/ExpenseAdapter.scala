package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration

/**
 * Adapter impl for working with 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseAdapter(configuration: Configuration) extends Adapter(configuration: Configuration) {
    val url = "expense"
}