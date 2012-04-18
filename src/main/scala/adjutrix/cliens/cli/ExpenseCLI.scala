package adjutrix.cliens.cli

import adjutrix.cliens.model.Expense
import adjutrix.cliens.conf.Configuration

/**
 * CLI implementation for 'Expense'.
 *
 * @author konstantin_grigoriev
 */
class ExpenseCLI(implicit configuration: Configuration) extends CLI[Expense](configuration)
