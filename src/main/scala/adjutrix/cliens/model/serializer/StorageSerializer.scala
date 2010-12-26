package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.{StorageType, CurrencyType, Storage}

/**
 * Serializer for 'Storage'.
 *
 * @author Konstantin_Grigoriev
 */
object StorageSerializer extends Serializer[Storage] {
    def deserialize(data: Map[String, Any]) = {
        val id = getId(data)
        val name = data.get("name").get.asInstanceOf[String]
        val amount = BigDecimal(data.get("amount").get.asInstanceOf[String])
        val storageType = Serializer(classOf[StorageType]).deserialize(data.get("type").get.asInstanceOf[Map[String, Any]])
        val currencyType = Serializer(classOf[CurrencyType]).deserialize(data.get("currency_type").get.asInstanceOf[Map[String, Any]])
        new Storage(name, storageType, currencyType, amount, id)
    }

    def serialize(entity: Storage) = serializeId(entity) ++
            Map("name" -> entity.name,
                "amount" -> entity.amount.toString,
                "currency_type" -> entity.currencyType.id.get,
                "type" -> entity.storageType.id.get)
}

