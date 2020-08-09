package ppjh.mvvm.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel(private val repository: SearchRepository): ViewModel() {
    private var name: String =""
    val users: MutableLiveData<ArrayList<User>> = MutableLiveData()

    fun search() {

    }
}