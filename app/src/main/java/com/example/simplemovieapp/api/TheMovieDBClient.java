package com.example.simplemovieapp.api;

import com.example.simplemovieapp.api.TheMovieDBInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class TheMovieDBClient {
    public static final String API_KEY = "6e63c2317fbe963d76c3bdc2b785f6d1";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";

    public static final int FIRST_PAGE = 1;
    public static final int POST_PER_PAGE = 20;

    public static final TheMovieDBInterface getClient() {
        Interceptor requestInterceptor = chain -> {
            // Interceptor take only one argument which is a lambda function so parenthesis can be omitted

            HttpUrl url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build();

            Request request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build();

            return chain.proceed(request);   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
        };
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDBInterface.class);
    }
}
