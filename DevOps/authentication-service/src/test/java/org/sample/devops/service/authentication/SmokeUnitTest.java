package org.sample.devops.service.authentication;

import org.junit.jupiter.api.Test;
import org.sample.devops.service.authentication.controller.AuthenticationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeUnitTest {

  @Autowired
  private AuthenticationController authenticationController;

  @Test
  void loadContext() throws Exception {
    assertThat(authenticationController).isNotNull();
  }
}
