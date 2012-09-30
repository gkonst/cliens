package adjutrix.cliens.model.serializer.json

import org.codehaus.jackson.map.{DeserializationContext, JsonDeserializer, SerializerProvider, JsonSerializer}
import org.codehaus.jackson.{JsonParser, JsonGenerator}
import adjutrix.cliens.model._

class RelatedSerializer extends JsonSerializer[Related[_]] {
  def serialize(value: Related[_], jgen: JsonGenerator, provider: SerializerProvider) {
    jgen.writeString(value.resourceURI)
  }
}

class RelatedDeserializer extends JsonDeserializer[Related[_]] {
  def deserialize(jp: JsonParser, ctxt: DeserializationContext) = {
    jp.getText
  }
}