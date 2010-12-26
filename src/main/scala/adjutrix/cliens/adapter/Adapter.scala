package adjutrix.cliens.adapter

import org.apache.commons.codec.binary.Base64
import java.net.{HttpURLConnection, URL}
import scala.io.Source.fromInputStream
import scala.util.parsing.json.JSON.parseFull
import adjutrix.cliens.util.Logging
import java.io.{OutputStreamWriter, InputStream}
import adjutrix.cliens.conf.Configuration
import adjutrix.cliens.model.Model
import java.lang.reflect.ParameterizedType
import adjutrix.cliens.model.serializer.Serializer

/**
 * Base adapter implementation. Encapsulates core CRUD methods for working with Adjutrix API.
 *
 * @author konstantin_grigoriev
 */
abstract class Adapter[T <: Model](configuration: Configuration) extends Logging {
    val baseUrl: String
    val auth = "Basic " + new String(Base64.encodeBase64((configuration.username + ":" + configuration.password).getBytes))
    val modelClass = this.getClass.getGenericSuperclass.asInstanceOf[ParameterizedType].getActualTypeArguments.head.asInstanceOf[Class[T]]
    val serializer = Serializer(modelClass)

    object Method extends Enumeration {
        type Method = Value
        val GET, POST, DELETE, PUT = Value
    }

    import Method._

    def findAll(): Option[List[T]] = executeGet(absoluteBaseUrl, None)

    def findById(id: Int): T = executeGet(absoluteBaseUrl + String.valueOf(id), None) match {
        case Some(x) => x.size match {
            case 1 => x.head
            case 0 => null.asInstanceOf[T]
            case _ => throw new IllegalArgumentException("Multi result returned")
        }
        case None => null.asInstanceOf[T]
    }

    def delete(id: Int) {
        debug(" > delete..." + id)
        executeDelete(absoluteBaseUrl + String.valueOf(id), None)
        debug(" < delete...Ok")
    }

    def create(entity: T): T = {
        debug(" > create..." + entity)
        val data = executePost(absoluteBaseUrl, serializer.serialize(entity)) match {
            case Some(x) => serializer.deserialize(x.asInstanceOf[Map[String, Any]])
            case None => null.asInstanceOf[T]
        }
        debug(" < create...Ok " + data)
        data
    }

    //    def convertRequestData(entity: T): Option[Map[String, Any]]
    //
    //    def convertResponseData(data: Map[String, Any]): T

    def absoluteBaseUrl = configuration.url + "/" + baseUrl + "/"

    def writeData(connection: HttpURLConnection, data: Map[String, Any]) {
        connection.setDoOutput(true)
        val out = new OutputStreamWriter(connection.getOutputStream())
        val dataEncoded = createData(data)
        debug("request data : " + dataEncoded)
        out.write(dataEncoded)
        out.close
    }

    def createData(data: Map[String, Any]) = data.map((item) => item._1 + "=" + item._2).reduceLeft(_ + "&" + _)

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

    def executeGet(url: String, data: Option[Map[String, Any]]): Option[List[T]] = {
        val connection = getConnection(GET, url + createQueryParameters(data))
        connection.getResponseCode match {
            case HttpURLConnection.HTTP_OK => baseConvertResponse(processJSONStream(connection.getInputStream))
            case code => {
                throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
            }
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

    def executePost(url: String, data: Map[String, Any]) = {
        val connection = getConnection(POST, url)
        writeData(connection, data)
        connection.getResponseCode match {
            case HttpURLConnection.HTTP_OK => processJSONStream(connection.getInputStream)
            case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONStream(connection.getErrorStream))
            case code => {
                throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
            }
        }
    }

    def executePut(url: String, data: Map[String, Any]) = {
        val connection = getConnection(PUT, url)
        writeData(connection, data)
        connection.getResponseCode match {
            case HttpURLConnection.HTTP_OK => processJSONStream(connection.getInputStream)
            case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONStream(connection.getErrorStream))
            case code => {
                throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
            }
        }
    }

    def processJSONStream(stream: InputStream) = processJSON(fromInputStream(stream).getLines.mkString)

    def processJSON(input: String) = parseFull(input).asInstanceOf[Option[List[Map[String, Any]]]]

}

/**
 * This exception is used when validation error occurs during create or update operation.
 * Contains detailed errors information.
 *
 * @author konstantin_grigoriev
 */
class ValidationException(errors: Option[Any]) extends Exception {
    override def getMessage = "ValidationException : " + errors
}
