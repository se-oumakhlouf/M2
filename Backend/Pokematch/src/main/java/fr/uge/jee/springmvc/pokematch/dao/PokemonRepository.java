package fr.uge.jee.springmvc.pokematch.dao;

import fr.uge.jee.springmvc.pokematch.dto.PokeApiResponse;
import fr.uge.jee.springmvc.pokematch.dto.Pokemon;
import fr.uge.jee.springmvc.pokematch.dto.PokemonFavorite;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PokemonRepository {

  private final WebClient webClient;
  private final List<Pokemon> pokemons = new ArrayList<>();
  private final ConcurrentHashMap<Pokemon, Long> counts = new ConcurrentHashMap<>();

  public PokemonRepository (WebClient webClient) {
    this.webClient = webClient;
  }

  @PostConstruct
  public void init () {
    var response = webClient.get()
        .uri("https://pokeapi.co/api/v2/pokemon?limit=40")
        .retrieve()
        .bodyToMono(PokeApiResponse.class)
        .block();

    if (response != null && response.results() != null) {
      pokemons.addAll(response.results());
    }
  }

  public List<Pokemon> getAllPokemons () {
    return Collections.unmodifiableList(pokemons);
  }

  public void addPokemon (Pokemon pokemon) {
    counts.compute(pokemon, (key, value) -> (value == null) ? 1L : value + 1);
  }

  public List<PokemonFavorite> getTopFavorites(int limit) {
    return counts.entrySet().stream()
        .map(entry -> new PokemonFavorite(entry.getKey().name(), entry.getValue()))
        .sorted(Comparator.comparing(PokemonFavorite::count).reversed())
        .limit(limit)
        .collect(Collectors.toList());
  }

}
