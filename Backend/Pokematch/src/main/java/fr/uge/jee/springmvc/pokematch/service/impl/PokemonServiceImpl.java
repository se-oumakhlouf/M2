package fr.uge.jee.springmvc.pokematch.service.impl;

import fr.uge.jee.springmvc.pokematch.dao.PokemonRepository;
import fr.uge.jee.springmvc.pokematch.dto.Pokemon;
import fr.uge.jee.springmvc.pokematch.dto.PokemonFavorite;
import fr.uge.jee.springmvc.pokematch.service.PokemonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PokemonServiceImpl implements PokemonService {

  private final PokemonRepository pokemonRepository;

  @Override
  public Pokemon findFavorite(String firstName, String lastName) {
    List<Pokemon> pokemons = pokemonRepository.getAllPokemons();

    if (pokemons.isEmpty()) return null;

    long targetHash = (firstName + lastName).hashCode();

    Pokemon bestMatch = pokemons.get(0);
    long minDiff = Long.MAX_VALUE;

    for (Pokemon pokemon : pokemons) {
      long diff = Math.abs(pokemon.name().hashCode() - targetHash);
      if (diff < minDiff) {
        minDiff = diff;
        bestMatch = pokemon;
      }
    }
    pokemonRepository.addPokemon(bestMatch);
    return bestMatch;
  }

  @Override
  public List<PokemonFavorite> getTopPokemons(int limit) {
    return pokemonRepository.getTopFavorites(limit);
  }


}
