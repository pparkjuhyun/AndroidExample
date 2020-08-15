package ppjh.mvvm.roomexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.List;

/**
 * Context가 필요한 ViewModel은 AndroidViewModel을 상속받아 사
 */
public class MainViewModel extends AndroidViewModel {
    AppDatabase db;
    public String newTodo;
    public LiveData<List<Todo>> todos;

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application, AppDatabase.class, "todo-db")
                .allowMainThreadQueries()
                .build();
        todos = getAll();
    }

    public LiveData<List<Todo>> getAll() {
        return db.todoDao().getAll();
    }
    
    public void insert(String todo) {
        db.todoDao().insert(new Todo(todo));
    }
}
