package adjutrix.cliens.cli

import adjutrix.cliens.model.Expense
import adjutrix.cliens.conf.Configurable

/**
 * CLI implementation for 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseCLI(options: CLIOption) extends CLI[Expense](options) {
  this: Configurable =>

}