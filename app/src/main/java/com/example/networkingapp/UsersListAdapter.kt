package com.example.networkingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.networkingapp.databinding.UserNamesListItemBinding
import com.example.networkingapp.userAPI.model.Result

class UsersListAdapter : RecyclerView.Adapter<UsersListAdapter.UserViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var users: List<Result>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserNamesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.apply {
            val user = users[position]
            userNameTV.text = user.name.first
        }
    }

    override fun getItemCount() = users.size

    inner class UserViewHolder(val binding: UserNamesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}