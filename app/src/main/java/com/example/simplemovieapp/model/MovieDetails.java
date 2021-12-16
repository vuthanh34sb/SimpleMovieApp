package com.example.simplemovieapp.model;

import com.google.gson.annotations.SerializedName;

public final class MovieDetails {
    private final int budget;
    private final int id;

    private final String overview;
    private final double popularity;
    @SerializedName("poster_path")

    private final String posterPath;
    @SerializedName("release_date")

    private final String releaseDate;
    private final long revenue;
    private final int runtime;

    private final String status;

    private final String tagline;

    private final String title;
    private final boolean video;
    @SerializedName("vote_average")
    private final float rating;

    @SerializedName("vote_count")
    private final int voteCount;



    public final int getBudget() {
        return this.budget;
    }

    public final int getId() {
        return this.id;
    }


    public final String getOverview() {
        return this.overview;
    }

    public final double getPopularity() {
        return this.popularity;
    }


    public final String getPosterPath() {
        return this.posterPath;
    }


    public final String getReleaseDate() {
        return this.releaseDate;
    }

    public final long getRevenue() {
        return this.revenue;
    }

    public final int getRuntime() {
        return this.runtime;
    }


    public final String getStatus() {
        return this.status;
    }


    public final String getTagline() {
        return this.tagline;
    }


    public final String getTitle() {
        return this.title;
    }

    public final boolean getVideo() {
        return this.video;
    }

    public final float getRating() {
        return this.rating;
    }

    public final int getVoteCount() {
        return this.voteCount;
    }

    public MovieDetails(int budget, int id, String overview, double popularity, String posterPath, String releaseDate, long revenue, int runtime, String status, String tagline, String title, boolean video, float rating, int voteCount) {
        this.budget = budget;
        this.id = id;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.rating = rating;
        this.voteCount = voteCount;
    }
}