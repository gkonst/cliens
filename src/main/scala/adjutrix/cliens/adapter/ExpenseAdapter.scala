package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Expense
import adjutrix.cliens.model.serializer.Serializer

/**
 * Adapter impl for working with 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseAdapter(implicit configuration: Configuration,
                     serializer: Serializer[Expense]) extends Adapter[Expense] {
  val baseUrl = "expense"
}