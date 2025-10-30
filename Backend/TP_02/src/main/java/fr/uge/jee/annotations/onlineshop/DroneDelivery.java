package fr.uge.jee.annotations.onlineshop;

public class DroneDelivery implements Delivery {

  @Override
  public String description () {
    return "Delivery by drone";
  }

}
