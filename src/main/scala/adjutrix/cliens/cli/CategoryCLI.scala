package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Category

/**
 * CLI implementation for 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryCLI(configuration: Configuration) extends CLI[Category](configuration: Configuration) {
    val summary = Array("id", "name", "type", "default_storage.name")
}