package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.{CurrencyType, Category, Storage, Model}

/**
 * Factory for serializers.
 *
 * @author Konstantin_Grigoriev
 */

object Serializer {
    def apply[T <: Model](cls: Class[T]): Serializer[T] = {
        if (cls == classOf[Storage]) {
            StorageSerializer.asInstanceOf[Serializer[T]]
        } else if (cls == classOf[Category]) {
            CategorySerializer.asInstanceOf[Serializer[T]]
        } else if (cls == classOf[CurrencyType]) {
            CurrencyTypeSerializer.asInstanceOf[Serializer[T]]
        } else {
            throw new UnsupportedOperationException("Serializer for model " + cls + " not found")
        }
    }
}

trait Serializer[T <: Model] {
    def serialize(entity: T): Option[Map[String, Any]]

    def deserialize(data: Map[String, Any]): T

    def getId(data: Map[String, Any]) = data.get("id").get.asInstanceOf[Double].toInt
}