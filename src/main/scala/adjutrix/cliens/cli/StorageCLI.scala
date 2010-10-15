package adjutrix.cliens.cli

import adjutrix.cliens.conf.Configuration

/**
 * CLI implementation for 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageCLI(configuration: Configuration) extends CLI(configuration: Configuration) {
    override val summary = Array("id", "name", "amount", "currency_type.name", "type.name")
}