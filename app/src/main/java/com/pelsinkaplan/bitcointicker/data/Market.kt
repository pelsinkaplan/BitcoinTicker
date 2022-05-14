package com.pelsinkaplan.bitcointicker.data

data class Market(
    val has_trading_incentive: Boolean,
    val identifier: String,
    val name: String
)