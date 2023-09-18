package com.pokeapp.app.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.pokeapp.app.service.PokemonService;

public class PokemonServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        
        Map<String, List<Map<String, String>>> mockApiResponse = Map.of(
                "results", List.of(
                        Map.of("name", "bulbasaur"),
                        Map.of("name", "ivysaur")
                        // ... (adicionar mais pokémons conforme necessário)
                )
        );
        
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockApiResponse);
    }

    @Test
    public void testGetPokemons() {
        Map<String, List<String>> pokemons = pokemonService.getPokemons(null, null);
        
        assertNotNull(pokemons, "The result should not be null");
        assertFalse(pokemons.get("result").isEmpty(), "The result list should not be empty");
    }

    @Test
    public void testGetPokemonsWithQuery() {
        Map<String, List<String>> pokemons = pokemonService.getPokemons("bulbasaur", null);
        
        assertNotNull(pokemons, "The result should not be null");
        assertEquals(1, pokemons.get("result").size(), "The result list should contain one entry");
        assertEquals("bulbasaur", pokemons.get("result").get(0), "The first entry should be 'bulbasaur'");
    }
}
