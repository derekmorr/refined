package eu.timepit.refined
package scalacheck

import eu.timepit.refined.api.RefType
import eu.timepit.refined.generic.Equal
import org.scalacheck.{Arbitrary, Gen}
import shapeless.Witness

/** Module that provides `Arbitrary` instances for generic predicates. */
object generic {

  implicit def equalArbitrary[F[_, _]: RefType, T, U <: T](
      implicit wu: Witness.Aux[U]
  ): Arbitrary[F[T, Equal[U]]] =
    arbitraryRefType(Gen.const(wu.value))
}
