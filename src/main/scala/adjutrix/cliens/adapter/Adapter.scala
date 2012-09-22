package adjutrix.cliens.adapter

import java.net.{HttpURLConnection, URL}
import scala.io.Source.fromInputStream
import java.io.OutputStreamWriter
import adjutrix.cliens.model.Model
import io.Source
import net.iharder.Base64
import adjutrix.cliens.model.serializer.Serializer
import grizzled.slf4j.Logging
import adjutrix.cliens.conf.Configuration

/**
 * Base adapter implementation. Encapsulates core CRUD methods for working with Adjutrix API.
 *
 * @author konstantin_grigoriev
 */
abstract class Adapter[T <: Model](implicit mf: Manifest[T],
                                   configuration: Configuration,
                                   serializer: Serializer[T]) extends Logging {
  protected val baseUrl: String
  protected val auth = "Basic " + Base64.encodeBytes((configuration.username + ":" + configuration.password).getBytes)

  protected object Method extends Enumeration {
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

  def create(entity: T): Either[Any, String] = {
    debug(" > create..." + entity)
    val data = executePost(absoluteBaseUrl, serializer.serialize(entity), serializer.contentType)
    debug(" < create...Ok " + data)
    data
  }

  protected def absoluteBaseUrl = configuration.url + "/" + baseUrl + "/"

  protected def writeData(connection: HttpURLConnection, data: String, contentType: String) {
    connection.setRequestProperty("Content-Type", contentType)
    connection.setDoOutput(true)
    val out = new OutputStreamWriter(connection.getOutputStream)
    debug("request data : " + data)
    try {
      out.write(data)
    } finally {
      out.close()
    }
  }

  protected def createData(data: Map[String, Any]) = data.map(it => it._1 + "=" + it._2).reduceLeft(_ + "&" + _)

  protected def createQueryParameters(data: Option[Map[String, Any]]) = data match {
    case Some(parameters) => "?" + createData(parameters)
    case None => ""
  }

  protected def getConnection(method: Method, requestUrl: String) = {
    debug("request url : " + requestUrl)
    val connection = new URL(requestUrl).openConnection.asInstanceOf[HttpURLConnection]
    debug(" auth : " + auth)
    connection.setRequestProperty("Authorization", auth)
    connection.setRequestProperty("Accept", serializer.contentType)
    connection.setRequestMethod(method.toString)
    connection
  }

  protected def processHttpOk(connection: HttpURLConnection): Some[Source] = {
    require(connection.getContentType == serializer.contentType,
      "required content type is %s, but actual is %s".format(serializer.contentType, connection.getContentType))
    Some(fromInputStream(connection.getInputStream))
  }

  protected def executeGet(url: String, data: Option[Map[String, Any]] = None): Option[Source] = {
    val connection = getConnection(GET, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => processHttpOk(connection)
      case HttpURLConnection.HTTP_NOT_FOUND => None
      case code => throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
    }
  }

  protected def executeDelete(url: String, data: Option[Map[String, Any]] = None) {
    val connection = getConnection(DELETE, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_NO_CONTENT => Unit
      case code => throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
    }
  }

  protected def executePost(url: String, data: String, contentType: String): Either[Any, String] = {
    val connection = getConnection(POST, url)
    writeData(connection, data, contentType)
    debug("response code : " + connection.getResponseCode)
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_CREATED => Right(connection.getHeaderField("Location"))
      // TODO handle badrequest, notfound, and so on???
      //      case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONList(connection.getErrorStream))
      case code => Left(new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString))
    }
  }

  protected def executePut(url: String, data: String, contentType: String): Option[Source] = {
    // TODO implement
    val connection = getConnection(PUT, url)
    writeData(connection, data, contentType)
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => processHttpOk(connection)
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
