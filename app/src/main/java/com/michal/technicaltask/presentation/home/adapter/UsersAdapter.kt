package com.michal.technicaltask.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.michal.technicaltask.databinding.ItemHomeUserBinding

class UsersAdapter : ListAdapter<UserItem, UsersAdapter.ViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = ItemHomeUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHomeUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserItem) {
            binding.userNameText.text = user.name

            binding.userEmailText.text = user.email

            binding.userCreationText.text = user.creationTimeText
        }
    }

    class ItemCallback : DiffUtil.ItemCallback<UserItem>() {

        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean = oldItem == newItem
    }
}