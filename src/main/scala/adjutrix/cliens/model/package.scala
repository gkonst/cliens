package adjutrix.cliens

package object model {
  implicit def modelToRelated[T <: Model](value: T) = Related(value.resourceURI.get, Some(value))

  implicit def stringToRelated(resourceURI: String) = Related(resourceURI, None)

  implicit def optStringToRelated[T <: Model](resourceURI: Option[String]) = Related[T](resourceURI.get, None)

  implicit def relatedToModel[T <: Model](related: Related[T]) = related.value.get
}