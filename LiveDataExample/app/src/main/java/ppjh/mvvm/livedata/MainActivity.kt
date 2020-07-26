package ppjh.mvvm.livedata

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import ppjh.mvvm.livedata.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //ViewModel의 LiveData객체를 subscribe할 Observer 객체 생성
        val nameObserver = Observer<String> {
            name ->
            binding.tvName.text = name
        }

        //LiveData에 Observer등록 (subscribe)
        model.currentName.observe(this, nameObserver)
    }

    fun onSubmitClick(view: View) {
        val newName = binding.etNewName.text.toString()
        model.currentName.value = newName
    }
}