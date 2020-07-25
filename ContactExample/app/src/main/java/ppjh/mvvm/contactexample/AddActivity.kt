package ppjh.mvvm.contactexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add.*
import ppjh.mvvm.contactexample.contact.Contact
import ppjh.mvvm.contactexample.contact.ContactViewModel
import ppjh.mvvm.contactexample.databinding.ActivityAddBinding

class AddActivity: AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var binding: ActivityAddBinding
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        if(intent != null && intent.hasExtra(EXTRA_CONTACT_NAME) && intent.hasExtra(EXTRA_CONTACT_NUMBER)
            && intent.hasExtra(EXTRA_CONTACT_ID)) {
            binding.addEdittextName.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
            binding.addEdittextNumber.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)

        }

        add_button.setOnClickListener {
            val name = binding.addEdittextName.text.toString().trim()
            val number = binding.addEdittextNumber.text.toString()

            if(name.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "Please enter name and number.", Toast.LENGTH_LONG).show()
            } else {
                val initial = name[0].toUpperCase()
                val contact = Contact(id, name, number, initial)
                contactViewModel.insert(contact)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
    }
}