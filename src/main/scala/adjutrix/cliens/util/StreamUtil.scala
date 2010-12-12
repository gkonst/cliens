package adjutrix.cliens.util

import java.io.Closeable

/**
 * Various stream utility methods.
 *
 * @author konstantin_grigoriev
 */
object StreamUtil {

    def withCloseable[T <: Closeable](create: () => T, operate: T => Unit) {
        var closeable: T = null.asInstanceOf[T];
        try {
            closeable = create()
            operate(closeable)
        } finally {
            if (closeable != null) {
                closeable.close
            }
        }
    }
}