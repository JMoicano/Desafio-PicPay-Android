package com.jmoicano.desafiopicpay.app.contacts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.databinding.ContactItemBinding

class UserAdapter(val clickListener: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>(), Filterable {

    private val users = mutableListOf<User>()

    private lateinit var filter: UserFilter


    var items: List<User>
        get() = users
        set(value) {
            users.clear()
            users.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = DataBindingUtil.inflate<ContactItemBinding>(
            layoutInflater,
            R.layout.contact_item,
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class ViewHolder(private val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.user = item
            binding.root.setOnClickListener {
                clickListener(item)
            }
        }
    }

    override fun getFilter(): Filter {
        if (!::filter.isInitialized) {
            filter = UserFilter(users)
        }
        return filter
    }

    inner class UserFilter(originalList: List<User>) : Filter(){
        private val originalList = originalList.toList()
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return FilterResults().apply {
                val filteredResults = if (!constraint.isNullOrBlank()) {
                    originalList.filter { item ->
                        item.name.contains(constraint, true)
                        item.username.contains(constraint, true)

                    }
                } else {
                    originalList
                }
                count = filteredResults.size
                values = filteredResults
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val values = results?.values as? List<User>
            values?.let {
                users.clear()
                users.addAll(it)
                notifyDataSetChanged()
            }
        }

    }
}