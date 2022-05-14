package com.pelsinkaplan.bitcointicker.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.pelsinkaplan.bitcointicker.data.CoinListItem
import com.pelsinkaplan.bitcointicker.databinding.ItemCoinBinding
import com.pelsinkaplan.bitcointicker.service.network.RetrofitAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pelşin KAPLAN on 13.05.2022.
 */
class FavoritesAdapter : RecyclerView.Adapter<FavoritesListViewHolder>() {

    private var coinList = mutableListOf<String>()
    lateinit var retrofitAPI: RetrofitAPI
    lateinit var userId: String

    fun setCoinsList(coinList: List<String>, userId: String, retrofitAPI: RetrofitAPI) {
        this.coinList = coinList.toMutableList()
        this.userId = userId
        this.retrofitAPI = retrofitAPI
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemCoinBinding.inflate(inflater, parent, false)

        return FavoritesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesListViewHolder, position: Int) {
        val coin = coinList[position]

        CoroutineScope(Dispatchers.Main).launch {
            val dataResponse = retrofitAPI.getCoinsById(coin)
            if (dataResponse.isSuccessful) {
                holder.binding.coinNameTextview.text = dataResponse.body()!!.name
                holder.binding.coinSymbolTextview.text = dataResponse.body()!!.symbol

                holder.binding.itemCardview.setOnClickListener {
                    val action =
                        FavoritesFragmentDirections.actionFavoritesFragmentToCoinDetailFragment(
                            dataResponse.body()!!.id, userId
                        )
                    Navigation.findNavController(holder.binding.itemCardview).navigate(action)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return coinList.size
    }
}

class FavoritesListViewHolder(val binding: ItemCoinBinding) :
    RecyclerView.ViewHolder(binding.root) {

}