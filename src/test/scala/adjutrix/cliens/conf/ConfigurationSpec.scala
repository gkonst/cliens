package adjutrix.cliens.conf

import org.specs.Specification

/**
 * Specification for {@link Configuration}.
 *
 * @author Konstantin_Grigoriev
 */
object ConfigurationSpec extends Specification {
    Configuration.baseDir = "target"

    def reset {
        Configuration.file.delete
    }

    "Configuration.load" should {
        reset.before
        val configuration = Configuration.load
        "return not Null" in {
            configuration must notBeNull
        }
        "return configuration with" in {
            "username not empty" in {
                configuration.username must (notBeNull and be_!=(""))
            }
            "password not empty" in {
                configuration.password must (notBeNull and be_!=(""))
            }
            "url not empty" in {
                configuration.url must (notBeNull and be_!=(""))
            }
        }
    }
}