package adjutrix.cliens.model.serializer

import adjutrix.cliens.model.Model
import io.Source

trait Serializer[T <: Model] {
  def deserialize(data: Source): T = deserialize(data.mkString)

  def deserializeAll(data: Source): Seq[T] = deserializeAll(data.mkString)

  def deserializeAll(data: String): Seq[T]

  def deserialize(data: String): T

  def serialize(entity: T): String

  def serializePretty(entity: T): String
}
