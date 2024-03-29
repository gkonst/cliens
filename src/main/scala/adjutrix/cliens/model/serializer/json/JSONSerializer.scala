package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model._
import serializer.Serializer
import org.codehaus.jackson.map._
import module.SimpleModule
import org.codehaus.jackson._
import org.codehaus.jackson.JsonParser
import com.codahale.jerkson.{Json => JerksonJson}
import java.io.{Writer, StringWriter}
import org.codehaus.jackson.util.DefaultPrettyPrinter
import grizzled.slf4j.Logging
import java.lang.reflect.Field
import scala.collection.JavaConversions._

abstract class JSONSerializer[T <: Model](implicit mf: Manifest[T]) extends Serializer[T] with Logging {
  private val klass = mf.erasure.asInstanceOf[Class[T]]
  private val fields = getFields(klass)

  val contentType = "application/json; charset=utf-8"

  class DefaultJacksonDeserializer[A <: Product](klass: Class[A]) extends JsonDeserializer[A] {
    private val constructor = klass.getConstructors.head

    def deserialize(jp: JsonParser, ctxt: DeserializationContext) = {
      if (jp.getCurrentToken == JsonToken.START_OBJECT) {
        jp.nextToken()
      }

      if (jp.getCurrentToken != JsonToken.FIELD_NAME &&
        jp.getCurrentToken != JsonToken.END_OBJECT) {
        throw ctxt.mappingException(klass)
      }

      val nodes = jp.readValueAsTree
      val values = fields.map {
        field =>
          logger.trace("deserializing field..." + field.getName)
          // TODO refactor trandform, so we could re-use field renames
          val (name, fieldType, valueConversion) = if (transformToEntity.isDefinedAt(field.getName)) {
            transformToEntity(field.getName)
          } else {
            (snakify(field.getName), getFieldType(field), handleOption(field))
          }

          val value = getTreeParser(nodes.get(name), jp).readValueAs(fieldType)
          toAnyRef(valueConversion(value))
      }
      constructor.newInstance(values.toArray: _*).asInstanceOf[A]
    }
  }

  def handleOption(field: Field): Any => Any = {
    if (isOption(field)) {
      x => Option(x)
    } else {
      x => x
    }
  }

  protected def transformToEntity: PartialFunction[String, (String, Class[_], (Any) => Any)] = {
    case "resourceURI" => ("resource_uri", classOf[String], {
      case v: String => Some(v)
    })
    case "id" => ("id", classOf[String], {
      case v: String => Some(v.toInt)
    })
  }

  class DefaultSerializer[A <: Product](klass: Class[A]) extends JsonSerializer[A] {

    private val methods = (for (m <- klass.getDeclaredMethods if m.getParameterTypes.isEmpty) yield m.getName -> m).toMap

    def serialize(value: A, json: JsonGenerator, provider: SerializerProvider) {
      json.writeStartObject()
      for (field <- fields) {
        // TODO refactor serialize
        val fieldValue: Object = methods.get(field.getName).map {
          _.invoke(value)
        }.getOrElse(field.get(value))
        if (fieldValue != None) {
          val (newFieldName, newFieldValue) = if (transformToJson.isDefinedAt(field.getName, fieldValue)) {
            transformToJson(field.getName, fieldValue)
          } else {
            (snakify(field.getName), fieldValue)
          }
          provider.defaultSerializeField(newFieldName, newFieldValue, json)
        }
      }
      json.writeEndObject()
    }

    override def handledType() = klass
  }

  protected def transformToJson: PartialFunction[(String, Any), (String, Any)] = {
    case ("resourceURI", x) => ("resource_uri", x)
    case ("id", Some(id)) => ("id", id.toString)
    case (name, x: BigDecimal) => (name, x.toString())
  }

  object Json extends JerksonJson {
    mapper.registerModule(new SimpleModule("CliensModule", new Version(0, 1, 0, "alpha"))
      .addSerializer(new DefaultSerializer(klass))
      .addDeserializer(klass, new DefaultJacksonDeserializer(klass))
      .addDeserializer(classOf[Related[_]], new RelatedDeserializer)
      .addSerializer(classOf[Related[_]], new RelatedSerializer))

    def prettyPrint[A](obj: A): String = {
      val writer = new StringWriter
      prettyPrint(obj, writer)
      writer.toString
    }

    def prettyPrint[A](obj: A, output: Writer) {
      prettyPrint(obj, factory.createJsonGenerator(output))
    }

    private def prettyPrint[A](obj: A, generator: JsonGenerator) {
      val printer = new DefaultPrettyPrinter
      printer.spacesInObjectEntries(false)
      generator.setPrettyPrinter(printer)
      generator.writeObject(obj)
      generator.close()
    }

    def parsePack[A <: Model](input: String)(implicit mf: Manifest[A]): Pack[A] = {
      val jp = factory.createJsonParser(input)
      val tree = jp.readValueAsTree()
      val meta = tree.get("meta")
      val treeParser = getTreeParser(tree.get("objects"), jp)
      val objectsList = treeParser.readValuesAs(mf.erasure).toList.asInstanceOf[List[A]]
      Pack[A](meta.get("next").asText(), meta.get("previous").asText(), objectsList)
    }

  }

  def deserialize(data: String) = Json.parse[T](data)

  def deserializeAll(data: String) = Json.parsePack[T](data)

  def serialize(entity: T) = Json.generate(entity)

  def serializePretty(entity: T) = Json.prettyPrint(entity)
}
