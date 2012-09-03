package adjutrix.cliens.model

import io.Source

package object serializer {
  def loadFileFromClasspathToString(filename: String) = Source.fromURL(getClass.getResource(filename)).mkString
}