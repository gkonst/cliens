package adjutrix.cliens.model

import serializer.CurrencyTypeSerializer

/**
 * Specification for {@link CurrencyType}.
 *
 * @author Konstantin_Grigoriev
 */

object CurrencyTypeSpec extends ModelSpec {
    def checkFields(result: CurrencyType, id: Int, name: String, abbr: String, rate: BigDecimal) {
        checkId(result, id)
        "result must have name and equal " + name in {
            result.name must (notBeNull and equalTo(name))
        }
        "result must have abbr and equal " + abbr in {
            result.abbr must (notBeNull and equalTo(abbr))
        }
        "result must have rate and equal " + rate in {
            result.rate must (notBeNull and equalTo(rate))
        }
    }

    "deserialize" should {
        val result = CurrencyTypeSerializer.deserialize(Map("name" -> "Dollar", "abbr" -> "$", "rate" -> "1.0", "id" -> 1d))
        checkFields(result, 1, "Dollar", "$", BigDecimal("1.0"))
    }

    "serialize" should {
        "not fail if id is Some" in {
            val result = CurrencyTypeSerializer.serialize(new CurrencyType("Dollar", "$", BigDecimal("1.0"), Some(1)))
            "result must not be empty" in {
                result must notBeEmpty
            }
            checkPair(result, "id", 1)
            checkPair(result, "name", "Dollar")
            checkPair(result, "abbr", "$")
            checkPair(result, "rate", "1.0")
        }
        "not fail if id is None" in {
            val result = CurrencyTypeSerializer.serialize(new CurrencyType("Dollar", "$", BigDecimal("1.0")))
            "result must not be empty" in {
                result must notBeEmpty
            }
            result must notHaveKey("id")
            checkPair(result, "name", "Dollar")
            checkPair(result, "abbr", "$")
            checkPair(result, "rate", "1.0")
        }
    }
}