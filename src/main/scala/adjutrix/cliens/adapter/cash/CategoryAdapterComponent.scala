package adjutrix.cliens.adapter.cash

import adjutrix.cliens.adapter.{ReaderAdapterComponent, ReaderAdapter}
import adjutrix.cliens.model.Category
import adjutrix.cliens.conf.{Configurable, PropertiesConfigurable}
import adjutrix.cliens.adapter.protocol.{JSONProtocol, Protocol}
import adjutrix.cliens.model.serializer.json.cash.CategorySerializer

trait CategoryAdapterComponent extends ReaderAdapterComponent[Category] {

  lazy val adapter: CategoryAdapter =
    new CategoryAdapter with PropertiesConfigurable with JSONProtocol[Category] {
      lazy val serializer = CategorySerializer
    }

  trait CategoryAdapter extends ReaderAdapter[Category] {
    self: Configurable with Protocol[Category] =>

    val baseUrl = "category"

    def findExpenseCategories = find(Some(Map("type" -> 0)))
  }

}