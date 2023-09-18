package com.pokeapp.app.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {
    private float count;
    private String next;
    private String previous = null;
    ArrayList<Pokemon> results = new ArrayList<Pokemon>();
    public Payload(float count, String next, String previous, ArrayList<Pokemon> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }
    
}
