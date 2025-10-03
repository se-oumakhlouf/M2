package fr.uge.scala.indexation

import scala.util.Random
import java.io.RandomAccessFile

object Main {
  def main(args: Array[String]): Unit = {
    val raf = new RandomAccessFile("test.txt", "rw")
    val rand = new Random()
    val index: Index = new Index()
    val fileManager: FileManager = new FileManager()

    val maxKey = rand.nextLong(30)
    val iterations = 100
    fileManager.writeALot(iterations, maxKey, index)


    val fileIndex = index.map.getOrElse(10L, 0L)
    println(fileIndex)
    val value = fileManager.readValue(fileIndex)
    println(value)
    println(index.map)
    fileManager.close()
  }
}