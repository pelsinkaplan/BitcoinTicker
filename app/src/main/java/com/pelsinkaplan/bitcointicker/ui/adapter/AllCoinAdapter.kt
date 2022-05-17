package com.pelsinkaplan.bitcointicker.ui.allcoin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.pelsinkaplan.bitcointicker.data.CoinListItem
import com.pelsinkaplan.bitcointicker.databinding.ItemCoinBinding
import com.pelsinkaplan.bitcointicker.ui.fragment.AllCoinFragmentDirections
import com.pelsinkaplan.bitcointicker.ui.utils.NavigationManager

/**
 * Created by Pelşin KAPLAN on 13.05.2022.
 */
class AllCoinAdapter : RecyclerView.Adapter<CoinListViewHolder>() {

    private var coinList = mutableListOf<CoinListItem>()

    lateinit var userId: String

    fun setCoinsList(coinList: List<CoinListItem>, userId: String) {
        this.coinList = coinList.toMutableList()
        this.userId = userId
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemCoinBinding.inflate(inflater, parent, false)

        return CoinListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        val coin = coinList[position]

        holder.binding.coinNameTextview.text = coin.name
        holder.binding.coinSymbolTextview.text = coin.symbol

        holder.binding.itemCardview.setOnClickListener {
            NavigationManager().actionAllCoinFragmentToCoinDetailFragment(
                holder.itemView,
                coin.id,
                userId
            )
        }
    }

    override fun getItemCount(): Int {
        return coinList.size
    }
}

class CoinListViewHolder(val binding: ItemCoinBinding) :
    RecyclerView.ViewHolder(binding.root) {

}