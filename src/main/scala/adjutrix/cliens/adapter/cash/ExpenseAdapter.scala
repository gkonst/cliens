package adjutrix.cliens.adapter.cash

import adjutrix.cliens.conf.Configurable
import adjutrix.cliens.model.Expense
import adjutrix.cliens.adapter.Adapter
import adjutrix.cliens.adapter.protocol.Protocol

trait ExpenseAdapter extends Adapter[Expense] {
  self: Configurable with Protocol[Expense] =>

  val baseUrl = "expense"
}