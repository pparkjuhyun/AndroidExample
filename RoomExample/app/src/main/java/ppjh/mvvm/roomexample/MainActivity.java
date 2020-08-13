package ppjh.mvvm.roomexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mTodoEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoEditText = findViewById(R.id.todo_edit);
        mResultTextView = findViewById(R.id.result_text);

//        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.clas s, "todo-db")
//                .allowMainThreadQueries()
//                .build();
//
//        db.todoDao().getAll().observe(this, todos -> {
//            mResultTextView.setText(todos.toString());
//        });
        //old version
//        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //ViewModelProviders.of() method는 deprecated. jetpack의 ViewModel dependency를 추가해 ViewModelProvider로 인스턴스를 받아야함
        MainViewModel viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainViewModel.class);

        //일반 뷰모델의 경우
//        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getAll().observe(this, todos -> mResultTextView.setText(todos.toString()));

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.todoDao().insert(new Todo(mTodoEditText.getText().toString()));
//                mResultTextView.setText(db.todoDao().getAll().toString());
                viewModel.insert(new Todo(mTodoEditText.getText().toString()));
            }
        });
    }
}