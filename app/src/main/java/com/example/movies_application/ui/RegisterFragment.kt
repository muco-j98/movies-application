package com.example.movies_application.ui

import android.app.Activity
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
import com.example.movies_application.databinding.RegisterFragmentBinding
import com.example.movies_application.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterFragment: Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener {
            validateEntries()?.let {
                registerUser(it)
            }
        }
        binding.redirectSignInText.setOnClickListener {
            navigateToLoginScreen()
        }
    }

    private fun navigateToLoginScreen() {
        view?.findNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun validateEntries(): Triple<String, String, String>? {
        val emailField = binding.emailField
        val usernameField = binding.usernameField
        val passwordField = binding.passwordField

        val email = emailField.editText?.text.toString().trim()
        val username = usernameField.editText?.text.toString().trim()
        val password = passwordField.editText?.text.toString().trim()

        if (email.isEmpty()) {
            emailField.error = "Email is required"
            emailField.requestFocus()
            return null
        }
        if (username.isEmpty()) {
            usernameField.error = "Username is required"
            usernameField.requestFocus()
            return null
        }
        if (password.isEmpty()) {
            passwordField.error = "Password is required"
            passwordField.requestFocus()
            return null
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.error = "Please provide a valid email"
            emailField.requestFocus()
            return null
        }
        if (password.length < 6) {
            passwordField.error = "Min password length should be 6 characters!"
            passwordField.requestFocus()
            return null
        }

        return Triple(email, username, password)
    }

    private fun registerUser(data: Triple<String, String, String>) {
        val email = data.first
        val username = data.second
        val password = data.third

        binding.progressCircular.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "createUserWithEmail:success")
                    val user = User(email, username)
                    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(auth.currentUser.uid)
                        .setValue(user).addOnCompleteListener(OnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                binding.progressCircular.visibility = View.INVISIBLE
                                navigateToMoviesScreen(user)
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.w("Firebase", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                                navigateToMoviesScreen(null)
                        }
                        })
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Firebase", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    navigateToMoviesScreen(null)
                }
            }
    }

    private fun navigateToMoviesScreen(user: User?) {
        user?.let {
            view?.findNavController()?.navigate(RegisterFragmentDirections.actionRegisterFragmentToMoviesFragment())
        }
    }
}

