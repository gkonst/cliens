package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.CurrencyType

/**
 * Serializer for 'CurrencyType'.
 *
 * @author Konstantin_Grigoriev
 */

object CurrencyTypeSerializer extends Serializer[CurrencyType] {
    def deserialize(data: Map[String, Any]) = {
        val id = getId(data)
        val name = data.get("name").get.asInstanceOf[String]
        val abbr = data.get("abbr").get.asInstanceOf[String]
        val rate = BigDecimal(data.get("rate").get.asInstanceOf[String])
        new CurrencyType(name = name,
            abbr = abbr,
            rate = rate,
            id = id)
    }

    def serialize(entity: CurrencyType) = serializeId(entity) ++ Map("name" -> entity.name, "abbr" -> entity.abbr, "rate" -> entity.rate.toString)
}