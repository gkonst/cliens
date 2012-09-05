package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.{CategoryType, Category}
import CategoryType._

object CategorySerializer extends JSONSerializer[Category] {

  override protected def transformToEntity = super.transformToEntity orElse {
    case "categoryType" => ("type", classOf[Int], {
      case v: Int => CategoryType.intToCategoryType(v)
    })
  }

  override protected def transformToJson = super.transformToJson orElse {
    case ("categoryType", v: CategoryType) => ("type", v.id)
  }
}
