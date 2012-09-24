package adjutrix.cliens.model

sealed trait Error

case class ServerError(code: Int, message: String) extends Error

case class ValidationError(message: String) extends Error