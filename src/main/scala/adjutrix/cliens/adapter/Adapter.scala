package adjutrix.cliens.adapter

import org.apache.commons.codec.binary.Base64
import java.net.{HttpURLConnection, URL}
import scala.io.Source.fromInputStream
import scala.util.parsing.json.JSON.parseFull
import adjutrix.cliens.util.Logging
import java.io.{OutputStreamWriter, InputStream}
import adjutrix.cliens.conf.Configuration

/**
 * Base adapter implementation. Encapsulates core CRUD methods for working with Adjutrix API.
 *
 * @author konstantin_grigoriev
 */
abstract class Adapter(configuration: Configuration) extends Logging {
    val baseUrl: String
    val auth = "Basic " + new String(Base64.encodeBase64((configuration.username + ":" + configuration.password).getBytes))

    object Method extends Enumeration {
        type Method = Value
        val GET, POST, DELETE, PUT = Value
    }

    import Method._

    def findAll() = executeGet(absoluteBaseUrl, None)

    def findById(id: Int) = executeGet(absoluteBaseUrl + String.valueOf(id), None) match {
        case Some(x) => x.asInstanceOf[List[Map[Any, Any]]].size match {
            case 1 => x.asInstanceOf[List[Map[Any, Any]]].head
            case 0 => null
            case _ => throw new IllegalArgumentException("Multi result returned")
        }
        case None => null
    }

    def delete(id: Int) {
        debug(" > delete..." + id)
        executeDelete(absoluteBaseUrl + String.valueOf(id), None)
        debug(" < delete...Ok")
    }

    def create(entity: Map[Any, Any]) = {
        debug(" > create..." + entity)
        val data = executePost(absoluteBaseUrl, Some(entity)) match {
            case Some(x) => x.asInstanceOf[Map[Any, Any]]
            case None => null
        }
        debug(" < create...Ok " + data)
        data
    }

    def absoluteBaseUrl = configuration.url + "/" + baseUrl + "/"

    def writeData(connection: HttpURLConnection, data: Option[Map[Any, Any]]) {
        data match {
            case Some(data) => {
                connection.setDoOutput(true)
                val out = new OutputStreamWriter(connection.getOutputStream())
                val dataEncoded = createData(data)
                debug("request data : " + dataEncoded)
                out.write(dataEncoded)
                out.close
            }
            case None => Unit
        }
    }

    def createData(data: Map[Any, Any]) = data.map((item) => item._1 + "=" + item._2).reduceLeft(_ + "&" + _)

    def createQueryParameters(data: Option[Map[Any, Any]]) = data match {
        case Some(data) => "?" + createData(data)
        case None => ""
    }

    def getConnection(method: Method, requestUrl: String) = {
        debug("request url : " + requestUrl)
        val connection = new URL(requestUrl).openConnection.asInstanceOf[HttpURLConnection]
        connection.setRequestProperty("Authorization", auth);
        connection.setRequestMethod(method.toString)
        connection
    }

    def executeGet(url: String, data: Option[Map[Any, Any]]) = {
        val connection = getConnection(GET, url + createQueryParameters(data))
        connection.getResponseCode match {
            case HttpURLConnection.HTTP_OK => processJSONStream(connection.getInputStream)
            case code => {
                throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
            }
        }
    }

    def executeDelete(url: String, data: Option[Map[Any, Any]]) {
        val connection = getConnection(DELETE, url + createQueryParameters(data))
        connection.getResponseCode match {
            case HttpURLConnection.HTTP_NO_CONTENT => Unit
            case code => {
                throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
            }
        }
    }

    def executePost(url: String, data: Option[Map[Any, Any]]) = {
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

    def executePut(url: String, data: Option[Map[Any, Any]]) = {
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

    def processJSON(input: String) = parseFull(input)

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
