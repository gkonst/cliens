package adjutrix.cliens.adapter

import adjutrix.cliens.LoggingUtilities._
import adjutrix.cliens.model._
import protocol.Protocol
import adjutrix.cliens.conf.Configurable

trait ReaderAdapter[M <: Model] extends Adapter[M] {
  this: Configurable with Protocol[M] =>

  def findAll(): Either[Error, Option[Seq[M]]] = find(None)

  def find(data: Option[Map[String, Any]]): Either[Error, Option[Seq[M]]] =
    executeGet(absoluteBaseUrl, data).fold(l => Left(l), r => Right(r map serializer.deserializeAll))

  def findById(id: Int): Either[Error, Option[M]] =
    executeGet(absoluteBaseUrl + id).fold(l => Left(l), r => Right(r map serializer.deserialize))
      .trace("findById")
}