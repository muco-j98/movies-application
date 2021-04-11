package com.example.movies_application.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var moviesAdapter: MovieAdapter

    private var _binding: MoviesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MoviesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            logoutUser()
        }
        return super.onOptionsItemSelected(item)
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

        binding.moviesButton.setOnClickListener {
            if (Firebase.auth.currentUser != null) {
                Toast.makeText(requireContext(), "Succ", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(requireContext(), "and i ooppp", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logoutUser() {
        auth.signOut()
        view?.findNavController()?.navigate(MoviesFragmentDirections.actionMoviesFragmentToLoginFragment())
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