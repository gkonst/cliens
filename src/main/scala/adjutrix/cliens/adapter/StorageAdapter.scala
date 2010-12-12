package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration

/**
 * Adapter impl for working with 'Storage'.
 *
 * @author konstantin_grigoriev
 */
case class StorageAdapter(configuration: Configuration) extends Adapter(configuration: Configuration) {
    val baseUrl = "storage"
}