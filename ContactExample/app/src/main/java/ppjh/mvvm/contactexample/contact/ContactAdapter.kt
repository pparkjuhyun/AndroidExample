package ppjh.mvvm.contactexample.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ppjh.mvvm.contactexample.R
import ppjh.mvvm.contactexample.databinding.ContactListItemBinding

class ContactAdapter(val contactItemClick: (Contact) -> Unit, val contactItemLongClick: (Contact) -> Unit): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var contacts: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding: ContactListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.contact_list_item, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    inner class ContactViewHolder(binding: ContactListItemBinding): RecyclerView.ViewHolder(binding.root) {

        private val binding: ContactListItemBinding = binding

        fun bind(contact: Contact) {
            binding.tvInitial.text = contact.initial.toString()
            binding.tvName.text = contact.name
            binding.tvNumber.text = contact.number

            binding.cvContactItem.setOnClickListener {
                contactItemClick(contact)
            }
            binding.cvContactItem.setOnLongClickListener {
                contactItemLongClick(contact)
                true
            }
        }
    }

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}