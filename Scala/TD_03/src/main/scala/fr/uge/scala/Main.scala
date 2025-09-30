package fr.uge.scala

object Main {
  def main(args: Array[String]): Unit = {
    val triplets = List((1,0,5),(5,1,8),(8,2,1),(2,0,6),(3,0,6),(6,1,9),(5,1,9),(9,3,11),(9,4,12),(4,0,7),(7,1,9),(7,2,10),
      (14,1,15),(15,1,16),(14,1,16),(17,0,18),(18,0,19),(19,1,20),(20,0,17))
    println(s"triplets : $triplets")

    val tuple: List[(Int, Int)] = triplets.map(x => (x._1, x._3))
    println(s"(sujet, objet) : $tuple")

    val sujet = tuple.map(x => x._1).distinct
    val objet = tuple.map(x => x._2).distinct

    val racines = sujet.diff(objet)
    println(s"Racine : $racines")

    val feuilles = objet.diff(sujet)
    println(s"Feuilles : $feuilles")

    def join(pair1 : List[(Int,Int)], pair2: List[(Int,Int)]) : List[(Int, Int)] = {
      val pair2Reverse = pair2.map(x => (x._2, x._1))
      val sortedRP2 = pair2Reverse.sortBy(x => x._1)

      var resList: List[(Int, Int)] = List()

      for (x <- pair1) {
        for (y <- sortedRP2) {
          if (x._1 == y._1) {
            resList = resList :+ (y._2, x._2)
          }
        }
      }
      resList
    }

    val testJoin = join(tuple, tuple)
    println(testJoin)

    def join2(pair1: List[(Int, Int)], pair2: List[(Int, Int)]): List[(Int, Int)] = {
      pair1 match {
        case Nil => List()
        case x::xs => pair2.filter(a => a._1 == x._2).map(a => (x._1, a._2)) ++ join2(xs, pair2)
      }
    }

    var newTuple: List[(Int, Int)] = tuple
    var newSize: Int = 0

    do {
      newSize = newTuple.size
      newTuple = tuple ++ join2(newTuple, newTuple).distinct
      println(s"old : $newSize\nnew : $newTuple")
    } while (newSize != newTuple.size)

    println(newTuple)
    println(newSize)



  }
}