package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.Category
import net.liftweb.json._
import net.liftweb.json.JsonAST.JValue

object CategorySerializer extends JSONSerializer[Category] {

  override def transformToEntity(json: JValue) =
    json transform {
      case JField("default_storage", x) => JField("defaultStorage", x) transform {
        case JField("type", xx) => JField("storageType", xx)
        case JField("currency_type", xx) => JField("currencyType", xx)
      }
    } transform {
      case JField("type", x) => JField("categoryType", x)
    }

  override protected def transformToJSON(json: JValue) = json transform {
    case JField("defaultStorage", x) => JField("default_storage", x) transform {
      case JField("storageType", xx) => JField("type", xx)
      case JField("currencyType", xx) => JField("currency_type", xx)
    }
  } transform {
    case JField("categoryType", x) => JField("type", x)
  }
}
