package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.Storage
import net.liftweb.json.JsonAST.{JField, JValue}

object StorageSerializer extends JSONSerializer[Storage] {
  override protected def transformToJSON(json: JValue) =
    json transform {
      case JField("storageType", x) => JField("type", x)
      case JField("currencyType", x) => JField("currency_type", x)
    }

  override protected def transformToEntity(json: JValue) =
    json transform {
      case JField("type", x) => JField("storageType", x)
      case JField("currency_type", x) => JField("currencyType", x)
    }
}

