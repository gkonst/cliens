package adjutrix.cliens.cli.cash

import adjutrix.cliens.cli.{CLIFactory, CLI}
import adjutrix.cliens.adapter.cash._

object CashCLI extends CLIFactory {
  def apply(name: String): CLI[_] = name match {
    case "category" => new CategoryCLI with CategoryAdapterComponent
    case "storage" => new StorageCLI with StorageAdapterComponent
    case _ => throw new UnsupportedOperationException("Unknown service name : " + name)
  }
}