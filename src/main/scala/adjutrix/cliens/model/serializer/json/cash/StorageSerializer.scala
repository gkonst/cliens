package adjutrix.cliens.model.serializer.json.cash

import adjutrix.cliens.model._
import serializer.json.JSONSerializer

object StorageSerializer extends JSONSerializer[Storage] {
  override protected def transformToEntity = super.transformToEntity orElse {
    case "storageType" => ("type", classOf[Related[_]], {
      case v => v
    })
  }

  override protected def transformToJson = super.transformToJson orElse {
    case ("storageType", v) => ("type", v)
  }
}
