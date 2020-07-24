package ppjh.mvvm.contactexample.contact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

//ViewModel은 Activity의 Context가 아닌 ApplicationContext를 받기위해 Application을 parameter로 받음
//Activity가 Destroy될 경우 Memory Leak이 발생할 수 있음
class ContactViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ContactRepository(application)
    private val contacts = repository.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return this.contacts
    }

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }
}