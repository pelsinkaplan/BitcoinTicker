package com.perpeer.bitcointicker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpeer.bitcointicker.data.cache.firestore.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 8.01.2025.
 */
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repository: FirebaseAuthRepository) :
    ViewModel() {

    fun sendPasswordResetEmail(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.sendPasswordResetEmail(email)
            onResult(success)
        }
    }
}