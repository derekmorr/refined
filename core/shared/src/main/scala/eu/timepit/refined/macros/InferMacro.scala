package eu.timepit.refined
package macros

import eu.timepit.refined.api.Inference.==>
import eu.timepit.refined.api.RefType
import eu.timepit.refined.internal.Resources
import macrocompat.bundle
import scala.reflect.macros.blackbox

@bundle
class InferMacro(val c: blackbox.Context) extends MacroUtils {
  import c.universe._

  def impl[F[_, _], T, A: c.WeakTypeTag, B: c.WeakTypeTag](ta: c.Expr[F[T, A]])(
    ir: c.Expr[A ==> B], rt: c.Expr[RefType[F]]
  ): c.Expr[F[T, B]] = {

    val inference = eval(ir)
    if (inference.notValid) {
      abort(Resources.invalidInference(weakTypeOf[A].toString, weakTypeOf[B].toString))
    }

    reify(rt.splice.unsafeRewrap(ta.splice))
  }
}
