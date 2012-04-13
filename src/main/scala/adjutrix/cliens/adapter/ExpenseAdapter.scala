package adjutrix.cliens.adapter

import adjutrix.cliens.model.Model
import adjutrix.cliens.conf.Configurable

/**
 * Adapter impl for working with 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseAdapter extends Adapter[Model] {
  this: Configurable =>
  val baseUrl = "expense"
}