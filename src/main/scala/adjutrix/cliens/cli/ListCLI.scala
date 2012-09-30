package adjutrix.cliens.cli

import adjutrix.cliens.adapter.ReaderAdapterComponent
import adjutrix.cliens.model.Model

trait ListCLI[M <: Model] extends DetailCLI[M] {
  self: ReaderAdapterComponent[M] =>

  def list(implicit options: CLIOption) {
    handleError(adapter.findAll()) {
      result =>
        printHeader()
        result match {
          case Some(items) => list(items, options)
          case None => out.println("No data found")
        }
    }
  }

  private def list(items: Seq[M], options: CLIOption) {
    items.foreach(item => row(item, options))
  }
}