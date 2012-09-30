package adjutrix.cliens.adapter

import adjutrix.cliens.model._

trait AdapterComponent[M <: Model] {
  def adapter: Adapter[M]
}