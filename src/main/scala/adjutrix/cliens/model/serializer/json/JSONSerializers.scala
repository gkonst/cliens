package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer.Serializers

trait JSONSerializers extends Serializers {
  implicit val currencyTypeSerializer = CurrencyTypeSerializer
  implicit val storageTypeSerializer = StorageTypeSerializer
  implicit val storageSerializer = StorageSerializer
  implicit val categorySerializer = CategorySerializer
}