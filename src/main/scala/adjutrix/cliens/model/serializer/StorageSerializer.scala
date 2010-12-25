package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.Storage

/**
 * Serializer for 'Storage'.
 *
 * @author Konstantin_Grigoriev
 */
object StorageSerializer extends Serializer[Storage] {
    def deserialize(data: Map[String, Any]) = {
        val id = data.get("id").get.asInstanceOf[Double].toInt
        val name = data.get("name").get.asInstanceOf[String]
        val amount = BigDecimal(data.get("amount").get.asInstanceOf[String])
        new Storage(name, 1, 1, amount, id)
    }

    def serialize(entity: Storage) = Some(Map("name" -> entity.name, "amount" -> entity.amount, "currency_type" -> entity.currencyType, "type" -> entity.storageType))
}

