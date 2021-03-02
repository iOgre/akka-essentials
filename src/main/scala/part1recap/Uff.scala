package part1recap

object Uff extends App {
  val number = "888#AAA_BBB_ccc$ddd"

  number match {
      case s"$pref#${aff1}_${aff2}_$aff3$$$aff4" =>
      println(s"pref = $pref")
      println(s"aff1 = $aff1")
      println(s"aff2 = $aff2")
      println(s"aff3 = $aff3")
      println(s"aff4 = $aff4")
      
  }
}
