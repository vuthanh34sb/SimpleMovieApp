package com.example.simplemovieapp.model;

import com.google.gson.annotations.SerializedName;

public final class Movie {
   private final int id;
   @SerializedName("poster_path")
   private final String posterPath;
   @SerializedName("release_date")
   private final String releaseDate;
   private final String title;

   public final int getId() {
      return this.id;
   }

   public final String getPosterPath() {
      return this.posterPath;
   }

   public final String getReleaseDate() {
      return this.releaseDate;
   }

   public final String getTitle() {
      return this.title;
   }

   public Movie(int id, String posterPath, String releaseDate, String title) {
      this.id = id;
      this.posterPath = posterPath;
      this.releaseDate = releaseDate;
      this.title = title;
   }
}