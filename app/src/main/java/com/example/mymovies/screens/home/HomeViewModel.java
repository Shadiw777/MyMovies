package com.example.mymovies.screens.home;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.net.model.Movie;
import com.example.mymovies.repository.Repository;
import com.paginate.Paginate;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

class HomeViewModel extends ViewModel implements Paginate.Callbacks {

    private static final String TAG = "HomeViewModel";
    private Repository repository;
    public final CompositeDisposable disposable = new CompositeDisposable();
    private int page = 1;
    private int totalPages = 0;
    private boolean isLoadingMore = false;

    @ViewModelInject
    public HomeViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Movie>> getMovieList() {
        return repository.getMovieList();
    }

    public void getPopularMovies() {
        isLoadingMore = true;

        disposable.add(repository.getMovie(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            isLoadingMore = false;
                            page++;
                            totalPages = result.getTotalPages();
                            repository.insertMovieList(result.getMovies());
                            Log.d(TAG, "getPopularMovies() called " + page);
                        },
                        error -> Log.e(TAG, "Error!!! " + error.getMessage())));
    }

    public void clearAllData() {
        repository.clearAllData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.clear();
    }

    @Override
    public void onLoadMore() {
        getPopularMovies();
    }

    @Override
    public boolean isLoading() {
        return isLoadingMore;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page > totalPages;
    }

}