package adjutrix.cliens.cli

import org.specs.Specification
import adjutrix.cliens.conf.TestConfiguration
import adjutrix.cliens.model.Model
import java.lang.String

abstract class CLISpec[T <: Model] extends Specification with TestConfiguration {

  val cli: CLI[T]

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
