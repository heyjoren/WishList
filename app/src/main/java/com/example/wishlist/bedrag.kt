package com.example.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
//voor view-model
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.wishlist.databinding.FragmentBedragBinding


/**
 * A simple [Fragment] subclass.
 * Use the [bedrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class bedrag : Fragment() {
    private lateinit var binding: FragmentBedragBinding
    private val viewModel: ViewModel_bedrag by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bedrag, container, false)*/

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bedrag, container, false)

//        val backgroundImageView: ImageView = binding.root.findViewById(R.id.backgroundImageView)
//        Glide.with(this)
//            .load(R.drawable.achtergrond3)
//            .placeholder(R.drawable.achtergrond3_res10)
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(backgroundImageView)

        binding.bedragViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.welcomeMessage.observe(viewLifecycleOwner, Observer { message ->
            binding.welcomeMessage.text = message
        })

        return binding.root
    }
}