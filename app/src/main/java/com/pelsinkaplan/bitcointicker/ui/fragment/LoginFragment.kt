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
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.bitcointicker.databinding.LoginFragmentBinding
import com.pelsinkaplan.bitcointicker.ui.utils.NavigationManager
import com.pelsinkaplan.bitcointicker.ui.utils.Validation
import com.pelsinkaplan.bitcointicker.ui.viewmodel.LoginViewModel

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
                NavigationManager().actionLoginFragmentToRegisterFragment(view)
            }
            loginButton.setOnClickListener {
                Validation.loginValidation(
                    emailEdittext.text.toString(),
                    passwordEdittext.text.toString(),
                    view
                )
            }
        }
    }
}