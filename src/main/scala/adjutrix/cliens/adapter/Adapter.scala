package adjutrix.cliens.adapter

import java.net.{HttpURLConnection, URL}
import scala.io.Source.fromInputStream
import adjutrix.cliens.util.Logging
import java.io.OutputStreamWriter
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model
import java.lang.reflect.ParameterizedType
import adjutrix.cliens.model.serializer.JSONSerializer
import io.Source
import net.iharder.Base64

/**
 * Base adapter implementation. Encapsulates core CRUD methods for working with Adjutrix API.
 *
 * @author konstantin_grigoriev
 */
abstract class Adapter[T <: Model](configuration: Configuration) extends Logging {
  val baseUrl: String
  val auth = "Basic " + Base64.encodeBytes((configuration.username + ":" + configuration.password).getBytes)
  val modelClass = this.getClass.getGenericSuperclass.asInstanceOf[ParameterizedType].getActualTypeArguments.head.asInstanceOf[Class[T]]
  val serializer: JSONSerializer[T] = JSONSerializer(modelClass)

  object Method extends Enumeration {
    type Method = Value
    val GET, POST, DELETE, PUT = Value
  }

  import Method._

  def findAll(): Option[List[T]] = baseConvertResponse(executeGetMultiResult(absoluteBaseUrl, None))

  def find(data: Option[Map[String, Any]]): Option[List[T]] = baseConvertResponse(executeGetMultiResult(absoluteBaseUrl, data))

  def findById(id: Int): Option[T] = executeGetSingleResult(absoluteBaseUrl + String.valueOf(id), None).map(serializer.deserialize)

  def delete(id: Int) {
    debug(" > delete..." + id)
    executeDelete(absoluteBaseUrl + String.valueOf(id), None)
    debug(" < delete...Ok")
  }

  def create(entity: T): Option[T] = {
    debug(" > create..." + entity)
    val data = executePost(absoluteBaseUrl, serializer.serialize(entity)) match {
      case Some(x) => Some(serializer.deserialize(x.asInstanceOf[Map[String, Any]]))
      case None => None
    }
    debug(" < create...Ok " + data)
    data
  }

  def absoluteBaseUrl = configuration.url + "/" + baseUrl + "/"

  def writeData(connection: HttpURLConnection, data: Map[String, Any]) {
    connection.setDoOutput(true)
    val out = new OutputStreamWriter(connection.getOutputStream())
    val dataEncoded = createData(data)
    debug("request data : " + dataEncoded)
    out.write(dataEncoded)
    out.close
  }

  def createData(data: Map[String, Any]) = data.map(it => it._1 + "=" + it._2).reduceLeft(_ + "&" + _)

  def createQueryParameters(data: Option[Map[String, Any]]) = data match {
    case Some(data) => "?" + createData(data)
    case None => ""
  }

  def getConnection(method: Method, requestUrl: String) = {
    debug("request url : " + requestUrl)
    val connection = new URL(requestUrl).openConnection.asInstanceOf[HttpURLConnection]
    debug(" auth : " + auth)
    connection.setRequestProperty("Authorization", auth);
    connection.setRequestMethod(method.toString)
    connection
  }

  def executeGetSingleResult(url: String, data: Option[Map[String, Any]]): Either[String, Option[Source]] = {
    val connection = getConnection(GET, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => Right(Some(Source.fromInputStream(connection.getInputStream)))
      case HttpURLConnection.HTTP_NOT_FOUND => Right(None)
      case code => Left(code + " " + fromInputStream(connection.getErrorStream).mkString)
    }
  }

  def executeGetMultiResult(url: String, data: Option[Map[String, Any]]): Either[String, Option[Source]] = {
    val connection = getConnection(GET, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => Right(Some(Source.fromInputStream(connection.getInputStream)))
      case HttpURLConnection.HTTP_NOT_FOUND => Right(None)
      case code => Left(code + " " + fromInputStream(connection.getErrorStream).mkString)
    }
  }

  def baseConvertResponse(data: Option[List[Map[String, Any]]]): Option[List[T]] = data match {
    case Some(data) => Some(data.map(serializer.deserialize))
    case None => None
  }

  def executeDelete(url: String, data: Option[Map[String, Any]]) {
    val connection = getConnection(DELETE, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_NO_CONTENT => Unit
      case code => {
        throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
      }
    }
  }

  def executePost(url: String, data: Map[String, Any]): Option[Map[String, Any]] = {
    val connection = getConnection(POST, url)
    writeData(connection, data)
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => processJSONObject(connection.getInputStream)
      case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONList(connection.getErrorStream))
      case code => {
        throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
      }
    }
  }

  def executePut(url: String, data: Map[String, Any]) = {
    val connection = getConnection(PUT, url)
    writeData(connection, data)
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => Source.from(connection.getInputStream)
      case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONList(connection.getErrorStream))
      case code => {
        throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
      }
    }
  }

  //    def parseJSON(stream: InputStream): Option[Any] = {
  //        parseFull(fromInputStream(stream).getLines.mkString)
  //    }
  //
  //    def processJSONList(stream: InputStream): Option[List[Map[String, Any]]] = {
  //        parseJSON(stream).asInstanceOf[Option[List[Map[String, Any]]]]
  //    }
  //
  //    def processJSONObject(stream: InputStream): Option[Map[String, Any]] = {
  //        parseJSON(stream).asInstanceOf[Option[Map[String, Any]]]
  //    }
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
