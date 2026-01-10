package fr.uge.jee.springmvc.pokematch.controller;

import fr.uge.jee.springmvc.pokematch.dao.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ImageController {

  private final PokemonRepository pokemonRepository;

  @GetMapping(value = "/pokemon/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
  @ResponseBody
  public byte[] getImage(@PathVariable String name) {
    return pokemonRepository.getPokemonImage(name);
  }
}
