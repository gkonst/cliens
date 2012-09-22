package adjutrix.cliens.model.serializer

import adjutrix.cliens.model._

trait Serializers {
  def storageSerializer: Serializer[Storage]

  def categorySerializer: Serializer[Category]
}