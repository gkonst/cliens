package adjutrix.cliens.cli

import adjutrix.cliens.adapter.AdapterComponent
import adjutrix.cliens.model.{Error, Model}

trait ListCLI[M <: Model] extends DetailCLI[M] {
  self: AdapterComponent[M] =>

  def list()(implicit options: CLIOption) {
    // TODO implement list operation in CLI
  }

  def optionList(data: Either[Error, Option[Seq[M]]], options: CLIOption) {
    handleError(data) {
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