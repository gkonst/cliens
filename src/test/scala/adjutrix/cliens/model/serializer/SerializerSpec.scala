package adjutrix.cliens.model.serializer

import org.specs2.mutable.Specification
import io.Source

abstract class SerializerSpec extends Specification {
  def loadFileFromClasspathToString(filename: String) = Source.fromURL(getClass.getResource(filename)).mkString
}