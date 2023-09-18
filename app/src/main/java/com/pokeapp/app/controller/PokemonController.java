package com.pokeapp.app.controller;
import com.pokeapp.app.Enum.SortOptions;
import com.pokeapp.app.service.PokemonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public Map<String, List<String>> getPokemons(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "NAME") String sort) {
        return pokemonService.getPokemons(query, SortOptions.fromString(sort));
    }

    @GetMapping("/highlight")
    public Map<String, List<Map<String, String>>> getPokemonsHighlight(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "NAME") String sort) {
        return pokemonService.getPokemonsHighlight(query, SortOptions.fromString(sort));
    }
}