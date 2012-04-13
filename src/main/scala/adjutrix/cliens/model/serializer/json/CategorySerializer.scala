package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.Category
import net.liftweb.json._
import net.liftweb.json.JsonAST.JValue

object CategorySerializer extends JSONSerializer[Category] {

  override def transformToEntity(json: JValue) =
    json transform {
      case JField("default_storage", x) => StorageSerializer.transformToEntity(JField("defaultStorage", x))
    } transform {
      case JField("type", x) => JField("categoryType", x)
    }

  override def transformToJSON(json: JValue) = json transform {
    case JField("defaultStorage", x) => StorageSerializer.transformToJSON(JField("default_storage", x))
  } transform {
    case JField("categoryType", x) => JField("type", x)
  }
}