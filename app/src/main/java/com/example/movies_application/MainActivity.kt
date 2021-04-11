package com.example.movies_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movies_fragment.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        bottom_nav.setupWithNavController(hostFragment.findNavController())

        hostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.moviesFragment, R.id.movieDetails, R.id.watchListFragment ->
                        bottom_nav.visibility = View.VISIBLE
                    else -> bottom_nav.visibility = View.GONE
                }
            }
    }
}