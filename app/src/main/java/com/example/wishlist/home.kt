package com.example.wishlist

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.wishlist.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlin.io.path.Path

class home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: Viewmodel_home by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.homeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        val backgroundImageView: ImageView = binding.root.findViewById(R.id.backgroundImageView)
//        Glide.with(this)
//            .load(R.drawable.achtergrond1)
//            .placeholder(R.drawable.achtergrond1_res10)
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(backgroundImageView)

        return binding.root
    }

}