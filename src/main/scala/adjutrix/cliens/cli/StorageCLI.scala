package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Storage

/**
 * CLI implementation for 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageCLI(configuration: Configuration) extends CLI[Storage](configuration: Configuration) {
    val summary = Array("id", "name", "amount", "currency_type.name", "type.name")
}