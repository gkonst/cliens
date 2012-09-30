package adjutrix.cliens.adapter.cash

import adjutrix.cliens.conf.Configurable
import adjutrix.cliens.model.Category
import adjutrix.cliens.adapter.Adapter
import adjutrix.cliens.adapter.protocol.Protocol

trait CategoryAdapter extends Adapter[Category] {
  self: Configurable with Protocol[Category] =>

  val baseUrl = "category"

  def findExpenseCategories = find(Some(Map("type" -> 0)))
}