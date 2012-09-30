package adjutrix.cliens.adapter

import java.net.{HttpURLConnection, URL}
import protocol.Protocol
import scala.io.Source.fromInputStream
import java.io.OutputStreamWriter
import io.Source
import net.iharder.Base64
import grizzled.slf4j.Logger
import adjutrix.cliens.conf.Configurable
import adjutrix.cliens.LoggingUtilities._
import adjutrix.cliens.model._

/**
 * Base adapter implementation. Encapsulates core CRUD methods for working with Adjutrix API.
 *
 * @author konstantin_grigoriev
 */
trait Adapter[T <: Model] {
  self: Configurable with Protocol[T] =>

  protected implicit lazy val logger = Logger(getClass)

  protected def baseUrl: String

  protected val auth = "Basic " + Base64.encodeBytes((configuration.username + ":" + configuration.password).getBytes)

  protected object Method extends Enumeration {
    type Method = Value
    val GET, POST, DELETE, PUT = Value
  }

  import Method._

  def findAll(): Either[Error, Option[Seq[T]]] = find(None)

  def find(data: Option[Map[String, Any]]): Either[Error, Option[Seq[T]]] =
    executeGet(absoluteBaseUrl, data).fold(l => Left(l), r => Right(r map serializer.deserializeAll))

  def findById(id: Int): Either[Error, Option[T]] =
    executeGet(absoluteBaseUrl + id).fold(l => Left(l), r => Right(r map serializer.deserialize))
      .trace("findById")

  def delete(id: Int): Either[Error, Unit] =
    executeDelete(absoluteBaseUrl + id)
      .trace("delete")

  def create(entity: T): Either[Any, String] =
    executePost(absoluteBaseUrl, serializer.serialize(entity), serializer.contentType)
      .trace("create")

  protected def absoluteBaseUrl = configuration.url + "/" + baseUrl + "/"

  protected def writeData(connection: HttpURLConnection, data: String, contentType: String) {
    connection.setRequestProperty("Content-Type", contentType)
    connection.setDoOutput(true)
    val out = new OutputStreamWriter(connection.getOutputStream)
    data.trace(" data")
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
    requestUrl.trace(" requestUrl")
    val connection = new URL(requestUrl).openConnection.asInstanceOf[HttpURLConnection]
    connection.setRequestProperty("Authorization", auth)
    connection.setRequestProperty("Accept", serializer.contentType)
    connection.setRequestMethod(method.toString)
    connection
  }

  protected def processResponseWithResult(connection: HttpURLConnection) = {
    require(connection.getContentType == serializer.contentType,
      "required content type is %s, but actual is %s".format(serializer.contentType, connection.getContentType))
    Right(Some(fromInputStream(connection.getInputStream)))
  }

  protected def executeGet(url: String, data: Option[Map[String, Any]] = None): Either[Error, Option[Source]] = {
    val connection = getConnection(GET, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_OK => processResponseWithResult(connection)
      case HttpURLConnection.HTTP_NOT_FOUND => Right(None)
      case code => Left(ServerError(code, connection.getErrorStream()))
    }
  }

  protected def executeDelete(url: String, data: Option[Map[String, Any]] = None): Either[Error, Unit] = {
    val connection = getConnection(DELETE, url + createQueryParameters(data))
    connection.getResponseCode match {
      case HttpURLConnection.HTTP_NO_CONTENT => Right()
      case code => Left(ServerError(code, connection.getErrorStream))
    }
  }

  protected def executePost(url: String, data: String, contentType: String): Either[Error, String] = {
    val connection = getConnection(POST, url)
    writeData(connection, data, contentType)
    connection.getResponseCode.trace(" responseCode") match {
      case HttpURLConnection.HTTP_CREATED => Right(connection.getHeaderField("Location"))
      // TODO parse error stream
      case HttpURLConnection.HTTP_BAD_REQUEST => Left(ValidationError(connection.getErrorStream))
      case code => Left(ServerError(code, connection.getErrorStream))
    }
  }

  protected def executePut(url: String, data: String, contentType: String): Option[Source] = {
    // TODO implement
    // val connection = getConnection(PUT, url)
    // writeData(connection, data, contentType)
    // connection.getResponseCode match {
    //   case HttpURLConnection.HTTP_OK => processResponseWithResult(connection)
    //   //      case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONList(connection.getErrorStream))
    //   case code => throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
    // }
    None
  }
}
