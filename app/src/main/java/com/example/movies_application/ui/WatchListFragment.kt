package com.example.movies_application.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies_application.R
import com.example.movies_application.adapters.MovieAdapter
import com.example.movies_application.adapters.WatchlistAdapter
import com.example.movies_application.databinding.WatchlistFragmentBinding
import com.example.movies_application.network.models.MoviesItem
import com.example.movies_application.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var watchListAdapter: WatchlistAdapter

    private var _binding: WatchlistFragmentBinding? = null
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
        _binding = WatchlistFragmentBinding.inflate(inflater, container, false)
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

        viewModel.getWatchListMovies(auth.currentUser!!.uid).observe(viewLifecycleOwner, Observer {
            watchListAdapter.submitList(it)
        })
    }

    private fun logoutUser() {
        auth.signOut()
        view?.findNavController()?.navigate(WatchListFragmentDirections.actionWatchListFragmentToLoginFragment())
    }

    private fun setupRecyclerView() {
        watchListAdapter = WatchlistAdapter() {
            manageOnClickEvent(it)
        }
        binding.rvWatchlistMovies.adapter = watchListAdapter
        binding.rvWatchlistMovies.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun manageOnClickEvent(movie: MoviesItem) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetails(movie)
        view?.findNavController()?.navigate(action)
    }
}