package com.pokeapp.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    private String name;
    private String url;

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
