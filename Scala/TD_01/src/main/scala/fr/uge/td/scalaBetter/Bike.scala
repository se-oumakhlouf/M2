package fr.uge.td.scalaBetter

class Bike(val color: String, val wheelSize: Int, val speed: Int = 0) {

  def speedUp(speedToAdd: Int): Bike = {
    if (speedToAdd < 0) {
      throw new UnsupportedOperationException()
    }
    new Bike(color, wheelSize, speedToAdd + speed)
  }

  def brake(speedToRemove: Int): Bike = {
    if (speedToRemove < 0) {
      throw new UnsupportedOperationException()
    }
    new Bike(color, wheelSize, speed - speedToRemove
    )
  }

  override def toString: String = s"Bike : Color = $color, WheelSize = $wheelSize, Speed = $speed"
}

object Bike extends App {
  private val bike = new Bike("Green", 10)
  println(s"Initialisation : $bike")
  val speedUpBike = bike.speedUp(10)
  println(s"SpeedUp(10) : $speedUpBike")
  val brakeBike = speedUpBike.brake(5)
  println(s"Brake(5) : $brakeBike")
}