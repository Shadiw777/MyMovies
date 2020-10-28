package com.example.mymovies.repository;

import androidx.lifecycle.LiveData;

import com.example.mymovies.net.MoviesApiService;
import com.example.mymovies.net.model.Movie;
import com.example.mymovies.net.model.MovieResponse;
import com.example.mymovies.storage.MovieListDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;

public class Repository {

    private MoviesApiService moviesApiService;
    private MovieListDao movieListDao;

    @Inject
    public Repository(MoviesApiService moviesApiService, MovieListDao movieListDao) {
        this.moviesApiService = moviesApiService;
        this.movieListDao = movieListDao;
    }

    public Single<MovieResponse> getMovie(int page) {
        return moviesApiService.getMovie(page);
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieListDao.getMovieList();
    }

    public void insertMovieList(List<Movie> movies) {
        movieListDao.insertAll(movies);
    }

    public void clearAllData() {
        movieListDao.clearAll();
    }

}
