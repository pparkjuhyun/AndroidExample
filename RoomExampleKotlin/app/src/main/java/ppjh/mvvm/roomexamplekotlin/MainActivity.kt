package ppjh.mvvm.roomexamplekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todo-db")
                .allowMainThreadQueries()
                .build()
        db.todoDao().getAll().observe(this, Observer { todos -> result_text.text = todos.toString() })

        add_btn.setOnClickListener {
            db.todoDao().insert(Todo(todo_edit.text.toString()))
//            result_text.text = db.todoDao().getAll().toString()
        }
    }
}