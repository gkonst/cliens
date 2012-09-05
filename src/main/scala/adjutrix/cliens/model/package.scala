package adjutrix.cliens

package object model {
  implicit def modelToRelated[T <: Model](value: T) = Related(value.resourceURI.get, Some(value))

  implicit def modelOptToOptRelated[T <: Model](value: Option[T]) = value.map {
    v => modelToRelated(v)
  }

  implicit def stringToRelated(resourceURI: String) = Related(resourceURI, None)

  implicit def optStringToRelated[T <: Model](resourceURI: Option[String]) = Related[T](resourceURI.get, None)

  implicit def relatedToModel[T <: Model](related: Related[T]) = related.value.get

  def snakify(word: String) = word.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").toLowerCase
}