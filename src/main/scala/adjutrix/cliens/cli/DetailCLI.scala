package adjutrix.cliens.cli

import adjutrix.cliens.model.{Error, Model}
import adjutrix.cliens.adapter.AdapterComponent

trait DetailCLI[M <: Model] extends CLI[M] {
  self: AdapterComponent[M] =>

  def detail(id: Int)(implicit options: CLIOption) {
    // TODO implement detail operation in CLI
  }

  protected def optionRow(data: Either[Error, Option[M]], options: CLIOption) {
    handleError(data) {
      result =>
        printHeader()
        result match {
          case Some(x) => row(x, options)
          case None => out.println("Not found")
        }
    }
  }

  protected def row(item: M, options: CLIOption) {
    options match {
      case Verbose(other) => out.println(rowVerbose(item))
      case _ => out.println(rowSummary(item))
    }
  }

  protected[cli] def rowSummary(item: M) = item.id match {
    case Some(x) => String.format("%-5s", x.toString)
    case None => ""
  }

  protected[cli] def rowVerbose(item: M) = rowSummary(item)
}