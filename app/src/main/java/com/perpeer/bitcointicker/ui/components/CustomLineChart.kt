package com.perpeer.bitcointicker.ui.components

import android.widget.LinearLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Pelşin KAPLAN on 8.01.2025.
 */

@Composable
fun LineChartView(
    modifier: Modifier = Modifier,
    chartData: List<Pair<Double, Double>> // x, y data points
) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )

                // General Chart Settings
                description.isEnabled = false
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(true)
                setPinchZoom(true)
                setDrawGridBackground(false)
                legend.isEnabled = false

                // X-Axis
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f // Minimum aralık belirler
                    isGranularityEnabled = true // Granularity özelliğini etkinleştirir
                    labelRotationAngle = 45f // Etiketleri 45 derece döndür
                }
                xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val timestamp = value.toLong() * 1000 // Saniyeyi milisaniyeye çevir
                        val sdf =
                            SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()) // Tarih formatı
                        return sdf.format(Date(timestamp))
                    }
                }

                axisLeft.apply {
                    setDrawGridLines(true)
                    granularity = 0.00001f // Daha hassas değerler için minimum aralık
                    isGranularityEnabled = true
                }
                axisRight.isEnabled = false // Sağ ekseni devre dışı bırak

            }
        },
        modifier = modifier.fillMaxSize(),
        update = { chart ->
            val entries = chartData.map { (x, y) -> Entry(x.toFloat(), y.toFloat()) }
            val dataSet = LineDataSet(entries, "Sample Data").apply {
                color = Color.Blue.toArgb()
                setCircleColor(Color.Red.toArgb())
                lineWidth = 2f
                circleRadius = 1f
                setDrawCircleHole(false)
                valueTextSize = 5f
                setDrawFilled(false)
            }

            chart.data = LineData(dataSet)
            chart.invalidate() // Refresh the chart
        }
    )
}

