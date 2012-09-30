package adjutrix.cliens.cli.cash

import adjutrix.cliens.cli.CLI
import adjutrix.cliens.adapter.cash._

object CashCLI {
  def apply(name: String): CLI[_] = name match {
    case "category" => new CategoryCLI with CategoryAdapterComponent
    case "storage" => new StorageCLI with StorageAdapterComponent
    case _ => throw new UnsupportedOperationException("Unknown service name : " + name)
  }
}