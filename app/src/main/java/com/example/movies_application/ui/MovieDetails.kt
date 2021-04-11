package com.example.movies_application.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
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
import com.example.movies_application.R
import com.example.movies_application.databinding.MovieDetailsBinding
import com.example.movies_application.network.models.MoviesItem
import com.example.movies_application.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private lateinit var auth: FirebaseAuth

    private lateinit var currentMovie: MoviesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        currentMovie = MovieDetailsArgs.fromBundle(requireArguments()).movieObj
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        lifecycleScope.launch {
            if (viewModel.checkIfMovieExists(currentMovie.id, auth.currentUser!!.uid)) {
                toogleStarOn()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMovieUi(currentMovie)

        binding.starMovieBtn.setOnClickListener {
            lifecycleScope.launch {
                if(!viewModel.checkIfMovieExists(currentMovie.id, auth.currentUser!!.uid)) {
                    currentMovie.userId = auth.currentUser?.uid
                    viewModel.insertMovie(currentMovie)
                    toogleStarOn()
                    Toast.makeText(requireContext(), currentMovie.title+ " added to Watchlist", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.deleteMovie(currentMovie)
                    binding.starMovieBtn.setImageResource(R.drawable.empty_star)
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

    private fun toogleStarOn() {
        binding.starMovieBtn.setImageResource(R.drawable.filled_star)
        binding.starMovieBtn.setColorFilter(Color.argb(	0, 255, 193, 7))
    }

}


