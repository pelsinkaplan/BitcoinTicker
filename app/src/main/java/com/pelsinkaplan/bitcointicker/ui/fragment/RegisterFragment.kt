package com.pelsinkaplan.bitcointicker.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.bitcointicker.R
import com.pelsinkaplan.bitcointicker.databinding.RegisterFragmentBinding
import com.pelsinkaplan.bitcointicker.ui.utils.Validation
import com.pelsinkaplan.bitcointicker.ui.viewmodel.RegisterViewModel

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
                Validation.registerValidation(
                    emailEdittext.text.toString(),
                    passwordEdittext.text.toString(),
                    passwordAgainEdittext.text.toString(),
                    view
                )

            }
        }
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }
}