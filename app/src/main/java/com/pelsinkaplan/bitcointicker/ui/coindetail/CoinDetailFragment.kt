package com.pelsinkaplan.bitcointicker.ui.coindetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.pelsinkaplan.bitcointicker.R
import com.pelsinkaplan.bitcointicker.data.CoinDetail
import com.pelsinkaplan.bitcointicker.databinding.FragmentCoinDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CoinDetailFragment : Fragment() {
    private lateinit var binding: FragmentCoinDetailBinding
    private lateinit var viewModel: CoinDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CoinDetailViewModel by activityViewModels()
        this.viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false);
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
            hashingAlgorithmTextview.text = coinDetail.hashing_algorithm
            Glide.with(requireActivity())
                .load(coinDetail.image.large)
                .into(imageView)
        }
    }

}