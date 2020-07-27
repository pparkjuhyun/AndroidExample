package ppjh.mvvm.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class NameViewModel: ViewModel() {

    // by lazy, read-only data에 대해 초기화 지연. 첫 access때 초기화 블록 실행
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>("Hong Gildong")
    }
}