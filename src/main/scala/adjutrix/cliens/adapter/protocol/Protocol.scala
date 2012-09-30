package adjutrix.cliens.adapter.protocol

import adjutrix.cliens.model.serializer.Serializer
import adjutrix.cliens.model._

trait Protocol[M <: Model] {
  def serializer: Serializer[M]

  def contentType: String

  def errorSerializer: Serializer[_]
}