package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model

/**
 * Factory object for CLI. Injects configuration.
 *
 * @author konstantin_grigoriev
 */
object CLIFactory {
  def apply(configuration: Configuration, cli: String): CLI[Model] = cli match {
    case "category" => new CategoryCLI(configuration).asInstanceOf[CLI[Model]]
    case "storage" => new StorageCLI(configuration).asInstanceOf[CLI[Model]]
    case "expense" => new ExpenseCLI(configuration).asInstanceOf[CLI[Model]]
    case _ => throw new UnsupportedOperationException("Unknown cli : " + cli)
  }
}
