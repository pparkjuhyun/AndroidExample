package ppjh.mvvm.contactexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ppjh.mvvm.contactexample.contact.Contact
import ppjh.mvvm.contactexample.contact.ContactAdapter
import ppjh.mvvm.contactexample.contact.ContactViewModel

class MainActivity: AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ContactAdapter(
            {
                contact ->
                val intent = Intent(this, AddActivity::class.java)
                intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
                intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
                intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
                startActivity(intent)
            },
            {
                contact ->
                deleteDialog(contact)
            }
        )

        val lm = LinearLayoutManager(this)
        rv_contact.adapter = adapter
        rv_contact.layoutManager = lm
        rv_contact.setHasFixedSize(true)    //?

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contacts ->
            adapter.setContacts(contacts!!)
        })

        btn_add.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") {_, _ ->}
            .setPositiveButton("YES") {_, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
}