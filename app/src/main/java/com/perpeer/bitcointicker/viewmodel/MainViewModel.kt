package com.perpeer.bitcointicker.viewmodel

import androidx.lifecycle.ViewModel
import com.perpeer.bitcointicker.data.cache.firestore.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 8.01.2025.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseRepository: FirebaseAuthRepository
) : ViewModel() {

    fun isUserLoggedIn(): Boolean {
        return firebaseRepository.getCurrentUser() != null
    }
}