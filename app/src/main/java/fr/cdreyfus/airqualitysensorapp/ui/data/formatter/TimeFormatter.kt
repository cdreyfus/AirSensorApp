package fr.cdreyfus.airqualitysensorapp.ui.data.formatter

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class TimeFormatter: IAxisValueFormatter {

    companion object {
        private val mFormat = SimpleDateFormat("dd/MM", Locale.FRANCE)
    }

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        val date = Date(value.toLong())
        return mFormat.format(date)
    }
}