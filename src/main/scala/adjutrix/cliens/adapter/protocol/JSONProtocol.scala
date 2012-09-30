package adjutrix.cliens.adapter.protocol

import adjutrix.cliens.model.Model

trait JSONProtocol[M <: Model] extends Protocol[M] {
  val contentType = "application/json; charset=utf-8"

  val errorSerializer = null
}