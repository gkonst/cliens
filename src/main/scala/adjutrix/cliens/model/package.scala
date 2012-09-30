package adjutrix.cliens

package object model {
  implicit def modelToRelated[T <: Model](value: T) = Related(value.resourceURI.get, Some(value))

  implicit def modelOptToOptRelated[T <: Model](value: Option[T]) = value.map {
    v => modelToRelated(v)
  }

  implicit def stringToRelated(resourceURI: String) = Related(resourceURI, None)

  implicit def optStringToRelated[T <: Model](resourceURI: Option[String]) = Related[T](resourceURI.get, None)

  implicit def relatedToModel[T <: Model](related: Related[T]) = related.get

  def uriToId(resourceURI: String) = resourceURI.split('/').last.toInt
}