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
    val url: String
    val auth = "Basic " + new String(Base64.encodeBase64((configuration.username + ":" + configuration.password).getBytes))

    def findAll() =
        processGet()

    def findById(id: Int) = processGet(Array(String.valueOf(id))) match {
        case Some(x) => x.asInstanceOf[List[Map[Any, Any]]].size match {
            case 1 => x.asInstanceOf[List[Map[Any, Any]]].head
            case 0 => null
            case _ => throw new IllegalArgumentException("Multi result returned")
        }
        case None => null
    }

    def create(entity: Map[Any, Any]) = {
        debug(" > create..." + entity)
        val data = processPost(entity)
        debug(" < create. " + data)
        data
    }

    def writeData(connection: HttpURLConnection, data: Map[Any, Any]) = {
        connection.setDoOutput(true)
        val out = new OutputStreamWriter(connection.getOutputStream());
        val a = createData(data)
        debug("request data : " + a)
        out.write(a);
        out.close();
        connection
    }

    def createData(data: Map[Any, Any]) = data.map((item) => item._1 + "=" + item._2).reduceLeft(_ + "&" + _)

    def getConnection(method: String, parameters: Array[String] = Array[String](), queryString: String = "") = {
        val fullUrl = (Array(configuration.url, url) ++ parameters).reduceLeft(_ + "/" + _) + "/" + queryString
        debug("request url : " + fullUrl)
        val connection = new URL(fullUrl).openConnection.asInstanceOf[HttpURLConnection]
        connection.setRequestProperty("Authorization", auth);
        connection.setRequestMethod(method)
        connection
    }

    def processGet(parameters: Array[String] = Array[String](), queryString: String = "") =
        processResponse(getConnection("GET", parameters, queryString))

    def processPost(data: Map[Any, Any]) = processResponse(writeData(getConnection("POST"), data))

    def processResponse(connection: HttpURLConnection) = connection.getResponseCode match {
        case HttpURLConnection.HTTP_OK => processJSONStream(connection.getInputStream)
        case HttpURLConnection.HTTP_BAD_REQUEST => throw new ValidationException(processJSONStream(connection.getErrorStream))
        case code => {
            throw new IllegalArgumentException(code + " " + fromInputStream(connection.getErrorStream).mkString)
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
