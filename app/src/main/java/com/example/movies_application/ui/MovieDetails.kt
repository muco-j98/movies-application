package com.example.movies_application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.movies_application.databinding.MovieDetailsBinding
import com.example.movies_application.network.models.MoviesItem

class MovieDetails: Fragment() {

    private var _binding: MovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMovieUi(MovieDetailsArgs.fromBundle(requireArguments()).movieObj)
    }

    private fun setMovieUi(movie: MoviesItem) {
        Glide.with(requireContext()).load(movie.posterurl).into(binding.moviePoster)
        binding.movieTitle.text = movie.title
        binding.releaseDate.text = movie.releaseDate
        binding.storyLine.text = movie.storyline
    }
}