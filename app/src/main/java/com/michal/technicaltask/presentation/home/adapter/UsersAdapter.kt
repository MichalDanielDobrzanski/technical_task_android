package com.michal.technicaltask.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.michal.technicaltask.databinding.ItemHomeUserBinding

class UsersAdapter(
    private val onUserRemoved: (UserItem) -> Unit
) : ListAdapter<UserItem, UsersAdapter.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = ItemHomeUserBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onUserRemoved = onUserRemoved
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == true) {
                holder.refreshTime(getItem(position))
            }
        }
    }

    class ViewHolder(
        private val binding: ItemHomeUserBinding,
        private val onUserRemoved: (UserItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserItem) {
            binding.userNameText.text = user.name

            binding.userEmailText.text = user.email

            binding.userCreationText.text = user.creationRelativeTimeText

            binding.root.setOnLongClickListener {
                onUserRemoved.invoke(user)
                true
            }
        }

        fun refreshTime(userItem: UserItem) {
            binding.userCreationText.text = userItem.creationRelativeTimeText
        }
    }

    object ItemCallback : DiffUtil.ItemCallback<UserItem>() {

        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean = oldItem == newItem

        override fun getChangePayload(oldItem: UserItem, newItem: UserItem): Any? {
            return if (oldItem.creationRelativeTimeText != newItem.creationRelativeTimeText) true else null
        }
    }
}