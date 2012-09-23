package adjutrix.cliens

import java.io.InputStream
import io.Source

package object adapter {
  implicit def inputStreamToString(stream: InputStream) = Source.fromInputStream(stream).mkString
}