package com.example.mymovies.storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mymovies.net.model.Movie;

@Database(entities = Movie.class, version = 1 ,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieListDao moviesListDao();

}
