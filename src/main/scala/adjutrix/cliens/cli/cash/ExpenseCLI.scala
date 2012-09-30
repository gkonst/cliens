package adjutrix.cliens.cli.cash

import adjutrix.cliens.model.Expense
import adjutrix.cliens.cli.CLI
import adjutrix.cliens.adapter.AdapterComponent

/**
 * CLI implementation for 'Expense'.
 *
 * @author konstantin_grigoriev
 */
trait ExpenseCLI extends CLI[Expense] {
  self: AdapterComponent[Expense] =>

  def create() = throw new UnsupportedOperationException()
}
