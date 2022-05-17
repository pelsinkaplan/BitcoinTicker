package com.pelsinkaplan.bitcointicker.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.bitcointicker.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: LoginViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            registerButton.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                Navigation.findNavController(view).navigate(action)
            }
            loginButton.setOnClickListener {
                when {
                    TextUtils.isEmpty(binding.emailEdittext.text.toString().trim { it <= ' ' }) -> {
                        Toast.makeText(requireContext(), "Please enter email!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    TextUtils.isEmpty(
                        binding.passwordEdittext.text.toString().trim { it <= ' ' }) -> {
                        Toast.makeText(
                            requireContext(),
                            "Please enter password!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    else -> {
                        val email = emailEdittext.text.toString().trim() { it <= ' ' }
                        val password = passwordEdittext.text.toString().trim() { it <= ' ' }

                        FirebaseAuth.getInstance()
                            .signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val firebaseUser = task.result.user
                                    Toast.makeText(
                                        requireContext(),
                                        "Login Successful!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val action =
                                        LoginFragmentDirections.actionLoginFragmentToAllCoinFragment(
                                            firebaseUser!!.uid
                                        )
                                    Navigation.findNavController(view).navigate(action)
                                } else
                                    Toast.makeText(
                                        requireContext(),
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }
                    }
                }
            }
        }
    }
}