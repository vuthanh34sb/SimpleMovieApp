package com.example.simplemovieapp.model;

import com.example.simplemovieapp.model.Comment;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class CommentResponse {
   @SerializedName("page")
   private final int page;
   @SerializedName("results")
   private final List<Comment> commentList;
   @SerializedName("total_pages")
   private final int totalPages;
   @SerializedName("total_results")
   private final int totalResults;

   public final int getPage() {
      return this.page;
   }

   public final List<Comment> getCommentList() {
      return this.commentList;
   }

   public final int getTotalPages() {
      return this.totalPages;
   }

   public final int getTotalResults() {
      return this.totalResults;
   }

   public CommentResponse(int page, List<Comment> commentList, int totalPages, int totalResults) {
      super();
      this.page = page;
      this.commentList = commentList;
      this.totalPages = totalPages;
      this.totalResults = totalResults;
   }
}