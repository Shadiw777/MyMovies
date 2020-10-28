package com.example.mymovies.storage;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {

    private static final String DATABASE_NAME = "popular_movie_list_db";

    @Provides
    @Singleton
    MovieDatabase provideMovieDatabase(Application application) {
        return Room.databaseBuilder(application, MovieDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    MovieListDao providePopularMoviesListDao(MovieDatabase movieDatabase) {
        return movieDatabase.moviesListDao();
    }

}
