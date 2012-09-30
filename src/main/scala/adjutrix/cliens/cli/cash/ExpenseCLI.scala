package adjutrix.cliens.cli.cash

import adjutrix.cliens.model.Expense
import adjutrix.cliens.cli.CLI
import adjutrix.cliens.adapter.AdapterComponent

trait ExpenseCLI extends CLI[Expense] {
  self: AdapterComponent[Expense] =>
}
