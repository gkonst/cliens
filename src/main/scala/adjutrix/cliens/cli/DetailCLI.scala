package adjutrix.cliens.cli

import adjutrix.cliens.model.Model
import adjutrix.cliens.adapter.ReaderAdapterComponent

trait DetailCLI[M <: Model] extends CLI[M] {
  self: ReaderAdapterComponent[M] =>

  def detail(id: Int)(implicit options: CLIOption) {
    handleError(adapter.findById(id)) {
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