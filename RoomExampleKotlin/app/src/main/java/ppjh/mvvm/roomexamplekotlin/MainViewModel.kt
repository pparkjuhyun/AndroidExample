package ppjh.mvvm.roomexamplekotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "todo-db")
        .allowMainThreadQueries()
        .build()

    var todos: LiveData<List<Todo>>
    var newTodo: String? = null

    init {
        todos = getAll()
    }

    fun getAll(): LiveData<List<Todo>> {
        return db.todoDao().getAll()
    }

    //suspend keyword로 coroutine 스코프 안에서 처리되도록 강제
    fun insert(todo: String) {
        viewModelScope.launch(Dispatchers.IO) { db.todoDao().insert(Todo(todo)) }
    }

}