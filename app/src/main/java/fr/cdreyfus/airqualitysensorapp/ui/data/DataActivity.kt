package fr.cdreyfus.airqualitysensorapp.ui.data

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import fr.cdreyfus.airqualitysensorapp.R
import fr.cdreyfus.airqualitysensorapp.model.Feed
import fr.cdreyfus.airqualitysensorapp.model.FeedDataPoint
import fr.cdreyfus.airqualitysensorapp.model.User
import kotlinx.android.synthetic.main.activity_data.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DataActivity : AppCompatActivity() {

    private val dataViewModel: DataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_data)

        val user = intent.getParcelableExtra(EXTRA_USER) as User

        dataViewModel.setUser(user)


        // background color
        chart.setBackgroundColor(Color.WHITE)

        // disable description text
        chart.description.isEnabled = false

        // enable touch gestures
        chart.setTouchEnabled(true)

        // set listeners
        chart.setDrawGridBackground(false)

        // enable scaling and dragging
        chart.isDragEnabled = false
        chart.setScaleEnabled(false)

        // force pinch zoom along both axis
        chart.setPinchZoom(false)


        val feedKeyObserver = Observer<String> { dataViewModel.getFeedData() }
        val feedsObserver = Observer<List<Feed>?> { data ->
            val spinnerValues = mutableListOf<String>()
            data?.forEach { feed -> spinnerValues.add(feed.name) }
            feed_spinner.adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerValues
            )

            feed_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    dataViewModel.setFeedKey(spinnerValues[position])
                }
            }
        }
        val feedDataObserver = Observer<List<FeedDataPoint>> {

            chart.description.text = dataViewModel.feedKey.value

            val leftAxis = chart.axisLeft
            leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

            val values = ArrayList<Entry>()
            it.forEach { item ->
                values.add(Entry(item.created_at, item.value))
            }
            val set1 = LineDataSet(values, "DataSet 1")

            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTextSize(20f)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }


            // set data
            chart.data = data
            chart.invalidate()
        }

        dataViewModel.feedList.observe(this, feedsObserver)
        dataViewModel.feedKey.observe(this, feedKeyObserver)
        dataViewModel.feedData.observe(this, feedDataObserver)


    }

    companion object {

        private const val EXTRA_USER: String = "EXTRA_USER"

        fun createIntent(context: Context, user: User): Intent {
            return Intent(context, DataActivity::class.java).putExtra(EXTRA_USER, user)
        }
    }

}