package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration

/**
 * Factory object for CLI. Injects configuration.
 *
 * @author konstantin_grigoriev
 */
object CLIFactory {
    def apply(configuration: Configuration, cli: String) = cli match {
        case "category" => new CategoryCLI(configuration)
        case _ => throw new UnsupportedOperationException("Unknown cli : " + cli)
    }
}