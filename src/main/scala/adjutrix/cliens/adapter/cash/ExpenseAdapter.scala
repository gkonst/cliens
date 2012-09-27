package adjutrix.cliens.adapter.cash

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Expense
import adjutrix.cliens.model.serializer.Serializer
import adjutrix.cliens.adapter.Adapter

/**
 * Adapter impl for working with 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseAdapter(implicit configuration: Configuration,
                     serializer: Serializer[Expense]) extends Adapter[Expense] {
  val baseUrl = "expense"
}