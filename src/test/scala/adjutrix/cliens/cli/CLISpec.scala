package adjutrix.cliens.cli

import org.specs.Specification
import adjutrix.cliens.model.Model
import java.lang.String
import adjutrix.cliens.conf.PropertiesConfiguration

abstract class CLISpec[T <: Model] extends Specification {
  implicit val configuration = PropertiesConfiguration.loadFromDefault()

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
