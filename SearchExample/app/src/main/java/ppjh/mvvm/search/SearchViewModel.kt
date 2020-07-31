package ppjh.mvvm.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
* ViewModel은 현재 Data의 상태에만 관여하고
* 실제적인 데이터 CRUD는 Repository 인터페이스로 분리해 관리
*/
class SearchViewModel(private val repository: SearchRepository): ViewModel() {
    private var name: String =""
    val users: MutableLiveData<ArrayList<User>> = MutableLiveData()

    fun search() {
        
    }
}
