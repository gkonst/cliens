package adjutrix.cliens.conf

/**
 * Provides {@link #conf} variable with test configuration.
 * Configuration is stored it 'target' directory.
 *
 * @author Konstantin_Grigoriev
 */
trait TestConfiguration1 {
    Configuration.baseDir = "target"
    val conf = Configuration.load
}

