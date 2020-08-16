package ppjh.mvvm.roomexamplekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ppjh.mvvm.roomexamplekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //new version for getting viewmodel
    val viewModel: MainViewModel by viewModels()
//    val viewModel by viewModels<MainViewModel>()  //위랑 똑같음

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this   //for LiveData
        binding.viewModel = viewModel
    }
}