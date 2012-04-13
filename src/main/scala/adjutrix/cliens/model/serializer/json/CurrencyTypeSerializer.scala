package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.CurrencyType
import net.liftweb.json.JsonAST.{JString, JDouble, JField, JValue}

object CurrencyTypeSerializer extends JSONSerializer[CurrencyType] {
  override def transformToJSON(json: JValue) =
    json transform {
      case JField("rate", JDouble(x)) => JField("rate", JString(x.toString))
    }

  override def transformToEntity(json: JValue) =
    json transform {
      case JField("rate", JString(x)) => JField("rate", JDouble(x.toDouble))
    }
}
