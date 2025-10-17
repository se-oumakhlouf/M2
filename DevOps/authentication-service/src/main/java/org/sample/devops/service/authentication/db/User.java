package org.sample.devops.service.authentication.db;

public class User {

  private final  String mail;
  private final String password;
  private final String firstName;
  private final String lastName;

  public User(String mail, String firstName, String lastName, String password) {
    this.mail = mail;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getMail() {
    return mail;
  }

  public String getPassword () {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
