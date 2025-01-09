package com.perpeer.bitcointicker.ui.components

import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.perpeer.bitcointicker.viewmodel.CoinDetailViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Pelşin KAPLAN on 9.01.2025.
 */

@Composable
fun CandleStickChartComposable(
    candleStickData: List<CoinDetailViewModel.CandleStick>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            CandleStickChart(context).apply {
                setDrawGridBackground(false)
                setScaleEnabled(true)
                setPinchZoom(true)
                description.isEnabled = false
                legend.isEnabled = false
                xAxis.apply {
                    position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    textColor = Color.Gray.toArgb()
                    textSize = 12f
                }
                axisLeft.apply {
                    setDrawGridLines(false)
                    textColor = Color.Gray.toArgb()
                    textSize = 12f
                }
                axisRight.isEnabled = false
            }
        },
        modifier = modifier,
        update = { chart ->
            // CandleEntry listesi oluştur
            val entries = candleStickData.mapIndexed { index, data ->
                CandleEntry(
                    index.toFloat(),     // X değeri
                    data.high.toFloat(), // En yüksek fiyat
                    data.low.toFloat(),  // En düşük fiyat
                    data.open.toFloat(), // Açılış fiyatı
                    data.close.toFloat() // Kapanış fiyatı
                )
            }

            // CandleDataSet oluştur
            val candleDataSet = CandleDataSet(entries, "Candle Stick Data").apply {
                shadowWidth = 1.5f
                barSpace = 0.1f
                increasingColor = Color.Green.toArgb()
                decreasingColor = Color.Red.toArgb()
                increasingPaintStyle = Paint.Style.FILL
                decreasingPaintStyle = Paint.Style.FILL
                neutralColor = Color.Gray.toArgb()
                shadowColorSameAsCandle = true
                setDrawValues(false) // Ekstra değerler gizlenir
            }

            // X ekseni tarih formatı
            chart.xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.toInt()
                    return if (index in candleStickData.indices) {
                        val timestamp = candleStickData[index].timestamp
                        val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                        sdf.format(Date(timestamp))
                    } else {
                        ""
                    }
                }
            }

            // Y ekseni aralıklarını ayarla
            chart.axisLeft.apply {
                axisMinimum = candleStickData.minOfOrNull { it.low.toFloat() }?.times(0.99f) ?: 0f
                axisMaximum = candleStickData.maxOfOrNull { it.high.toFloat() }?.times(1.01f) ?: 0f
            }

            // Chart için data ayarla ve güncelle
            chart.data = CandleData(candleDataSet)
            chart.invalidate() // Grafiği yeniden çiz
        }
    )
}

@Composable
fun xx() {
    AndroidView(
        factory = { context ->
            val chart = CandleStickChart(context)
            val entries = mutableListOf<CandleEntry>()

            // Mum grafik verisi ekleyin
            entries.add(CandleEntry(0f, 100f, 80f, 90f, 85f)) // (x, high, low, open, close)
            entries.add(CandleEntry(1f, 110f, 90f, 100f, 95f))

            val dataSet = CandleDataSet(entries, "Candle Data").apply {
                color = android.graphics.Color.BLUE
                shadowColor = android.graphics.Color.GRAY
                shadowWidth = 0.7f
                decreasingColor = android.graphics.Color.RED
                increasingColor = android.graphics.Color.GREEN
                decreasingPaintStyle = android.graphics.Paint.Style.FILL
                increasingPaintStyle = android.graphics.Paint.Style.FILL
            }

            chart.data = CandleData(dataSet)
            chart.invalidate()

            chart // AndroidView bu chart'ı döner
        },
        modifier = Modifier.fillMaxSize()
    )

}


