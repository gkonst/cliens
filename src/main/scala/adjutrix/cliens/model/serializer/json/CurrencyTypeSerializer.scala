package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.CurrencyType

object CurrencyTypeSerializer extends JSONSerializer[CurrencyType] {
  //  override def transformToJSON(json: JValue) =
  //    super.transformToJSON(json) transform {
  //      case JField("rate", JDouble(x)) => JField("rate", JString(x.toString))
  //    }
  //
  //  override def transformToEntity(json: JValue) =
  //    super.transformToEntity(json) transform {
  //      case JField("rate", JString(x)) => JField("rate", JDouble(x.toDouble))
  //    }
}
