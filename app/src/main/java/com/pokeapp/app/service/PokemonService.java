package com.pokeapp.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pokeapp.app.Enum.SortOptions;

@Service
public class PokemonService {

    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon?limit=1500";

    public Map<String, List<String>> getPokemons(String query, SortOptions sort) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> pokeApiResponse = restTemplate.getForObject(POKEAPI_URL, Map.class);
    
        List<Map<String, String>> results = (List<Map<String, String>>) pokeApiResponse.get("results");
        List<String> pokemonNames = new ArrayList<>();
        for (Map<String, String> result : results) {
            pokemonNames.add(result.get("name"));
        }
    
        if (query != null && !query.isBlank()) {
            List<String> filteredNames = new ArrayList<>();
            for (String name : pokemonNames) {
                if (name.toLowerCase().contains(query.toLowerCase())) {
                    filteredNames.add(name);
                }
            }
            pokemonNames = filteredNames;
        }
    
        if (sort == SortOptions.NAME) {
            pokemonNames = bubbleSort(pokemonNames, Function.identity(), SortOptions.NAME);
        } else if (sort == SortOptions.SIZE) {
            pokemonNames = bubbleSort(pokemonNames, Function.identity(), SortOptions.SIZE);
        }        
    
        Map<String, List<String>> response = new HashMap<>();
        response.put("result", pokemonNames);
    
        return response;
    }

    public Map<String, List<Map<String, String>>> getPokemonsHighlight(String query, SortOptions sort) {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, Object> pokeApiResponse = restTemplate.getForObject(POKEAPI_URL, Map.class);

    List<Map<String, String>> results = (List<Map<String, String>>) pokeApiResponse.get("results");
    List<Map<String, String>> pokemonHighlights = new ArrayList<>();

    for (Map<String, String> result : results) {
        String name = result.get("name");
        Map<String, String> pokemonHighlight = new HashMap<>();
        pokemonHighlight.put("name", name);
        if (query != null && !query.isBlank() && name.toLowerCase().contains(query.toLowerCase())) {
            pokemonHighlight.put("highlight", name.toLowerCase().replace(query.toLowerCase(), "<pre>" + query.toLowerCase() + "</pre>"));
        } else {
            pokemonHighlight.put("highlight", name);
        }
        pokemonHighlights.add(pokemonHighlight);
    }

    if (query != null && !query.isBlank()) {
        List<Map<String, String>> filteredHighlights = new ArrayList<>();
        for (Map<String, String> pokemonHighlight : pokemonHighlights) {
            if (pokemonHighlight.get("name").toLowerCase().contains(query.toLowerCase())) {
                filteredHighlights.add(pokemonHighlight);
            }
        }
        pokemonHighlights = filteredHighlights;
    }

    if (sort == SortOptions.NAME) {
        pokemonHighlights = bubbleSort(pokemonHighlights, pokemon -> pokemon.get("name"), SortOptions.NAME);
    } else if (sort == SortOptions.SIZE) {
        pokemonHighlights = bubbleSort(pokemonHighlights, pokemon -> pokemon.get("name"), SortOptions.SIZE);
    }

    Map<String, List<Map<String, String>>> response = new HashMap<>();
    response.put("result", pokemonHighlights);

    return response;
}

    

    public <T> List<T> bubbleSort(List<T> list, Function<T, String> keyExtractor, SortOptions sortOption) {
        int n = list.size();
        T temp;    
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                boolean condition;
                if (sortOption == SortOptions.NAME) {
                    condition = keyExtractor.apply(list.get(j - 1)).compareTo(keyExtractor.apply(list.get(j))) > 0;
                } else {
                    condition = keyExtractor.apply(list.get(j - 1)).length() > keyExtractor.apply(list.get(j)).length();
                }
    
                if (condition) {
                    temp = list.get(j - 1);
                    list.set(j - 1, list.get(j));
                    list.set(j, temp);
                }
            }
        }    
        return list;
    }
    
    
}
