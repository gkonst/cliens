package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.Storage
import net.liftweb.json.JsonAST.{JString, JDouble, JField, JValue}

object StorageSerializer extends JSONSerializer[Storage] {
  override def transformToJSON(json: JValue) =
    json transform {
      case JField("storageType", x) => JField("type", x)
      case JField("currencyType", x) => CurrencyTypeSerializer.transformToJSON(JField("currency_type", x))
      case JField("amount", JDouble(x)) => JField("amount", JString(x.toString))
    }

  override def transformToEntity(json: JValue) =
    json transform {
      case JField("type", x) => JField("storageType", x)
      case JField("currency_type", x) => CurrencyTypeSerializer.transformToEntity(JField("currencyType", x))
      case JField("amount", JString(x)) => JField("amount", JDouble(x.toDouble))
    }
}

