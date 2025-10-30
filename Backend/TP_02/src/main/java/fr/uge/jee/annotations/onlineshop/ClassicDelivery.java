package fr.uge.jee.annotations.onlineshop;

public class ClassicDelivery implements Delivery {

  private final int averageDeliveryTime;

  public ClassicDelivery (int averageDeliveryTime) {
    this.averageDeliveryTime = averageDeliveryTime;
  }

  @Override
  public String description () {
    return "Standard delivery with delay of " + averageDeliveryTime + " days";
  }

}
