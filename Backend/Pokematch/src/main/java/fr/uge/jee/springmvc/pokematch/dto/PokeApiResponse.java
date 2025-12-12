package fr.uge.jee.springmvc.pokematch.dto;

import java.util.List;

public record PokeApiResponse(List<Pokemon> results) {}
