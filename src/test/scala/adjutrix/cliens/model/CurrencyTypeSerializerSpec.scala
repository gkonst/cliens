package adjutrix.cliens.model

import serializer.CurrencyTypeSerializer

class CurrencyTypeSerializerSpec extends ModelSpec {
  def modelShouldHaveFields(result: CurrencyType, id: Int, name: String, abbr: String, rate: BigDecimal) = {
    modelShouldHaveId(result, id)
    "result must have name equal " + name in {
      result.name must equalTo(name)
    }
    "result must have abbr equal " + abbr in {
      result.abbr must equalTo(abbr)
    }
    "result must have rate equal " + rate in {
      result.rate must equalTo(rate)
    }
  }

  "deserialize" should {
    "return correct result" in {
      val result = CurrencyTypeSerializer.deserialize("""{"name":"Dollar","abbr":"$","rate":1.0,"id":1}""")
      modelShouldHaveFields(result, 1, "Dollar", "$", BigDecimal("1.0"))
    }
  }

  "serialize" should {
    "not fail if id is Some" in {
      val result = CurrencyTypeSerializer.serialize(new CurrencyType("Dollar", "$", BigDecimal("1.0"), Some(1)))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo("""{"name":"Dollar","abbr":"$","rate":1.0,"id":1}""")
      }
    }
    "not fail if id is None" in {
      val result = CurrencyTypeSerializer.serialize(new CurrencyType("Dollar", "$", BigDecimal("1.0")))
      "result must not be empty" in {
        result must not beEmpty
      }
      "result must be correct" in {
        result must equalTo("""{"name":"Dollar","abbr":"$","rate":1.0}""")
      }
    }
  }
}