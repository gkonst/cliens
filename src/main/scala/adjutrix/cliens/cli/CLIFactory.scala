package adjutrix.cliens.cli

import adjutrix.cliens.model.Model
import adjutrix.cliens.conf.PropertiesConfigurable

/**
 * Factory object for CLI. Injects configuration.
 *
 * @author konstantin_grigoriev
 */
object CLIFactory {
  def apply(cli: String, options: CLIOption): CLI[Model] = cli match {
    case "category" => (new CategoryCLI(options) with PropertiesConfigurable).asInstanceOf[CLI[Model]]
    case "storage" => (new StorageCLI(options) with PropertiesConfigurable).asInstanceOf[CLI[Model]]
    case "expense" => (new ExpenseCLI(options) with PropertiesConfigurable).asInstanceOf[CLI[Model]]
    case _ => throw new UnsupportedOperationException("Unknown cli : " + cli)
  }
}
