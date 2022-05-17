package com.pelsinkaplan.bitcointicker.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pelsinkaplan.bitcointicker.R
import com.pelsinkaplan.bitcointicker.data.CoinDetail
import com.pelsinkaplan.bitcointicker.databinding.FavoritesFragmentBinding
import com.pelsinkaplan.bitcointicker.ui.Adapter
import com.pelsinkaplan.bitcointicker.ui.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FavoritesFragmentBinding
    private lateinit var viewModel: FavoritesViewModel
    private val adapter = Adapter()

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
        var list: List<CoinDetail>
        viewModel.getFavoritesCoinIdList(userId)
        viewModel.favList.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.getFavoritesCoinList(it)
                viewModel.favCoinList.observe(viewLifecycleOwner) { list ->
                    binding.recyclerview.adapter = adapter
                    adapter.setCoinsList(list, userId)
                }

            }
        }
    }
}