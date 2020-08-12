package ppjh.mvvm.roomexamplekotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "todo-db")
        .allowMainThreadQueries()
        .build()

    fun getAll(): LiveData<List<Todo>> {
        return db.todoDao().getAll()
    }

    //suspend keyword로 coroutine 스코프 안에서 처리되도록 강제
    suspend fun insert(todo: Todo) {
        db.todoDao().insert(todo)
    }

}