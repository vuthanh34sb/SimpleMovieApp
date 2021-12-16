package com.example.simplemovieapp.model;

import com.google.gson.annotations.SerializedName;

public final class Comment {
   @SerializedName("author")
   private final String author;
   @SerializedName("content")
   private final String content;
   @SerializedName("id")
   private final String id;
   @SerializedName("url")
   private final String url;

   public final String getAuthor() {
      return this.author;
   }

   public final String getContent() {
      return this.content;
   }

   public final String getId() {
      return this.id;
   }

   public final String getUrl() {
      return this.url;
   }

   public Comment(String author, String content, String id, String url) {
      this.author = author;
      this.content = content;
      this.id = id;
      this.url = url;
   }
}