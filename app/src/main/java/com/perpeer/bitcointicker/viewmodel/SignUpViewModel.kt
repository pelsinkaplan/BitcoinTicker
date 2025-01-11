package com.perpeer.bitcointicker.viewmodel

/**
 * Created by Pelşin KAPLAN on 7.01.2025.
 */
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpeer.bitcointicker.data.cache.firestore.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: FirebaseAuthRepository) :
    ViewModel() {
    var signUpSuccess: Boolean = false
    var errorMessage: String? = null

    fun signUp(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.signUp(email, password)
            signUpSuccess = result != null
            errorMessage = if (signUpSuccess) null else "Sign up failed. Try again."
            onResult(signUpSuccess)
        }
    }
}
