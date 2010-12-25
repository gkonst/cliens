package adjutrix.cliens.model

import org.specs.Specification
import serializer.CurrencyTypeSerializer

/**
 * Specification for {@link CurrencyTypeSerializer}.
 *
 * @author Konstantin_Grigoriev
 */

object CurrencyTypeSpec extends Specification {
    def checkFields(result: CurrencyType, id: Int, name: String, abbr: String, rate: BigDecimal) {
        "result must have id and equal " + id in {
            result.id must (notBeNull and equalTo(id))
        }
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
        var result = CurrencyTypeSerializer.serialize(new CurrencyType("Dollar", "$", BigDecimal("1.0"), 1))
        "result must be Some of Map[String, Any]" in {
            result must beSome[Map[String, Any]]
        }
        def checkPair(key: String, value: Any) {
            "result must have " + key + " and equal " + value in {
                result.get must havePair((key, value))
            }
        }
        checkPair("id", 1)
        checkPair("name", "Dollar")
        checkPair("abbr", "$")
        checkPair("rate", "1.0")
    }
}