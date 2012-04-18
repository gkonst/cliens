package adjutrix.cliens.service

import adjutrix.cliens.conf.PropertiesConfigurable
import adjutrix.cliens.model.{Storage, Category, Model}
import adjutrix.cliens.adapter.{StorageAdapter, CategoryAdapter, Adapter}
import adjutrix.cliens.cli.{CategoryCLI, StorageCLI, CLIOption, CLI}

class Service[M <: Model](val adapter: Adapter[M], val cli: CLI[M]) {
  def view(id: Int, options: CLIOption) {
    cli.optionRow(adapter.findById(id), options)
  }

  def list(options: CLIOption) {
    cli.optionList(adapter.findAll(), options)
  }
}

object Service extends PropertiesConfigurable {
  def apply(name: String): Service[_] = name match {
    case "category" => new Service[Category](new CategoryAdapter(), new CategoryCLI())
    case "storage" => new Service[Storage](new StorageAdapter(), new StorageCLI())
    case _ => throw new UnsupportedOperationException("Unknown service name : " + name)
  }
}
