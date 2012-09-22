package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer.Serializers

trait JSONSerializers extends Serializers {
  implicit val storageSerializer = StorageSerializer
  implicit val categorySerializer = CategorySerializer
}