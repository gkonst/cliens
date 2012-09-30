package adjutrix.cliens.adapter

import adjutrix.cliens.model._

trait AdapterComponent[M <: Model] {
  def adapter: Adapter[M]
}

trait ReaderAdapterComponent[M <: Model] extends AdapterComponent[M] {
  def adapter: ReaderAdapter[M]
}

trait WriterAdapterComponent[M <: Model] extends ReaderAdapterComponent[M] {
  def adapter: WriterAdapter[M] with ReaderAdapter[M]
}
