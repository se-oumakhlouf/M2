package org.sample.devops.service.authentication.db;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UsersDB {

  private static final Map<String, User> db = new HashMap<>();

  @PostConstruct
  private void initialize(){
    db.put("user1@mail.com", new User("user1@mail.com", "User1FirstName", "User1LastName", "password1"));
    db.put("user2@mail.com", new User("user2@mail.com", "User2FirstName", "User2LastName", "password2"));
  }

  public Optional<User> getUserByMail(String mail){
    return Optional.ofNullable(db.get(mail));
  }
}