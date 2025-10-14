package fr.uge.scala.indexation

import java.io.RandomAccessFile
import java.time.LocalDateTime
import scala.util.Random

class FileManager(fileName: String) {

  private var now = LocalDateTime.now().toString
  private var raf: RandomAccessFile = new RandomAccessFile(fileName + now, "rw")
  private val rapport = 0.4

  private def write(key: Long, value: Long, index: Index, addToIndex: Boolean, raf: RandomAccessFile): Unit = {
    raf.seek(raf.length())
    val bytes = s"$key | $value\n".getBytes()
    raf.write(bytes)
    if (addToIndex) {
      index.add(key, bytes.length)
    }
  }

  def readValue(seek: Long): String = {
    raf.seek(seek)
    val line = raf.readLine()
    if (line == null) {
      return "Error"
    }
    line.split("\\|")(1)
  }

  def writeALot(iterations: Long, maxKey: Long, index: Index): Unit = {
    val rand = new Random()
    val range = 0L to iterations

    for (_ <- range) {
      val value = rand.nextLong()
      val key = rand.nextLong(maxKey)
      write(key, value, index, addToIndex = true, raf)
      compact(index, raf)
    }
  }

  private def lineCount(file: String): Int = {
    val src = io.Source.fromFile(file)
    try {
      src.getLines().length
    } finally {
      src.close()
    }

  }

  private def compact(index: Index, raf: RandomAccessFile): Unit = {
    val ratio = index.map.size.toFloat / lineCount(fileName + now)
    if (ratio < rapport) {
      close()
      val now2 = LocalDateTime.now().toString
      var raf2: RandomAccessFile = new RandomAccessFile(fileName + now2, "rw")
      index.map.foreach(pair => {
        write(pair._1, readValue(pair._2).toLong, index, addToIndex = false, raf2)
      })
      index.compact()
      this.raf = raf2
      this.now = now2
    }
  }

  def close(): Unit = {
    raf.close()
  }

}
