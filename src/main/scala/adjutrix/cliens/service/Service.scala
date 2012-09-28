package adjutrix.cliens.service

import adjutrix.cliens.conf.PropertiesConfigurable
import adjutrix.cliens.model.{Storage, Category, Model}
import adjutrix.cliens.adapter.cash.{StorageAdapter, CategoryAdapter}
import adjutrix.cliens.adapter.Adapter
import adjutrix.cliens.cli.cash.{CategoryCLI, StorageCLI}
import adjutrix.cliens.cli.{CLIOption, CLI}
import adjutrix.cliens.model.serializer.json.JSONSerializers

class Service[M <: Model](val adapter: Adapter[M], val cli: CLI[M]) {
  def view(id: Int, options: CLIOption) {
    cli.optionRow(adapter.findById(id), options)
  }

  def list(options: CLIOption) {
    cli.optionList(adapter.findAll(), options)
  }
}

trait ServiceFactory {
  def apply(name: String): Service[_]
}

object Service extends ServiceFactory with PropertiesConfigurable with JSONSerializers {
  def apply(name: String): Service[_] = name match {
    case "category" => new Service[Category](new CategoryAdapter(), new CategoryCLI())
    case "storage" => new Service[Storage](new StorageAdapter, new StorageCLI())
    case _ => throw new UnsupportedOperationException("Unknown service name : " + name)
  }
}
