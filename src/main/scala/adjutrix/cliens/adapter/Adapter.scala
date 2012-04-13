package adjutrix.cliens.adapter

import java.net.{HttpURLConnection, URL}
import scala.io.Source.fromInputStream
import adjutrix.cliens.util.Logging
import java.io.OutputStreamWriter
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model
import java.lang.reflect.ParameterizedType
import io.Source
import net.iharder.Base64
import adjutrix.cliens.model.serializer.Serializer
import adjutrix.cliens.model.serializer.json.JSONSerializer

/**
 * Base adapter implementation. Encapsulates core CRUD methods for working with Adjutrix API.
 *
 * @author konstantin_grigoriev
 */
abstract class Adapter[T <: Model](configuration: Configuration) extends Logging {
  val baseUrl: String
  val auth = "Basic " + Base64.encodeBytes((configuration.username + ":" + configuration.password).getBytes)
  val modelClass = this.getClass.getGenericSuperclass.asInstanceOf[ParameterizedType].getActualTypeArguments.head.asInstanceOf[Class[T]]
  val serializer: Serializer[T] = JSONSerializer(modelClass)

  object Method extends Enumeration {
    type Method = Value
    val GET, POST, DELETE, PUT = Value
  }

  import Method._

  def findAll(): Option[Seq[T]] = executeGet(absoluteBaseUrl) map serializer.deserializeAll

  def find(data: Option[Map[String, Any]]): Option[Seq[T]] = executeGet(absoluteBaseUrl, data) map serializer.deserializeAll

  def findById(id: Int): Option[T] = executeGet(absoluteBaseUrl + String.valueOf(id)) map serializer.deserialize

  def delete(id: Int) {
    debug(" > delete..." + id)
    executeDelete(absoluteBaseUrl + String.valueOf(id))
    debug(" < delete...Ok")
  }

  def create(entity: T): Option[T] = {
    debug(" > create..." + entity)
    val data = executePost(absoluteBaseUrl, serializer.serialize(entity)) map serializer.deserialize
    debug(" < create...Ok " + data)
    data
  }

  def absoluteBaseUrl = configuration.url + "/" + baseUrl + "/"

  def writeData(connection: HttpURLConnection, data: String) {
    connection.setDoOutput(true)
    val out = new OutputStreamWriter(connection.getOutputStream)
    debug("request data : " + data)
    out.write(data)
    out.close()
  }

  def createData(data: Map[String, Any]) = data.map(it => it._1 + "=" + it._2).reduceLeft(_ + "&" + _)

  def createQueryParameters(data: Option[Map[String, Any]]) = data match {
    case Some(parameters) => "?" + createData(parameters)
    case None => ""
  }

  def getConnection(method: Method, requestUrl: String) = {
    debug("request url : " + requestUrl)
    val connection = new URL(requestUrl).openConnection.asInstanceOf[HttpURLConnection]
    debug(" auth : " + auth)
    connection.setRequestProperty("Authorization", auth)
    connection.setRequestMethod(method.toString)
    connection
  }

  def executeGet(url: String, data: Option[Map[String, Any]] = None): Option[Source] = {
    val connection = getConnection(GET, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => Some(Source.fromInputStream(connection.getInputStream))
      case HttpURLConnection.HTTP_NOT_FOUND => None
      case code => throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
    }
  }

  def executeDelete(url: String, data: Option[Map[String, Any]] = None) {
    val connection = getConnection(DELETE, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_NO_CONTENT => Unit
      case code => throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
    }
  }

  def executePost(url: String, data: String): Option[Source] = {
    val connection = getConnection(POST, url)
    writeData(connection, data)
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => Some(Source.fromInputStream(connection.getInputStream))
      //      case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONList(connection.getErrorStream))
      case code => throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
    }
  }

  def executePut(url: String, data: String): Option[Source] = {
    val connection = getConnection(PUT, url)
    writeData(connection, data)
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => Some(Source.fromInputStream(connection.getInputStream))
      //      case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONList(connection.getErrorStream))
      case code => throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
    }
  }
}

/**
 * This exception is used when validation error occurs during create or update operation.
 * Contains detailed errors information.
 *
 * @author konstantin_grigoriev
 */
class ValidationException(errors: Option[List[Map[String, Any]]]) extends Exception {
  override def getMessage = "ValidationException : " + errors
}
