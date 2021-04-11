package com.example.movies_application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movies_application.databinding.MovieDetailsBinding
import com.example.movies_application.network.models.MoviesItem
import com.example.movies_application.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_details.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetails : Fragment() {

    private var _binding: MovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

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

        val currentMovie = MovieDetailsArgs.fromBundle(requireArguments()).movieObj
        setMovieUi(currentMovie)

        binding.starMovieBtn.setOnClickListener {
            lifecycleScope.launch {
                if(!viewModel.checkIfMovieExists(currentMovie.id)) {
                    viewModel.insertMovie(currentMovie)
                    Toast.makeText(requireContext(), currentMovie.title+ " added to Watchlist", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.deleteMovie(currentMovie)
                }
            }
        }
    }

    private fun setMovieUi(movie: MoviesItem) {
        Glide.with(requireContext()).load(movie.posterurl).into(binding.moviePoster)
        binding.movieTitle.text = movie.title
        binding.releaseDate.text = movie.releaseDate
        binding.storyLine.text = movie.storyline
    }

}


