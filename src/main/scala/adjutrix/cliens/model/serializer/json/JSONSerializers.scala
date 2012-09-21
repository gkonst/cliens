package adjutrix.cliens.model.serializer.json

trait JSONSerializers {
  implicit val storageSerializer = StorageSerializer
  implicit val categorySerializer = CategorySerializer
}