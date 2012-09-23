package adjutrix.cliens

import grizzled.slf4j.Logger

/*
 * A mixin with logging utilities.
 */
trait LoggingUtilities {
  implicit def wrapAny[A](value: A) = new AnyWrapper(value)

  class AnyWrapper[A](value: A) {

    /**
     * This is a logging version of Kestrel combinator.
     *
     * This method is very similar to Object#tap method in Ruby. It prints the given message along with the object
     * on which the method was invoked, and evaluates to the same object. It is used to tap into method chains, in
     * in order to inspect the values of object at various stages in processing.
     *
     * Example:
     * List.range(1, 10).trace("Original")
     * .map(2 *).trace("After mapping")
     * .filter(4 <).trace("After filtering")
     * .size.trace("Size")
     *
     * The default logging method invoked is Logger#debug. It is easy to invoke other logging methods instead, if
     * so required.
     *
     * Example:
     * List.range(1, 10).trace("Original", _.info)
     *
     * @param message The message to be logged along with the object value.
     * @param logFn The logging method to use. By default, set to Logger#debug.
     * @param logger The logger to use for logging.
     * @return The object on which the method was invoked.
     */
    @inline final def trace(message: => String, logFn: Logger => (=> Any) => Unit = _.debug)(implicit logger: Logger): A = {
      logFn(logger)(message + ": " + value)
      value
    }
  }

}

/*
 * A first class module that provides logging utilities.
 */
object LoggingUtilities extends LoggingUtilities