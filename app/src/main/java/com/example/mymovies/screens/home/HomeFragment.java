package com.example.mymovies.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.databinding.FragmentHomeBinding;
import com.example.mymovies.screens.home.adapter.HomeAdapter;
import com.paginate.Paginate;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private Paginate paginate;
    @Inject
    HomeAdapter homeAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerView();
        observeData();

        viewModel.getPopularMovies();

//        binding.btnClear.setOnClickListener(v -> {
//            viewModel.clearAllData();
//        });
    }

    private void observeData() {
        viewModel.getMovieList().observe(getViewLifecycleOwner(),
                movies -> {
                    managePagination();
                    homeAdapter.submitList(movies);
                });
    }

    private void setupRecyclerView() {
        binding.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.moviesRecyclerView.setHasFixedSize(true);
        binding.moviesRecyclerView.setAdapter(homeAdapter);
        enablePagination();
    }

    /**
     * Attaches pagination listener to recycler view.
     */
    public void enablePagination() {
        if (paginate != null) {
            paginate.unbind();
        }

        paginate = Paginate.with(binding.moviesRecyclerView, viewModel)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(false)
                .build();
    }

    private void managePagination() {
        if (viewModel.hasLoadedAllItems()) {
            disablePagination();
        } else {
            enablePagination();
        }
    }

    /**
     * Detaches pagination listener from recycler view.
     */
    public void disablePagination() {
        if (paginate != null) {
            paginate.unbind();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

}