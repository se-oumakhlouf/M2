package fr.uge.jee.annotations.onlineshop;

public class ReturnInsurance implements Insurance {

  private final boolean membersOnly;

  public ReturnInsurance (boolean membersOnly) {
    this.membersOnly = membersOnly;
  }

  @Override
  public String description () {
    return "Return insurance" + (membersOnly ? " only for members" : "");
  }
}
