package fr.uge.scala

object Main {
  def main(args: Array[String]): Unit = {

    // Question 1:
    val triplets = List((1, 0, 5), (5, 1, 8), (8, 2, 1), (2, 0, 6), (3, 0, 6), (6, 1, 9), (5, 1, 9), (9, 3, 11), (9, 4, 12), (4, 0, 7), (7, 1, 9), (7, 2, 10),
      (14, 1, 15), (15, 1, 16), (14, 1, 16), (17, 0, 18), (18, 0, 19), (19, 1, 20), (20, 0, 17))
    println(s"triplets : $triplets")
    println()

    val tuple: List[(Int, Int)] = triplets.map(x => (x._1, x._3))
    println(s"Question 1 : (sujet, objet) : $tuple")
    println()


    // Question 2:
    val sujet = tuple.map(x => x._1).distinct
    val objet = tuple.map(x => x._2).distinct

    val racines = sujet.diff(objet)
    println(s"Question 2 : Racine : $racines")
    println()

    // Question 3:
    val feuilles = objet.diff(sujet)
    println(s"Question 3 : Feuilles : $feuilles")
    println()

    // Question 4.a:
    def join(pair1: List[(Int, Int)], pair2: List[(Int, Int)]): List[(Int, Int)] = {
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

    // Question 4.b
    var fermetureTransitive: List[(Int, Int)] = tuple
    var newSize: Int = 0

    do {
      newSize = fermetureTransitive.size
      fermetureTransitive = (tuple ++ join(fermetureTransitive, fermetureTransitive)).distinct
    } while (newSize != fermetureTransitive.size)

    println(s"Question 4 : Fermuture transitive : $fermetureTransitive\nTaille de la fermeture : ${fermetureTransitive.size}")
    println()

    // Question 5:
    val pairesInferees = fermetureTransitive.diff(tuple).sortBy(x => x._1)
    println(s"Question 5 : Paires inférées trié : $pairesInferees")
    println(s"Taille paire inférées : ${pairesInferees.size}")
    println()


    // Question 6:
    def join2(pair1: List[(Int, Int)], pair2: List[(Int, Int)]): List[(Int, Int)] = {
      pair1 match {
        case Nil => List()
        case x :: xs => pair2.filter(a => a._1 == x._2).map(a => (x._1, a._2)) ++ join2(xs, pair2)
      }
    }

    var fermetureTransitiveRec = tuple
    var newSizeRec = 0

    do {
      newSizeRec = fermetureTransitiveRec.size
      fermetureTransitiveRec = (tuple ++ join2(fermetureTransitiveRec, fermetureTransitiveRec)).distinct
    } while (newSizeRec != fermetureTransitiveRec.size)

    println(s"Question 6 : FermetureTransitiveRec : $fermetureTransitive\nTaille fermetureRec : ${fermetureTransitiveRec.size}")
    println()

    // Question 7:
    def hashJoin(pair1: List[(Int, Int)], pair2: List[(Int, Int)]) = {
      val hashMap = pair2.groupMap(_._1)(_._2)
      var resList: List[(Int, Int)] = List()

      for (pair <- pair1) {
        val value2 = hashMap.getOrElse(pair._1, Nil)
        if (value2 != Nil) {
          for (value <- value2) {
            resList = resList :+ (pair._1, value)
          }
        }
      }
      resList
    }

    // Question 9:
    def boucleFermeture(pair: List[(Int, Int)], joinFunction: (List[(Int, Int)], List[(Int, Int)]) => List[(Int, Int)]): List[(Int, Int)] = {
      var fermetureTransitive: List[(Int, Int)] = pair
      var oldSize: Int = 0

      do {
        oldSize = fermetureTransitive.size
        fermetureTransitive = (pair ++ joinFunction(fermetureTransitive, fermetureTransitive)).distinct
      } while (oldSize != fermetureTransitive.size)

      println(s"Question 9 : Taile : ${fermetureTransitive.size}")
      fermetureTransitive
    }

    println(s"${boucleFermeture(tuple, hashJoin).sortBy(x => x._1)}")
    println()

  }
}