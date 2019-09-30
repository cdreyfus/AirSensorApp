package fr.cdreyfus.airqualitysensorapp.ui.data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import fr.cdreyfus.airqualitysensorapp.R
import fr.cdreyfus.airqualitysensorapp.model.Feed
import fr.cdreyfus.airqualitysensorapp.model.User
import kotlinx.android.synthetic.main.activity_data.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataActivity : AppCompatActivity() {

    private val dataViewModel: DataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_data)

        val user = intent.getParcelableExtra(EXTRA_USER) as User

        dataViewModel.setUser(user)

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

        dataViewModel.feedKey.observe(this, feedKeyObserver)
        dataViewModel.feeds.observe(this, feedsObserver)
    }

    companion object {

        private const val EXTRA_USER: String = "EXTRA_USER"

        fun createIntent(context: Context, user: User): Intent {
            return Intent(context, DataActivity::class.java).putExtra(EXTRA_USER, user)
        }
    }

}