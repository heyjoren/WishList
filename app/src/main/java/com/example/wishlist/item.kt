package com.example.wishlist

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wishlist.Adapter.ItemAdapter
import com.example.wishlist.database.item.ItemData
import com.example.wishlist.database.item.itemDatabase
import com.example.wishlist.databinding.AlertDialogDeleteBinding
import com.example.wishlist.databinding.FragmentItemBinding
import com.example.wishlist.databinding.ListItemBinding
import com.example.wishlist.detailItem.detail_item
import com.example.wishlist.itemDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class item : Fragment(), ItemAdapter.ItemClickListener {

    private lateinit var binding: FragmentItemBinding
    private lateinit var viewModel: ViewModel_item
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup ViewModel
        val application = requireNotNull(this.activity).application
        val dataSource = itemDatabase.getInstance(application).itemDao
        val viewModelFactory = ViewModel_Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel_item::class.java)
        binding.itemViewModel = viewModel

        setupRecyclerView(binding.root)

//        val backgroundImageView: ImageView = binding.root.findViewById(R.id.backgroundImageView)
//        Glide.with(this)
//            .load(R.drawable.achtergrond2)
//            .placeholder(R.drawable.achtergrond2_res10)
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(backgroundImageView)

        return binding.root
    }

    private fun setupRecyclerView(view: View) {
        adapter = ItemAdapter(this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.itemlist.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

    }


    override fun onClick(item: ItemData, binding: ListItemBinding) {
        item.itemId?.let { itemId ->
            findNavController().navigate(itemDirections.actionItemToDetailItem(itemId))
        }

        binding.deleteButtonContainer.findViewById<View>(R.id.delete_button).setOnClickListener {
            showDeleteConfirmationDialog(item)
        }
    }

    private fun showDeleteConfirmationDialog(item: ItemData) {
        val deleteDialogFragment = DeleteDialogFragment.newInstance(item)
        deleteDialogFragment.show(childFragmentManager, "deleteConfirmation")
    }


}