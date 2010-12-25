package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

/**
 * Adapter impl for working with 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseAdapter(configuration: Configuration) extends Adapter[Model](configuration: Configuration) {
    val baseUrl = "expense"
}