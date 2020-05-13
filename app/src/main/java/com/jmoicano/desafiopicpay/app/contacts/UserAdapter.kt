package com.jmoicano.desafiopicpay.app.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.databinding.ContactItemBinding

class UserAdapter(val clickListener: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val users = mutableListOf<User>()

    var items: List<User>
        get() = users
        set(value) {
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
}