package com.pelsinkaplan.bitcointicker.ui.favorites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.bitcointicker.R
import com.pelsinkaplan.bitcointicker.data.CoinListItem
import com.pelsinkaplan.bitcointicker.databinding.FavoritesFragmentBinding
import com.pelsinkaplan.bitcointicker.databinding.FragmentAllCoinBinding
import com.pelsinkaplan.bitcointicker.service.network.RetrofitAPI
import com.pelsinkaplan.bitcointicker.ui.allcoin.AllCoinAdapter
import com.pelsinkaplan.bitcointicker.ui.allcoin.AllCoinFragmentArgs
import com.pelsinkaplan.bitcointicker.ui.allcoin.AllCoinFragmentDirections
import com.pelsinkaplan.bitcointicker.ui.allcoin.AllCoinViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private lateinit var binding: FavoritesFragmentBinding
    private lateinit var viewModel: FavoritesViewModel
    val adapter = FavoritesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavoritesViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritesFragmentBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coinService: FavoritesFragmentArgs by navArgs()
        val userId = coinService.userId
        var list = listOf<String>()
        var retrofitAPI: RetrofitAPI
        CoroutineScope(Dispatchers.Main).launch {
            retrofitAPI = viewModel.service(userId)
            viewModel.favList.observe(viewLifecycleOwner) {
                list = it
                binding.recyclerview.adapter = adapter
                adapter.setCoinsList(list, userId, retrofitAPI)
            }

        }
    }

}