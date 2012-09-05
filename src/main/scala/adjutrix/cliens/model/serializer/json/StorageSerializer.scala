package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.Storage

object StorageSerializer extends JSONSerializer[Storage] {

  //  override protected def transformToEntity = ({
  //    case "storageType" => ("type", {
  //      v => v
  //    })
  //    case "currencyType" => ("currency_type", {
  //      v => v
  //    })
  //  }: PartialFunction[String, (String, (Any) => Any)]) orElse super.transformToEntity

  override protected def transformToJson = ({
    case ("storageType", v) => ("storage_type", v)
    case ("currencyType", v) => ("currency_type", v)
  }: PartialFunction[(String, Any), (String, Any)]) orElse super.transformToJson
}

