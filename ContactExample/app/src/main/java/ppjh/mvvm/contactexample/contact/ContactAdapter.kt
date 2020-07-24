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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    inner class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvInitial = itemView.findViewById<TextView>(R.id.tv_initial)
        private val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        private val tvNumber = itemView.findViewById<TextView>(R.id.tv_number)

        fun bind(contact: Contact) {
            tvInitial.text = contact.initial.toString()
            tvName.text = contact.name
            tvNumber.text = contact.number

            itemView.setOnClickListener {
                contactItemClick(contact)
            }
            itemView.setOnLongClickListener {
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