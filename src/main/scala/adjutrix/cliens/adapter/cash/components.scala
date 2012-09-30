package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.AdapterComponent
import adjutrix.cliens.model.{Storage, Category}
import adjutrix.cliens.conf.PropertiesConfigurable

trait CategoryAdapterComponent extends AdapterComponent[Category] {

  def adapter = new CategoryAdapter with PropertiesConfigurable with CategoryProtocol
}

trait StorageAdapterComponent extends AdapterComponent[Storage] {

  def adapter = new StorageAdapter with PropertiesConfigurable with StorageProtocol
}