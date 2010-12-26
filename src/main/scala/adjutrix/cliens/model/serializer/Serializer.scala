package adjutrix.cliens.model.serializer

import adjutrix.cliens.model._

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
        } else if (cls == classOf[StorageType]) {
            StorageTypeSerializer.asInstanceOf[Serializer[T]]
        } else {
            throw new UnsupportedOperationException("Serializer for model " + cls + " not found")
        }
    }
}

trait Serializer[T <: Model] {
    def serialize(entity: T): Map[String, Any]

    def deserialize(data: Map[String, Any]): T

    def getId(data: Map[String, Any]) = data.get("id").get match {
        case x: Int => x
        case x: Double => x.toInt
        case x => throw new IllegalArgumentException("Unknown id type : " + x.asInstanceOf[Object].getClass)
    }
}