package ppjh.mvvm.contactexample.contact

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception

class ContactRepository(application: Application) {

    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }

    fun insert(contact: Contact) {
        try {
            val thread = Thread(Runnable {  //Room은 DB를 메인쓰레드에 접근할 경우 Exception 발생함
                contactDao.insert(contact)
            })
            thread.start()
        } catch (e: Exception) {
            //do nothing
        }
    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.delete(contact)
            })
            thread.start()
        } catch (e: Exception) {
            //do nothing
        }
    }
}