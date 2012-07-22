package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model._
import net.liftweb.json.JsonAST.{JDouble, JInt, JValue}
import serializer.Serializer
import text.Document
import java.io.StringWriter
import net.liftweb.json._
import ext.EnumSerializer

abstract class JSONSerializer[T <: Model](implicit mf: Manifest[T]) extends Serializer[T] {
  implicit val formats = DefaultFormats + BigDecimalSerializer + new EnumSerializer(CategoryType)

  val contentType = "application/json; charset=utf-8"

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

trait JSONSerializers {
  implicit val storageSerializer = StorageSerializer
  implicit val categorySerializer = CategorySerializer
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
