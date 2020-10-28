package com.example.mymovies.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mymovies.net.model.Movie;

import java.util.List;

@Dao
public interface MovieListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> movies);

    @Query("DELETE FROM movie_table")
    void clearAll();

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getMovieList();

    /**
     * Insert a movie in the database. If the movie already exists, replace it.
     *
     * @param movie the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

}