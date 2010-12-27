package adjutrix.cliens.cli

import org.specs.Specification
import adjutrix.cliens.conf.TestConfiguration
import adjutrix.cliens.model.Model

/**
 * Specification for {@link CLI} class and its subclasses.
 *
 * @author konstantin_grigoriev
 */
object CLISpec {

    /**
     * Specification for {@link CLI} class and its subclasses.
     *
     * @author konstantin_grigoriev
     */
    abstract class CLISpec[T <: Model](cliName: String) extends Specification with TestConfiguration {
        val cli = CLIFactory(conf, cliName)

        def expectedRowSummary: String

        def givenModel: T

        "rowSummary should return " + expectedRowSummary in {
            cli.rowSummary(givenModel) must beEqualTo(expectedRowSummary)
        }
    }

}