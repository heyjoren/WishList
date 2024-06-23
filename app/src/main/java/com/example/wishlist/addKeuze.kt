package com.example.wishlist

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addKeuze.newInstance] factory method to
 * create an instance of this fragment.
 */
class addKeuze : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomAlertDialog)

            val items = arrayOf("Item", "Bedrag")
            val icons = arrayOf(R.drawable.baseline_assignment_add_24, R.drawable.baseline_add_card_24)

            val adapter = TextIconAdapter(it, items, icons)

            builder.setTitle("Voeg toe")
                .setAdapter(adapter) { _, which ->
                    when (which) {
                        0 -> navigateToAddItemFragment()
                        1 -> navigateToAddBedragFragment()
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun navigateToAddItemFragment() {
        val navController = findNavController()
        navController.navigate(R.id.action_addKeuze_to_add_item)

    }

    private fun navigateToAddBedragFragment() {
        // Navigeer naar de BedragFragment
    }
}
