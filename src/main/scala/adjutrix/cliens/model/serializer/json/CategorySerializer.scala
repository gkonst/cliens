package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.Category

object CategorySerializer extends JSONSerializer[Category] {

  //  override def transformToEntity(json: JValue) =
  //    json transform {
  //      case JField("default_storage", x) => StorageSerializer.transformToEntity(JField("defaultStorage", x))
  //    } transform {
  //      case JField("type", x) => JField("categoryType", x)
  //    }
  //
  //  override def transformToJSON(json: JValue) = json transform {
  //    case JField("defaultStorage", x) => JField("default_storage", x \ "id")
  //  } transform {
  //    case JField("categoryType", x) => JField("type", x)
  //  }
}
