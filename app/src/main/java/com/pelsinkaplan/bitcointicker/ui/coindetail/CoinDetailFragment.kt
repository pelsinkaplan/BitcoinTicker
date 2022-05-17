package com.pelsinkaplan.bitcointicker.ui.coindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.work.*
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.pelsinkaplan.bitcointicker.R
import com.pelsinkaplan.bitcointicker.data.CoinDetail
import com.pelsinkaplan.bitcointicker.databinding.CoinDetailFragmentBinding
import com.pelsinkaplan.bitcointicker.service.CoinWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {
    private lateinit var binding: CoinDetailFragmentBinding
    private lateinit var viewModel: CoinDetailViewModel
    private lateinit var periodicWorkRequest: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CoinDetailViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CoinDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coinService: CoinDetailFragmentArgs by navArgs()
        val coinId = coinService.id
        val userId = coinService.userId
        var favList: List<String> = listOf()
        val database =
            FirebaseDatabase.getInstance().getReference("favorites").child(userId)

        database.get().addOnSuccessListener {
            if (it.exists()) {
                favList = it.value as List<String>
                if (favList.contains(coinId))
                    binding.addFavoritesButton.setBackgroundResource(R.drawable.ic_star_filled)
            }
        }

        binding.addFavoritesButton.setOnClickListener {
            it.setBackgroundResource(R.drawable.ic_star_filled)
            database.get().addOnSuccessListener {
                database.setValue(favList.plus(coinId))
            }
        }

        binding.okButton.setOnClickListener {
            val refreshInterval = binding.refreshIntervalEdittext.text
            if (refreshInterval != null) {
                val workCondition = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val coin = Data.Builder()
                coin.putString("coinId", coinId)
                val user = Data.Builder()
                user.putString("userId", userId)

                periodicWorkRequest =
                    PeriodicWorkRequestBuilder<CoinWorker>(
                        refreshInterval.toString().toInt().toLong(), TimeUnit.MINUTES
                    )
                        .setConstraints(workCondition)
                        .setInputData(coin.build())
                        .setInputData(user.build())
                        .build()

                WorkManager.getInstance(requireContext())
                    .enqueue(periodicWorkRequest)
            }
        }
        request(coinId)
    }

    fun request(coinId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = viewModel.service(coinId)
            if (response != null)
                editUI(response)
        }
    }

    private fun editUI(coinDetail: CoinDetail) {
        binding.apply {
            coinSymbolTextview.text = coinDetail.symbol
            coinNameTextview.text = coinDetail.name
            descriptionTextview.text = coinDetail.description.en
            currentPriceTextview.text = coinDetail.market_data.current_price.toString()
            hashingAlgorithmTextview.text = coinDetail.hashing_algorithm
            Glide.with(requireActivity())
                .load(coinDetail.image.large)
                .into(imageView)
        }
    }

}