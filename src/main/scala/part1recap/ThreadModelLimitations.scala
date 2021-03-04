package part1recap

import scala.concurrent.Future

//Rant 1 OOP encapsulation valid for single threaded model only
object Rant1 extends App {

  //Rants
  val acc = new BankAccount(2000)
  for (_ <- 1 to 1000)
    new Thread(() => acc.withdraw(1)).start()

  for (_ <- 1 to 1000)
    new Thread(() => acc.deposit(1)).start()

  println(acc.getAmount)

  val acc2 = new BankAccount(2000)
  for (_ <- 1 to 1000)
    new Thread(() => acc2.safeWithdraw(1)).start()

  for (_ <- 1 to 1000)
    new Thread(() => acc2.safeDeposit(1)).start()

  println(acc2.getAmount)

  //OOP encaps broken in multithreaded env
  // needs syncheonisations
  //deadlocks, livelocks

}

/**
 * Rant2 - delegating smth to a thread is pain
 */
object Rant2 extends App {
  var task: Runnable = null

  val runningThread: Thread = new Thread(() => {
    while (task == null)
      runningThread.synchronized {
        println("[bg] i have a task")
      }

    task.synchronized {
      println("[bg] i have a task!")
      task.run()
      task = null
    }
  })

  def delegateToBg(r: Runnable) = {
    if (task == null) task = r
    runningThread.synchronized {
      runningThread.notify()
    }
  }

  runningThread.start()
  Thread.sleep(1000)
  delegateToBg(() => println(42))
  Thread.sleep(1000)
  delegateToBg(() => println("this should run in bg"))

}

/**
 * Rant 3 - tracing and handling errors in multithreaded env is pain
 */
object Rant3 extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  val futures = (0 to 9)
    .map(i => 100000 * i until 100000 * (i + 1))
    .map(range =>
      Future {
        println(range)
        if (range.contains(546735))
         throw new RuntimeException("invalid number")
        range.sum
      }
    )

  val sumFuture = Future.reduceLeft(futures)(_ + _)
  sumFuture.onComplete(println)

}

class BankAccount(private var amt: Int) {
  override def toString(): String = "" + amt
  def withdraw(money: Int)        = this.amt -= money
  def deposit(money: Int)         = this.amt += money

  def safeWithdraw(money: Int) = this.synchronized(this.amt -= money)
  def safeDeposit(money: Int)  = this.synchronized(this.amt += money)
  def getAmount                = amt
}
