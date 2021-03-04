package part1recap

object MultithreadingRecap extends App {
  
    val aTh = new Thread(() => println("in parallel"))

    val thrHello = new Thread(() => (1 to 100).foreach(_ => println("hello")))
    val thrBye = new Thread(() => (1 to 100).foreach(_ => println("bye")))
    thrHello.start()
    
    thrBye.start()
}

class BankAcc(private var amt: Int) {
    override def toString(): String = s"AMOUNT: $amt"

    def withdraw(money:Int) = this.amt -= money

}

/*
BA(10000)

T1 -> withdraw 1000
T2 -> withdraw 2000

T1 -> this.amount = this.amount - .... //PREEMPTED by the OS
T2 -> this.amount = this.amount - 2000 = 8000
T1 -> -1000 = 9000
=> result = 9000
this.amount = this.amount - 1000 NOT ATOMIC
*/

// inter-thread communications on the jvm
// wait - notify

//scala futures

