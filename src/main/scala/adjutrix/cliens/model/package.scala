package adjutrix.cliens

package object model {
  implicit def modelToRelated[T <: Model](value: T) = Related(value.resourceURI.get, Some(value))

  implicit def modelOptToOptRelated[T <: Model](value: Option[T]) = value.map {
    v => modelToRelated(v)
  }

  implicit def stringToRelated(resourceURI: String) = Related(resourceURI, None)

  implicit def optStringToRelated[T <: Model](resourceURI: Option[String]) = Related[T](resourceURI.get, None)

  implicit def relatedToModel[T <: Model](related: Related[T]) = related.value.get

  /**
   * Convert a string from camelCase to snake_case.
   */
  def snakeCase(word: String) = {
    val len = word.length()
    val builder = new StringBuilder(len)
    if (len > 0) {
      var idx = if (word(0) == '_') {
        // preserve the first underscore
        builder += '_'
        1
      } else 0
      while (idx < len) {
        val char = word(idx)
        if (Character.isUpperCase(char)) {
          builder += '_'
          builder += Character.toLowerCase(char)
        } else {
          builder += char
        }
        idx += 1
      }
    }
    builder.toString()
  }
}