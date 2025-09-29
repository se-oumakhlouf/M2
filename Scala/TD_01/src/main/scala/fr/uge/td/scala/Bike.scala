package fr.uge.td.scala

class Bike(val color: String, val wheelSize: Int, var speed: Int = 0) {

  def speedUp(speedToAdd: Int): Unit = {
    if (speedToAdd < 0) {
      throw new UnsupportedOperationException()
    }
    speed += speedToAdd
  }

  def brake(speedToRemove: Int): Unit = {
    if (speedToRemove < 0) {
      throw new UnsupportedOperationException()
    }
    speed -= speedToRemove
  }

  override def toString: String = s"Bike : Color = $color, WheelSize = $wheelSize, Speed = $speed"
}

object Bike extends App {
  private val bike = new Bike("Green", 10)
  println(s"Initialisation : $bike")
  bike.speedUp(10)
  println(s"SpeedUp(10) : $bike")
  bike.brake(5)
  println(s"Brake(5) : $bike")
}