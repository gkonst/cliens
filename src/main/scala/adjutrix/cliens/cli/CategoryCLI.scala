package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration

/**
 * CLI implementation for 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryCLI(configuration: Configuration) extends CLI(configuration: Configuration) {
    override val summary = Array("id", "name", "type", "default_storage")
}