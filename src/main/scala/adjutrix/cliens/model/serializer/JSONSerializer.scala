package adjutrix.cliens.model.serializer

import adjutrix.cliens.model._
import net.liftweb.json.JsonAST.{JDouble, JInt, JValue}
import net.liftweb.json._

object JSONSerializer {
  def apply[T <: Model](cls: Class[T]): JSONSerializer[T] = {
    if (cls == classOf[Storage]) {
      StorageSerializer.asInstanceOf[JSONSerializer[T]]
    } else if (cls == classOf[Category]) {
      CategorySerializer.asInstanceOf[JSONSerializer[T]]
    } else if (cls == classOf[CurrencyType]) {
      CurrencyTypeSerializer.asInstanceOf[JSONSerializer[T]]
    } else if (cls == classOf[StorageType]) {
      StorageTypeSerializer.asInstanceOf[JSONSerializer[T]]
    } else {
      throw new UnsupportedOperationException("JSONSerializer for model " + cls + " not found")
    }
  }
}

abstract class JSONSerializer[T <: Model](implicit mf: Manifest[T]) {
  implicit val formats = DefaultFormats + BigDecimalSerializer + new EnumerationSerializer(CategoryType)

  def deserialize(data: String) = {
    Serialization.read(data)
  }

  def serialize(entity: T) = {
    Serialization.write(entity)
  }
}

object BigDecimalSerializer extends net.liftweb.json.Serializer[BigDecimal] {
  private val BigDecimalClass = classOf[BigDecimal]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), BigDecimal] = {
    case (TypeInfo(BigDecimalClass, _), json) => json match {
      case JInt(iv) => BigDecimal(iv)
      case JDouble(dv) => BigDecimal(dv)
      case value => throw new MappingException("Can't convert " + value + " to " + BigDecimalClass)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case d: BigDecimal => JDouble(d.doubleValue)
  }
}

class EnumerationSerializer[E <: Enumeration : ClassManifest](enum: E) extends net.liftweb.json.Serializer[E#Value] {
  private val EnumerationClass = classOf[E#Value]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), E#Value] = {
    case (TypeInfo(EnumerationClass, _), json) => json match {
      case JInt(value) => enum.values.filter(_.id == value).head
      case value => throw new MappingException("Can't convert " + value + " to " + EnumerationClass)
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case value: E#Value => JInt(value.id)
  }
}
