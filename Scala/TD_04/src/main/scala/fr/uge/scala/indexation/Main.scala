package fr.uge.scala.indexation

import scala.util.Random

object Main {

  def main(args: Array[String]): Unit = {
    val rand = new Random()
    val index: Index = new Index()

    val fileStart = "memory_"
    val fileManager: FileManager = new FileManager(fileStart)

    val maxKey = rand.nextLong(4)
    val iterations = 10
    fileManager.writeALot(iterations, maxKey, index)

    index.map.foreach(pair => {
      val read = fileManager.readValue(pair._2)
      println(s"Last value for key : ${pair._1} -> $read")
    })
    fileManager.close()
  }
}