package stuff

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal
import monix.eval.Task
import java.util.concurrent.CompletableFuture

object Antipatterns extends App {

  def baseGuarantee[R](f: => R)(finalizer: => Unit): R = try f
  finally finalizer

  def badGuarantee[R](f: => R)(finalizer: => Unit): R = try
  //anti=pattern
  f match {
    case ref: Future[_] =>
      ref
        .transform { r => finalizer; r }
        .asInstanceOf[R]
    case result =>
      finalizer
      result
  } catch {
    case NonFatal(e) =>
      finalizer
      throw e
  }

  /*   baseGuarantee {
    println(s"executin")
    1 + 1
  } {
    println("done")
  } */

//   badGuarantee {
//     Future {
//       println(s"executin3")
//       1 + 1
//     }
//   } {
//     println("done")
//   }

  badGuarantee {
    Task {
      println("rr")
    }
  } {
    println("DONE")
  }

}





