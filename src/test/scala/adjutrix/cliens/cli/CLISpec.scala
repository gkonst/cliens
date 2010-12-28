package adjutrix.cliens.cli

import org.specs.Specification
import adjutrix.cliens.conf.TestConfiguration
import adjutrix.cliens.model.Model
import java.lang.String

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

        def expectedRowSummaryValues: Iterable[String]

        def givenModel: T

        "rowSummary" should {
            val result = cli.rowSummary(givenModel)
            expectedRowSummaryValues.foreach(value => {
                "return value includes " + value in {
                    result must include(value)
                }
            })
        }

        def expectedHeaderValues: Iterable[String]

        "header" should {
            val result = cli.header
            expectedHeaderValues.foreach(value => {
                "return value includes " + value in {
                    result must include(value)
                }
            })
        }
    }

}