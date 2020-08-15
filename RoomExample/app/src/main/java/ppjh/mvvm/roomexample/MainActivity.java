package ppjh.mvvm.roomexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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

import ppjh.mvvm.roomexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

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
        binding.setViewModel(viewModel);

        //일반 뷰모델의 경우
//        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }
}