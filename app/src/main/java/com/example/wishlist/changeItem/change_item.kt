package com.example.wishlist.changeItem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wishlist.MainActivity
import com.example.wishlist.R
import com.example.wishlist.ViewModel_Factory
import com.example.wishlist.database.item.ItemData
import com.example.wishlist.database.item.itemDatabase
import com.example.wishlist.databinding.FragmentChangeItemBinding
import com.example.wishlist.detailItem.ViewModel_detailItem
import com.example.wishlist.item

class change_item : Fragment() {

    private lateinit var viewModel: ViewModel_changeItem
    private lateinit var viewModelFactory: ViewModel_Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentChangeItemBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_change_item, container, false)

        val itemId = arguments?.getLong("itemId") ?: 0

        val application = requireNotNull(this.activity).application
        val dataSource = itemDatabase.getInstance(application).itemDao

        viewModelFactory = ViewModel_Factory(dataSource, application, itemId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel_changeItem::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.changeButton.setOnClickListener {
            viewModel.onUpdate()

        }

        viewModel.navigateToPreviousFragment.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                findNavController().navigate(R.id.action_change_item_to_item)
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}