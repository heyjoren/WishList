package com.example.wishlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.example.wishlist.databinding.FragmentAddItemBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.wishlist.addItem.ViewModel_addItem
import com.example.wishlist.database.item.itemDatabase
import androidx.navigation.fragment.findNavController

class add_item : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAddItemBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_item, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = itemDatabase.getInstance(application).itemDao
        val viewModelFactory = ViewModel_Factory(dataSource, application)
        val viewModel: ViewModel_addItem by viewModels { viewModelFactory }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToPreviousFragment.observe(viewLifecycleOwner, Observer { shouldNavigate ->
            if (shouldNavigate == true) {
                findNavController().navigate(R.id.action_add_item_to_item)
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}