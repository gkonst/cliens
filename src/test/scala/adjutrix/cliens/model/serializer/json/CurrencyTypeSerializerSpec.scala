package adjutrix.cliens.model.serializer.json

import adjutrix.cliens.model.serializer.SerializerSpec
import adjutrix.cliens.model.CurrencyType

class CurrencyTypeSerializerSpec extends SerializerSpec {

  "deserialize" should {
    "return correct result" in {
      CurrencyTypeSerializer.deserialize(
        """{"abbr":"$",
            "name":"Dollar",
            "rate":"1.0",
            "id":"1",
            "resource_uri": "/api/v1/currencytype/1/"}""") must beEqualTo(
        CurrencyType("Dollar", "$", BigDecimal("1.0"), Some(1), Some("/api/v1/currencytype/1/")))
    }
  }

  "serialize" should {
    "not fail if id is Some" in {
      CurrencyTypeSerializer.serialize(CurrencyType("Dollar", "$", BigDecimal("1.0"), Some(1), Some("/api/v1/currencytype/1/"))) must equalTo(
        """{"name":"Dollar","abbr":"$","rate":"1.0","id":"1","resource_uri":"/api/v1/currencytype/1/"}""")
    }
    "not fail if id is None" in {
      CurrencyTypeSerializer.serialize(CurrencyType("Dollar", "$", BigDecimal("1.0"))) must equalTo(
        """{"name":"Dollar","abbr":"$","rate":"1.0"}""")
    }
  }
}