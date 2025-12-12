package fr.uge.jee.springmvc.pokematch.service;

import fr.uge.jee.springmvc.pokematch.dto.Pokemon;
import fr.uge.jee.springmvc.pokematch.dto.PokemonFavorite;

import java.util.List;

public interface PokemonService {

  Pokemon findFavorite(String firstName, String lastName);

  List<PokemonFavorite> getTopPokemons(int limit);
}
