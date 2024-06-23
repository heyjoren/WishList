package com.example.wishlist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wishlist.database.item.itemDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ViewModel_item
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val loadedBitmaps = mutableMapOf<Int, Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Toolbar as the action bar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize ViewModel with ViewModelFactory
        val application = requireNotNull(this).application
        val dataSource = itemDatabase.getInstance(application).itemDao
        val viewModelFactory = ViewModel_Factory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel_item::class.java)

        drawerLayout = findViewById(R.id.drawerLayout)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setBackgroundResource(android.R.color.transparent)

        // Configure NavController with DrawerLayout
        val navController = this.findNavController(R.id.fr_navhost)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        // Set NavController destination change listener
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fragmentsWithBackButton = setOf(
                R.id.detail_item,
                R.id.change_item,
                R.id.add_item
            )
            val fragmentsWithDrawer = setOf(
                R.id.home,
                R.id.item,
                R.id.bedrag
            )

            when (destination.id) {
                in fragmentsWithBackButton -> showBackButton(true)
                in fragmentsWithDrawer -> showBackButton(false)
                else -> showBackButton(false)
            }
        }

        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            navController.navigate(R.id.addKeuze)
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                loadedBitmaps
            }
        }

        preloadImages()
    }

    private fun preloadImages() {
        val imageIds = listOf(
            R.drawable.achtergrond1,
            R.drawable.achtergrond2,
            R.drawable.achtergrond3,
            R.drawable.achtergrond4
        )

        imageIds.forEach { imageId ->
            Glide.with(this)
                .load(imageId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .preload()
        }
    }

    private fun showBackButton(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
        supportActionBar?.setDisplayShowHomeEnabled(show)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.fr_navhost)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}
