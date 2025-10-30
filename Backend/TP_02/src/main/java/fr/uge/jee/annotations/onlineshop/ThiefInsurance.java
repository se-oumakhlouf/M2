package fr.uge.jee.annotations.onlineshop;

public class ThiefInsurance implements Insurance {

  @Override
  public String description () {
    return "Thief insurance";
  }

}
