package ppjh.mvvm.roomexamplekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //new version for getting viewmodel
    val viewModel: MainViewModel by viewModels()
//    val viewModel by viewModels<MainViewModel>()  //위랑 똑같음

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todo-db")
//                .allowMainThreadQueries()
//                .build()

        //old version
//        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
//        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //LiveData를 적용해 LiveDataList를 return받고 해당 데이터를 observe해 publish할때마다 뷰 업데이트
//        db.todoDao().getAll().observe(this, Observer { todos -> result_text.text = todos.toString() })

        viewModel.getAll().observe(this, Observer { todos -> result_text.text = todos.toString() })

        add_btn.setOnClickListener {
//            db.todoDao().insert(Todo(todo_edit.text.toString()))
            //LiveData 적용을 통해 업데이트 후 뷰 업데이트 삭제
//            result_text.text = db.todoDao().getAll().toString()
            //coroutine을 이용해 async트랜잭션 처리
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.insert(Todo(todo_edit.text.toString()))
            }
        }
    }
}