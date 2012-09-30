package adjutrix.cliens.adapter

import protocol.Protocol
import adjutrix.cliens.conf.Configurable
import adjutrix.cliens.LoggingUtilities._
import adjutrix.cliens.model._

trait WriterAdapter[M <: Model] extends Adapter[M] {
  self: Configurable with Protocol[M] =>

  def delete(id: Int): Either[Error, Unit] =
    executeDelete(absoluteBaseUrl + id)
      .trace("delete")

  def create(entity: M): Either[Any, String] =
    executePost(absoluteBaseUrl, serializer.serialize(entity), serializer.contentType)
      .trace("create")
}
