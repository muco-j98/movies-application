package com.example.movies_application.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies_application.R
import com.example.movies_application.adapters.MovieAdapter
import com.example.movies_application.databinding.MoviesFragmentBinding
import com.example.movies_application.network.models.MoviesItem
import com.example.movies_application.network.util.Resource
import com.example.movies_application.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var moviesAdapter: MovieAdapter

    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MoviesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getMovies()

        viewModel.moviesResponse.observe(viewLifecycleOwner, Observer { moviesResponse ->
            when (moviesResponse) {
                is Resource.Success -> {
                    moviesResponse.data.let { response ->
                        response?.let {
                            if (it.isEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    "No movies found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else
                                Log.e("info", "Success")
                                moviesAdapter.submitList(it)
                        }
                    }
                }
                is Resource.Error -> {
                    moviesResponse.message?.let {
                        Log.e("error", it)
                    }
                }
                is Resource.Loading -> {
                    Log.e("info", "Loading")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        moviesAdapter = MovieAdapter() {
            manageOnClickEvent(it)
        }
        binding.rvMovies.adapter = moviesAdapter
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun manageOnClickEvent(movie: MoviesItem) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetails(movie)
        view?.findNavController()?.navigate(action)
    }
}