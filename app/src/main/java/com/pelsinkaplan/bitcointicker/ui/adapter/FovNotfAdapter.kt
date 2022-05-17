package com.pelsinkaplan.bitcointicker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pelsinkaplan.bitcointicker.data.CoinDetail
import com.pelsinkaplan.bitcointicker.databinding.ItemCoinBinding
import com.pelsinkaplan.bitcointicker.ui.utils.NavigationManager

/**
 * Created by Pelşin KAPLAN on 17.05.2022.
 */
class Adapter : RecyclerView.Adapter<CoinListViewHolder>() {

    private var coinList = mutableListOf<CoinDetail>()

    lateinit var userId: String

    fun setCoinsList(coinList: List<CoinDetail>, userId: String) {
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