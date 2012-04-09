package adjutrix.cliens.model

import org.specs2.mutable.Specification

abstract class ModelSpec extends Specification {

  def modelShouldHaveId(result: Model, id: Int) = {
    "result must have id and equal " + id in {
      result.id must (beSome[Int] and equalTo(Some(id)))
    }
  }
}