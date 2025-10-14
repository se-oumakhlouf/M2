package fr.uge.scala.indexation

import scala.collection.immutable.HashMap

class Index {

  private var fileIndex = 0;
  var map: HashMap[Long, Long] = HashMap();

  def add(key: Long, size: Int): Unit = {
    map += (key -> fileIndex)
    fileIndex += size;
  }

  def compact(): Unit = {
    val minIndexValue = map.values.min
    map.foreach(pair => {
      map += (pair._1 -> (pair._2 - minIndexValue))
    })
  }


}


