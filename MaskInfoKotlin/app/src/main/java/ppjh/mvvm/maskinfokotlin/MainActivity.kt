package ppjh.mvvm.maskinfokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ppjh.mvvm.maskinfokotlin.model.Store
import ppjh.mvvm.maskinfokotlin.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var rvStore : RecyclerView? = null
    private val viewModel : MainViewModel by this.viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)

        this.rvStore = this.findViewById(R.id.rv_store)
        val pgBar : ProgressBar = findViewById(R.id.pb_loading)
        val adapter = StoreAdapter()
        this.rvStore?.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = adapter

//            동일코드
//            rvStore.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//            val adapter = StoreAdapter()
//            rvStore.adapter = adapter
        }

        val items = listOf(
            Store("abc", "111", "111", 33.33, 33.33, "pharmacy", "plenty", "33", "33"),
            Store("abc", "111", "111", 33.33, 33.33, "pharmacy", "plenty", "33", "33"),
            Store("abc", "111", "111", 33.33, 33.33, "pharmacy", "plenty", "33", "33")
        )

        this.viewModel.apply {
            this.itemLiveData.observe(this@MainActivity, Observer {
                adapter.updateItems(it)
            })

            this.loadingLiveData.observe(this@MainActivity, Observer { isLoading ->
                pgBar.visibility = if(isLoading) View.VISIBLE else View.GONE
            })
        }

        viewModel.fetchStoreInfo()
    }
}