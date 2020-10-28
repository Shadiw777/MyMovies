package com.example.mymovies.screens.home.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mymovies.R;
import com.example.mymovies.databinding.MoviesItemBinding;
import com.example.mymovies.net.model.Movie;

class MoviesVH extends RecyclerView.ViewHolder {

    private MoviesItemBinding binding;

    public MoviesVH(MoviesItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setMovieData(Movie movie) {
        binding.movieName.setText(movie.getOriginalTitle());

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(binding.movieImage.getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(binding.movieImage.getContext())
                .load("https://image.tmdb.org/t/p/original" + movie.getBackdropPath())
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.movieImage);
    }
}
