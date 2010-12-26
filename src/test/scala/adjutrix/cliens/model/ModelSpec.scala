package adjutrix.cliens.model

import org.specs.Specification

/**
 * Base class for specifying models.
 *
 * @author Konstantin_Grigoriev
 */

class ModelSpec extends Specification {

    def checkPair(result: Option[Map[String, Any]], key: String, value: Any) {
        "result must have " + key + " and equal " + value in {
            result.get must havePair((key, value))
        }
    }
}