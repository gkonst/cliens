package adjutrix.cliens.util

import org.specs.Specification
import java.io.{FileOutputStream, File}
import org.specs.mock.Mockito

/**
 * Specification for {@link StreamUtil} object.
 *
 * @author Konstantin_Grigoriev
 */
object StreamUtilSpecification extends Specification with Mockito {
    "withCloseable" should {
        object Stub {
            var stream: FileOutputStream = null
            val file: File = File.createTempFile("stream-test", null)
            file.deleteOnExit

            def createStream() = {
                stream = spy(new FileOutputStream(file))
                stream
            }

            def operate(stream: FileOutputStream) {
                stream.write("Test".getBytes)
            }
        }
        StreamUtil.withCloseable[FileOutputStream](Stub.createStream _, Stub.operate _)
        "create stream" in {
            Stub.stream must notBeNull
        }
        "operate was called" in {
            there was one(Stub.stream).write(any[Array[Byte]])
        }
        "close created stream" in {
            there was one(Stub.stream).close
        }
    }
}