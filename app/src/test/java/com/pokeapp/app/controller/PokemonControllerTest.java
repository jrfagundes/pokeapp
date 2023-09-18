package com.pokeapp.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pokeapp.app.Enum.SortOptions;
import com.pokeapp.app.service.PokemonService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @Test
    public void testGetPokemons() throws Exception {
        when(pokemonService.getPokemons(anyString(), any(SortOptions.class)))
            .thenReturn(Map.of("result", List.of("bulbasaur", "ivysaur", "venusaur")));

        mockMvc.perform(get("/pokemons")
                    .param("query", "bul")
                    .param("sort", "name", "size"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0]").value("bulbasaur"))
                .andExpect(jsonPath("$.result[1]").value("ivysaur"))
                .andExpect(jsonPath("$.result[2]").value("venusaur"));
    }

    @Test
    public void testGetPokemonsHighlight() throws Exception {
        Map<String, String> pokemonHighlight = Map.of(
                "name", "bulbasaur",
                "highlight", "<pre>bul</pre>basaur"
        );

        when(pokemonService.getPokemonsHighlight(anyString(), any(SortOptions.class)))
            .thenReturn(Map.of("result", Collections.singletonList(pokemonHighlight)));

        mockMvc.perform(get("/pokemons/highlight")
                    .param("query", "bul")
                    .param("sort", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].name").value("bulbasaur"))
                .andExpect(jsonPath("$.result[0].highlight").value("<pre>bul</pre>basaur"));
    }
}
