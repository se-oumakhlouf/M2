package fr.uge.td.scalaEvenBetter

class Bike(val color: String, val wheelSize: Int, val speed: Int = 0) {

  def speedUp(speedToAdd: Int): Either[String, Bike] = {
    if (speedToAdd < 0) Left(s"Adding a negative speed is not allowed, speedToAdd = $speedToAdd")
    else Right (new Bike(color, wheelSize, speed + speedToAdd))
  }

  def brake(speedToRemove: Int): Either[String, Bike] = {
    if (speedToRemove < 0) Left(s"Substracting negative speed is not allowed, speedToRemove = $speedToRemove")
    else Right (new Bike(color, wheelSize, speed - speedToRemove)
    )
  }

  override def toString: String = s"Bike : Color = $color, WheelSize = $wheelSize, Speed = $speed"
}

object Bike extends App {
  private val bike = new Bike("Green", 10)
  println(s"Initialisation : $bike")
  val speedUpBike = bike.speedUp(10)
  println(s"SpeedUp(10) : $speedUpBike")
  val brakeBike = bike.brake(5)
  println(s"Brake(5) : $brakeBike")

  val bikeErrorSpeedUp = bike.speedUp(-10)
  println(bikeErrorSpeedUp)
  val bikeErrorBrake = bike.brake(-5)
  println(bikeErrorBrake)
}