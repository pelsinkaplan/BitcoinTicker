package com.pelsinkaplan.bitcointicker.ui.utils

import android.view.View
import androidx.navigation.Navigation
import com.pelsinkaplan.bitcointicker.ui.fragment.AllCoinFragmentDirections
import com.pelsinkaplan.bitcointicker.ui.fragment.LoginFragmentDirections
import com.pelsinkaplan.bitcointicker.ui.fragment.RegisterFragmentDirections

/**
 * Created by Pelşin KAPLAN on 17.05.2022.
 */
class NavigationManager {
    fun actionLoginFragmentToAllCoinFragment(view: View, userId: String) {
        val action =
            LoginFragmentDirections.actionLoginFragmentToAllCoinFragment(userId)
        Navigation.findNavController(view).navigate(action)
    }

    fun actionLoginFragmentToRegisterFragment(view: View) {
        val action =
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(view).navigate(action)
    }

    fun actionRegisterFragmentToLoginFragment(view: View) {
        val action =
            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        Navigation.findNavController(view).navigate(action)
    }

    fun actionAllCoinFragmentToCoinDetailFragment(view: View, coinId: String, userId: String) {
        val action =
            AllCoinFragmentDirections.actionAllCoinFragmentToCoinDetailFragment(coinId, userId)
        Navigation.findNavController(view).navigate(action)
    }

    fun actionAllCoinFragmentToFavoritesFragment(view: View, userId: String) {
        val action =
            AllCoinFragmentDirections.actionAllCoinFragmentToFavoritesFragment(userId)
        Navigation.findNavController(view).navigate(action)
    }

    fun actionAllCoinFragmentToLoginFragment(view: View) {
        val action =
            AllCoinFragmentDirections.actionAllCoinFragmentToLoginFragment()
        Navigation.findNavController(view).navigate(action)
    }

    fun actionAllCoinFragmentToNotificationsFragment(view: View, userId: String) {
        val action =
            AllCoinFragmentDirections.actionAllCoinFragmentToNotificationsFragment(userId)
        Navigation.findNavController(view).navigate(action)
    }

}