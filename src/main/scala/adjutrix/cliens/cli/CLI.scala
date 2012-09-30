package adjutrix.cliens.cli

import adjutrix.cliens.model._
import scala.Predef._
import adjutrix.cliens.adapter.AdapterComponent

/**
 * Base CLI implementation. Used to communicate with user using console. 
 *
 * @author konstantin_grigoriev
 */
trait CLI[T <: Model] extends SystemIO {
  self: AdapterComponent[T] =>

  private val headerLine = "-" * header.size

  protected[cli] def header: String = String.format("%-5s", "Id")

  protected def handleError[A](data: Either[Error, Option[A]])(f: Option[A] => Unit) {
    data match {
      case Left(error) => printError(error)
      case Right(x) => f(x)
    }
  }

  def readLine(prompt: String, args: Any*) = {
    out.printf(prompt, args)
    in.readLine()
  }

  protected[cli] def printHeaderLine() {
    out.println(headerLine)
  }

  protected[cli] def printHeader() {
    printHeaderLine()
    out.println(header)
    printHeaderLine()
  }

  protected[cli] def printError(error: Error) {
    out.println(error)
  }
}
