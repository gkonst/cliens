package adjutrix.cliens.adapter

import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Storage

/**
 * Adapter impl for working with 'Storage'.
 *
 * @author konstantin_grigoriev
 */
class StorageAdapter(configuration: Configuration) extends Adapter[Storage](configuration: Configuration) {
    val baseUrl = "storage"

    def convertRequestData(entity: Storage) = {
        Some(Map("name" -> entity.name, "amount" -> entity.amount, "currency_type" -> entity.currencyType, "type" -> entity.storageType))
    }

    def convertResponseData(data: Map[String, Any]) = {
        val id = data.get("id").get.asInstanceOf[Double].toInt
        val name = data.get("name").get.asInstanceOf[String]
        val amount = BigDecimal(data.get("amount").get.asInstanceOf[String])
        new Storage(name, 1, 1, amount, id)
    }
}