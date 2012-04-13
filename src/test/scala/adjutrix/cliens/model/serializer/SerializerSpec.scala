package adjutrix.cliens.model.serializer

import org.specs2.mutable.Specification
import io.Source
import adjutrix.cliens.model.Model

abstract class SerializerSpec extends Specification {

  def loadFileFromClasspathToString(filename: String) = Source.fromURL(getClass.getResource(filename)).mkString

  def modelShouldHaveId(result: Model, id: Int) = {
    "result must have id and equal " + id in {
      result.id must (beSome[Int] and equalTo(Some(id)))
    }
  }
}