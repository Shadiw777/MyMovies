package com.example.mymovies.screens.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.databinding.ItemLoadingBinding;
import com.example.mymovies.databinding.MoviesItemBinding;
import com.example.mymovies.net.model.Movie;

import javax.inject.Inject;


public class HomeAdapter extends ListAdapter<Movie, RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    @Inject
    public HomeAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
//            case VIEW_TYPE_LOADING:
//                ItemLoadingBinding itemLoadingBinding = ItemLoadingBinding
//                        .inflate(layoutInflater, parent, false);
//
//                return new LoadingVH(itemLoadingBinding);
            case VIEW_TYPE_NORMAL:
            default:
                MoviesItemBinding moviesItemBinding = MoviesItemBinding
                        .inflate(layoutInflater, parent, false);

                return new MoviesVH(moviesItemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = getItem(position);

        if (holder instanceof MoviesVH) {
            ((MoviesVH) holder).setMovieData(movie);
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (isLoaderVisible) {
//            return position == getItemCount() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
//        } else {
//            return VIEW_TYPE_NORMAL;
//        }
//    }

//    public void setLoaderVisible(boolean isLoaderVisible) {
//        this.isLoaderVisible = isLoaderVisible;
//    }

}
