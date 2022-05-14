package com.pelsinkaplan.bitcointicker.ui.register

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
import com.pelsinkaplan.bitcointicker.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: RegisterFragmentBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: RegisterViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            registerButton.setOnClickListener {
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
                    TextUtils.isEmpty(
                        binding.passwordAgainEdittext.text.toString().trim { it <= ' ' }) -> {
                        Toast.makeText(
                            requireContext(),
                            "Please enter repassword!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    else -> {
                        val email = emailEdittext.text.toString().trim() { it <= ' ' }
                        val password = passwordEdittext.text.toString().trim() { it <= ' ' }
                        val repassword = passwordAgainEdittext.text.toString().trim() { it <= ' ' }

                        if (password != repassword)
                            Toast.makeText(
                                requireContext(),
                                "Passwords do not match!",
                                Toast.LENGTH_SHORT
                            ).show()
                        else {
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Registration Successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val action =
                                            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
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
}