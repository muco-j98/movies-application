package com.example.movies_application.ui

import android.app.Activity
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.movies_application.R
import com.example.movies_application.databinding.LoginFragmentBinding
import com.example.movies_application.databinding.MovieDetailsBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment: Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val authStateListener =  FirebaseAuth.AuthStateListener { firebaseAuth ->
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            binding.progressCircular.visibility = View.INVISIBLE
            view?.findNavController()
                ?.navigate(LoginFragmentDirections.actionLoginFragmentToMoviesFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.root.visibility = View.INVISIBLE
        binding.progressCircular.visibility = View.VISIBLE
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(this.authStateListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            validateEntries()?.let { data ->
                loginUser(data)
            }
        }

        binding.redirectSignUpText.setOnClickListener {
            navigateToRegistrationScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.removeAuthStateListener(this.authStateListener)
    }

    private fun loginUser(data: Pair<String, String>) {
        val email = data.first
        val password = data.second

        binding.progressCircular.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToMoviesScreen()
                } else {
                    binding.progressCircular.visibility = View.INVISIBLE
                    Log.w("Firebase", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun navigateToMoviesScreen() {
        view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToMoviesFragment())
    }

    private fun navigateToRegistrationScreen() {
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun validateEntries(): Pair<String, String>? {
        val email = binding.emailField.editText?.text.toString().trim()
        val password = binding.passwordField.editText?.text.toString().trim()

        if (email.isEmpty()) {
            binding.emailField.error = "Email is required"
            binding.emailField.requestFocus()
            return null
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailField.error = "Please provide a valid email"
            binding.emailField.requestFocus()
            return null
        }
        if (password.isEmpty()) {
            binding.passwordField.error = "Password is required"
            binding.passwordField.requestFocus()
            return null
        }
        if (password.length < 6) {
            binding.passwordField.error = "Min password length should be 6 characters!"
            binding.passwordField.requestFocus()
            return null
        }

        return Pair(email, password)
    }
}