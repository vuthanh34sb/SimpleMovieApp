package com.example.simplemovieapp.model;

public final class Trailer {
   private final String id;
   private final String key;
   private final String name;
   private final String type;
   
   public final String getId() {
      return this.id;
   }

   public final String getKey() {
      return this.key;
   }

   public final String getName() {
      return this.name;
   }

   public final String getType() {
      return this.type;
   }

   public Trailer(String id, String key, String name, String type) {
      super();
      this.id = id;
      this.key = key;
      this.name = name;
      this.type = type;
   }
}