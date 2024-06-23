package com.example.wishlist.Adapter

import DeleteDialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wishlist.R
import com.example.wishlist.databinding.ListItemBinding
import com.example.wishlist.database.item.ItemData

class ItemAdapter(private val clickListener: ItemClickListener) : ListAdapter<ItemData, ItemAdapter.ViewHolder>(
    ItemDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemData, clickListener: ItemClickListener) {
            binding.item = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                clickListener.onClick(item, binding)
            }

            binding.deleteButtonContainer.findViewById<View>(R.id.delete_button).setOnClickListener {
                val dialogFragment = DeleteDialogFragment.newInstance(item)
                dialogFragment.show((binding.root.context as AppCompatActivity).supportFragmentManager, "deleteConfirmation")
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<ItemData>() {
        override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            val result = oldItem.itemId == newItem.itemId
            return result
        }

        override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
            val result = oldItem == newItem
            return result
        }
    }


    interface ItemClickListener {
        fun onClick(item: ItemData, binding: ListItemBinding)
    }
}
