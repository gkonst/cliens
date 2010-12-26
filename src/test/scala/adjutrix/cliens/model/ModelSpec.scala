package adjutrix.cliens.model

import org.specs.Specification

/**
 * Base class for specifying models.
 *
 * @author Konstantin_Grigoriev
 */

class ModelSpec extends Specification {

    def checkPair(result: Map[String, Any], key: String, value: Any) {
        "result must have " + key + " and equal " + value in {
            result must havePair((key, value))
        }
    }

    def checkId(result: Model, id: Int) {
        "result must have id and equal " + id in {
            result.id must (beSome[Int] and equalTo(Some(id)))
        }
    }
}