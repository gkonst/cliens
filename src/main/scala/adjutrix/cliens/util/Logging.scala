package adjutrix.cliens.util

/**
 * Trait for adding logging functionality to classes. May delegate logging to any logging system.
 * At now simply prints to console.
 *
 * @author konstantin_grigoriev
 */
trait Logging {
    def debug(msg: => String) = Console.println(msg)
}