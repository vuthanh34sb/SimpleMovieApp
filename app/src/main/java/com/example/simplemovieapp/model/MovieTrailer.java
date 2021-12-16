package com.example.simplemovieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class MovieTrailer {
    private final int id;
    @SerializedName("results")
    private final List<Trailer> results;

    public final int getId() {
        return this.id;
    }

    public final List<Trailer> getResults() {
        return this.results;
    }

    public MovieTrailer(int id, List<Trailer> results) {
        super();
        this.id = id;
        this.results = results;
    }
}