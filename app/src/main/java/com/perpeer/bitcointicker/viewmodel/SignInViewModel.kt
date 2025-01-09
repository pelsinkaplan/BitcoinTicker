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
class SignInViewModel @Inject constructor(private val repository: FirebaseAuthRepository) :
    ViewModel() {
    var signInSuccess: Boolean = false
    var errorMessage: String? = null

    fun signIn(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.signIn(email, password)
            signInSuccess = result != null
            errorMessage = if (signInSuccess) null else "Sign in failed. Check your credentials."
            onResult(signInSuccess)
        }
    }
}
