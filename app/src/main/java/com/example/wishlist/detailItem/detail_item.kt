package com.example.wishlist.detailItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wishlist.R
import com.example.wishlist.database.item.ItemData
import com.example.wishlist.databinding.FragmentDetailItemBinding
import com.example.wishlist.ViewModel_Factory
import com.example.wishlist.database.item.itemDatabase
import com.example.wishlist.itemDirections

class detail_item : Fragment() {

    private lateinit var binding: FragmentDetailItemBinding
    private lateinit var viewModel: ViewModel_detailItem
    private lateinit var viewModelFactory: ViewModel_Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_item, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(this.activity).application
        val dataSource = itemDatabase.getInstance(application).itemDao
        val itemId = arguments?.getLong("itemId") ?: 0

        viewModelFactory = ViewModel_Factory(dataSource, application, itemId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel_detailItem::class.java)

        binding.itemViewModel = viewModel

        binding.itemChange.setOnClickListener {
            findNavController().navigate(detail_itemDirections.actionDetailItemToChangeItem(itemId))
        }


        return binding.root
    }
}

