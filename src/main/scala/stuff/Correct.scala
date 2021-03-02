package stuff

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal
import java.util.concurrent.CompletableFuture
import scala.annotation.implicitNotFound

object Correct extends App {

  def guarantee[R: CanGuarantee](f: => R)(fin: => Unit): R =
    implicitly[CanGuarantee[R]].guarantee(f)(fin)

  val thunk = new Thunk(() => println("in the thunk"))

  guarantee {
    thunk
  } {
    println("thunk finished")
  }

  guarantee {
    Future {
      println("futured xxx")
    }
  } {
    println("futured-finished")
  }

  /* guarantee {
    CompletableFuture.runAsync { () =>
      println("Running!")
    }
  } {
    println("Done!")
  } */

}

final class Thunk[A](val run: () => A)

/* object Thunk {

  // Extending our logic with a new data type
  implicit def canGuarantee[A]: CanGuarantee[Thunk[A]] =
    new CanGuarantee[Thunk[A]] {

      def guarantee(f: => Thunk[A])(finalizer: => Unit): Thunk[A] =
        new Thunk(() =>
          try f.run()
          finally finalizer
        )
    }
} */

@implicitNotFound("""Cannot find implicit value for CanGuarantee[${R}].
If this value is synchronously calculated via an effectful function,
then use CanGuarantee.synchronous to create one.""")
trait CanGuarantee[R] {
  def guarantee(f: => R)(fin: => Unit): R
}

object CanGuarantee {

  implicit def futureInstance[A]: CanGuarantee[Future[A]] =
    new CanGuarantee[Future[A]] {

      def guarantee(f: => Future[A])(fin: => Unit): Future[A] = Future(f).flatten.transform { r =>
        fin
        r
      }
    }

  implicit def thunkInstance[A]: CanGuarantee[Thunk[A]] =
    new CanGuarantee[Thunk[A]] {

      def guarantee(f: => Thunk[A])(fin: => Unit): Thunk[A] = new Thunk(() =>
          try f.run()
          finally fin
        )
    }

  def synchronous[R]: CanGuarantee[R] =
    new CanGuarantee[R] {

      def guarantee(f: => R)(fin: => Unit): R = try {
        println("SYNC")
        f
      } finally fin
    }

}
