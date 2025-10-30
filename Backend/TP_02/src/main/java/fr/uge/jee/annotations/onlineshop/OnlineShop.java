package fr.uge.jee.annotations.onlineshop;

import java.util.Set;

public class OnlineShop {

  private final String name;
  private final Set<Delivery> deliveryOptions;
  private final Set<Insurance> insurances;

  public OnlineShop (String name, Set<Delivery> deliveryOptions, Set<Insurance> insurances) {
    this.name = name;
    this.deliveryOptions = deliveryOptions;
    this.insurances = insurances;
  }

  public void printDescription() {
    System.out.println(name);
    System.out.println("Delivery options : ");
    deliveryOptions.forEach(opt -> System.out.println("\t" + opt.description()));
    System.out.println("Insurances options : ");
    insurances.forEach(insurance -> System.out.println("\t" + insurance.description()));
  }
}
