package com.pelsinkaplan.bitcointicker.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.bitcointicker.data.CoinListItem
import com.pelsinkaplan.bitcointicker.databinding.AllCoinFragmentBinding
import com.pelsinkaplan.bitcointicker.ui.allcoin.AllCoinAdapter
import com.pelsinkaplan.bitcointicker.ui.utils.NavigationManager
import com.pelsinkaplan.bitcointicker.ui.viewmodel.AllCoinViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AllCoinFragment : Fragment() {

    private lateinit var binding: AllCoinFragmentBinding
    private lateinit var viewModel: AllCoinViewModel
    private val adapter = AllCoinAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AllCoinViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllCoinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coinService: AllCoinFragmentArgs by navArgs()
        val userId = coinService.userId
        binding.favoritesButton.setOnClickListener {
            NavigationManager().actionAllCoinFragmentToFavoritesFragment(view, userId)
        }
        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            NavigationManager().actionAllCoinFragmentToLoginFragment(view)

        }
        binding.notificationsButton.setOnClickListener {
            NavigationManager().actionAllCoinFragmentToNotificationsFragment(view, userId)
        }
        apiRequest(userId)

//        binding.refreshSwipeLayout.setOnRefreshListener {
//            apiRequest(userId)
//            binding.refreshSwipeLayout.setRefreshing(false)
//        }
    }


    private fun apiRequest(userId: String) {
        var list = listOf<CoinListItem>()

        CoroutineScope(Dispatchers.Main).launch {
            val response = viewModel.service()
            if (response != null)
                list = viewModel.service()!!
            binding.recyclerview.adapter = adapter
            adapter.setCoinsList(list, userId)
        }
    }
}