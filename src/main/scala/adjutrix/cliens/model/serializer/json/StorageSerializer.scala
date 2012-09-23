package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model._

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
