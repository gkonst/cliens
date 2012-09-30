package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.AdapterComponent
import adjutrix.cliens.model.{Storage, Category}
import adjutrix.cliens.conf.PropertiesConfigurable
import adjutrix.cliens.model.serializer.json.JSONSerializers

trait CategoryAdapterComponent extends AdapterComponent[Category] with PropertiesConfigurable with JSONSerializers {

  def adapter = new CategoryAdapter
}

trait StorageAdapterComponent extends AdapterComponent[Storage] with PropertiesConfigurable with JSONSerializers {

  def adapter = new StorageAdapter
}