package adjutrix.cliens.cli.cash

import adjutrix.cliens.model.Expense
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.cli.CLI

/**
 * CLI implementation for 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseCLI(implicit configuration: Configuration) extends CLI[Expense] {
  def create() = throw new UnsupportedOperationException()
}
