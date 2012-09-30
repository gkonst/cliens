package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.{Adapter, AdapterComponent}
import adjutrix.cliens.model.Category
import adjutrix.cliens.conf.{Configurable, PropertiesConfigurable}
import adjutrix.cliens.adapter.protocol.{JSONProtocol, Protocol}
import adjutrix.cliens.model.serializer.json.CategorySerializer

trait CategoryAdapterComponent extends AdapterComponent[Category] {

  lazy val adapter = new CategoryAdapter with PropertiesConfigurable with JSONProtocol[Category] {
    lazy val serializer = CategorySerializer
  }

  trait CategoryAdapter extends Adapter[Category] {
    self: Configurable with Protocol[Category] =>

    val baseUrl = "category"

    def findExpenseCategories = find(Some(Map("type" -> 0)))
  }

}