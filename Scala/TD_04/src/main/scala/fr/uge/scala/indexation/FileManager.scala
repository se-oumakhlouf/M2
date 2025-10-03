package fr.uge.scala.indexation

import java.io.RandomAccessFile
import scala.util.Random

class FileManager {

  val raf: RandomAccessFile = new RandomAccessFile("test.txt", "rw")

  def write(key: Long, value: Long, index: Index): Unit = {
    raf.seek(raf.length())
    raf.write(s"$key | $value\n".getBytes)
    index.add(key)
  }

  def readValue(seek: Long): Long = {
    var skip: Long = java.lang.Long.BYTES + 3
    raf.seek(seek)
    raf.skipBytes(skip.toInt) // sauter " | "
    raf.readLong()
  }

  def writeALot(iterations: Long, maxKey: Long, index: Index) = {
    val rand = new Random()
    val range = 0L to iterations

    for (_ <- range) {
      val value = rand.nextLong()
      val key = rand.nextLong(maxKey)
      write(key, value, index)
    }

  }

  def close(): Unit = {
    raf.close()
  }

}
