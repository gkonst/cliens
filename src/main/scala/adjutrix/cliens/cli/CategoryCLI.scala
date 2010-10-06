package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration

/**
 * CLI implementation for 'Category'.
 *
 * @author konstantin_grigoriev
 */
class CategoryCLI(configuration: Configuration) extends CLI(configuration: Configuration) {
    def row(item: Map[Any, Any]) = {
        if (configuration.showFull) {
            println(item)
        } else {
            println("id -> " + item.get("id").get + ", name -> " + item.get("name").get)
        }
    }
}