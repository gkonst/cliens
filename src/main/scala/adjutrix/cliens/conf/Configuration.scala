package adjutrix.cliens.conf

/**
 * Configuration model class, used to store and transfer configuration related values.
 *
 * @author konstantin_grigoriev
 */
case class Configuration(url: String,
                         username: String,
                         password: String,
                         var showFull: Boolean = false,
                         var entity: String = null)