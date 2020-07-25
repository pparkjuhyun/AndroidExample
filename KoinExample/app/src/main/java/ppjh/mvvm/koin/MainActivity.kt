package ppjh.mvvm.koin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import ppjh.mvvm.koin.component.HelloPresenter
import ppjh.mvvm.koin.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private val helloPresenter: HelloPresenter by inject()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.tvHello.text = helloPresenter.sayHello()
    }
}