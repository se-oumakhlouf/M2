package fr.uge.jee.annotations.onlineshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

@Configuration
@PropertySource("classpath:onlineshop.properties")
public class ApplicationConfig {

  @Value("${onlineshop.name}")
  private String name;

  @Value("${onlineshop.standarddelivery.delay}")
  private int averageDeliveryTime;

  @Value("${onlineshop.returninsurance.membersonly}")
  private boolean membersOnly;

  @Bean
  ReturnInsurance returnInsurance() {
    return new ReturnInsurance(membersOnly);
  }

  @Bean
  DroneDelivery droneDelivery() {
    return new DroneDelivery();
  }

  @Bean
  ClassicDelivery classicDelivery() {
    return new ClassicDelivery(averageDeliveryTime);
  }

  @Bean
  OnlineShop onlineShop(Set<Delivery> deliveryOptions, Set<Insurance> insurances) {
    return new OnlineShop(name, deliveryOptions, insurances);
  }

}
