package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.{CategoryType, Category}
import CategoryType._

object CategorySerializer extends JSONSerializer[Category] {

  //  override def transformToEntity(json: JValue) =
  //    json transform {
  //      case JField("default_storage", x) => StorageSerializer.transformToEntity(JField("defaultStorage", x))
  //    } transform {
  //      case JField("type", x) => JField("categoryType", x)
  //    }

  override protected def transformToEntity = super.transformToEntity orElse {
    case "categoryType" => ("type", classOf[Int], {
      case v: Int => CategoryType.intToCategoryType(v)
    })
  }: PartialFunction[String, (String, Class[_], Any => Any)]

  override protected def transformToJson = ({
    case ("categoryType", v: CategoryType) => ("type", v.id)
    case ("defaultStorage", v) => ("default_storage", v)
  }: PartialFunction[(String, Any), (String, Any)]) orElse super.transformToJson
}
