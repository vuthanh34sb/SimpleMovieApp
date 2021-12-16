package com.example.simplemovieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class MovieResponse {
   private final int page;
   @SerializedName("results")
   private final List<Movie> movieList;
   @SerializedName("total_pages")
   private final int totalPages;
   @SerializedName("total_results")
   private final int totalResults;

   public final int getPage() {
      return this.page;
   }

   public final List<Movie> getMovieList() {
      return this.movieList;
   }

   public final int getTotalPages() {
      return this.totalPages;
   }

   public final int getTotalResults() {
      return this.totalResults;
   }

   public MovieResponse(int page, List<Movie> movieList, int totalPages, int totalResults) {
      super();
      this.page = page;
      this.movieList = movieList;
      this.totalPages = totalPages;
      this.totalResults = totalResults;
   }
}