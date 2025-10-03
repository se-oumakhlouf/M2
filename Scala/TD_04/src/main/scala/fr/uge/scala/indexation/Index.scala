package fr.uge.scala.indexation

import scala.collection.immutable.HashMap

class Index {

  private val indentSize = java.lang.Long.BYTES
  private var fileIndex = 0;
  var map: HashMap[Long, Long] = HashMap();

  def add(key: Long): Unit = {
    map += (key -> fileIndex)
    fileIndex += indentSize
  }

  def size(): Long = {
    map.size
  }


}


