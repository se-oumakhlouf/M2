package fr.uge.jee.springmvc.pokematch.controller;

import fr.uge.jee.springmvc.pokematch.UserForm;
import fr.uge.jee.springmvc.pokematch.dto.Pokemon;
import fr.uge.jee.springmvc.pokematch.service.PokemonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PokemonController {

  private final PokemonService pokemonService;

  @Value("${pokemon.number_of_favorites}")
  private int limit;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("userForm", new UserForm());
    loadTopPokemons(model);
    return "pokematch";
  }

  @PostMapping("/")
  public String resolve(@Valid @ModelAttribute("userForm") UserForm form,
                        BindingResult result,
                        Model model) {

    if (result.hasErrors()) {
      return "pokematch";
    }

    Pokemon fetiche = pokemonService.findFavorite(form.getFirstName(), form.getLastName());

    if (fetiche != null) {
      model.addAttribute("pokemon", fetiche);
    }

    loadTopPokemons(model);
    return "pokematch";
  }

  private void loadTopPokemons(Model model) {
    var topList = pokemonService.getTopPokemons(limit);
    model.addAttribute("topPokemons", topList);
    model.addAttribute("topCount", limit);
  }

}
