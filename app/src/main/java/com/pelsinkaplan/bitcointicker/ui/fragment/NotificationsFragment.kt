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
import com.pelsinkaplan.bitcointicker.databinding.NotificationsFragmentBinding
import com.pelsinkaplan.bitcointicker.ui.Adapter
import com.pelsinkaplan.bitcointicker.ui.viewmodel.NotificationsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {

    private lateinit var viewModel: NotificationsViewModel
    private lateinit var binding: NotificationsFragmentBinding
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: NotificationsViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NotificationsFragmentBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coinService: NotificationsFragmentArgs by navArgs()
        val userId = coinService.userId
        var list: List<CoinDetail>
        viewModel.getNotificationCoinIdList(userId)
        viewModel.favList.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.getNotificationCoinList(it)
                viewModel.favCoinList.observe(viewLifecycleOwner) { list ->
                    binding.recyclerview.adapter = adapter
                    adapter.setCoinsList(list, userId)
                }

            }
        }
    }
}