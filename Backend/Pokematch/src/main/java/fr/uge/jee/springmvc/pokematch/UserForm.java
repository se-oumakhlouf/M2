package fr.uge.jee.springmvc.pokematch;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserForm {

  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must be composed of letters only")
  private String firstName;

  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must be composed of letters only")
  private String lastName;
}
