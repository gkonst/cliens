package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model._
import net.liftweb.json.JsonAST.{JDouble, JInt, JValue}
import serializer.Serializer
import text.Document
import java.io.StringWriter
import net.liftweb.json._

abstract class JSONSerializer[T <: Model](implicit mf: Manifest[T]) extends Serializer[T] {
  implicit val formats = DefaultFormats + BigDecimalSerializer + new EnumerationSerializer(CategoryType)

  def deserialize(data: String) = {
    transformToEntity(parse(data)).extract
  }

  def deserializeAll(data: String) = transformToEntity(parse(data)).extract[List[T]]

  def serialize(entity: T) = {
    innerSerialize(entity, Printer.compact[StringWriter])
  }

  def serializePretty(entity: T) = {
    innerSerialize(entity, Printer.pretty[StringWriter])
  }

  private def innerSerialize(entity: T, printerFunction: (Document, StringWriter) => StringWriter) = {
    printerFunction(render(transformToJSON(Extraction.decompose(entity))), new StringWriter()).toString
  }

  protected def transformToJSON(json: JValue) = json

  protected def transformToEntity(json: JValue) = json
}

object JSONSerializer {
  def apply[T <: Model, S <: JSONSerializer[T]](cls: Class[T]): S = {
    if (cls == classOf[Storage]) {
      StorageSerializer.asInstanceOf[S]
    } else if (cls == classOf[Category]) {
      CategorySerializer.asInstanceOf[S]
    } else if (cls == classOf[CurrencyType]) {
      CurrencyTypeSerializer.asInstanceOf[S]
    } else if (cls == classOf[StorageType]) {
      StorageTypeSerializer.asInstanceOf[S]
    } else {
      throw new UnsupportedOperationException("Serializer for model " + cls + " not found")
    }
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
    case d: BigDecimal => JDouble(d.doubleValue())
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
