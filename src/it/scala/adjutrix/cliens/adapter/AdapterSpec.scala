package adjutrix.cliens.adapter

import adjutrix.cliens.model._
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

trait AdapterSpec[M <: Model, C <: AdapterComponent[M]] extends FlatSpec with MustMatchers {
  self: C =>
}
