package com.example.simplemovieapp.api;

import com.example.simplemovieapp.model.CommentResponse;
import com.example.simplemovieapp.model.MovieDetails;
import com.example.simplemovieapp.model.MovieResponse;
import com.example.simplemovieapp.model.MovieTrailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBInterface {
//    @GET("movie/popular")
//    MovieResponse getPopularMovie(@Query("page")int page);
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovie();


    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id")int id);

    @GET("movie/{movie_id}/videos")
    Call<MovieTrailer> getMovieTrailer(@Path("movie_id") int id);

    @GET("movie/{movie_id}/reviews")
    Call<CommentResponse> getComment(@Path("movie_id") int id, @Query("page") int page);

    @GET("search/movie")
    Call<MovieResponse> search(@Query("query") String query, @Query("page") int page);
}
