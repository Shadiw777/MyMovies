package com.example.mymovies.net;

import com.example.mymovies.net.model.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("movie/popular")
    Single<MovieResponse> getMovie(@Query("page") int page);

}
