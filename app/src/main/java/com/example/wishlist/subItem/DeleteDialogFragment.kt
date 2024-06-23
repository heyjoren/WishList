import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.wishlist.MainActivity
import com.example.wishlist.R
import com.example.wishlist.database.item.ItemData
import com.example.wishlist.ViewModel_item

class DeleteDialogFragment : DialogFragment() {
    companion object {
        private const val ARG_ITEM = "item"

        fun newInstance(item: ItemData): DeleteDialogFragment {
            val fragment = DeleteDialogFragment()
            val args = Bundle()
            args.putParcelable(ARG_ITEM, item)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var itemData: ItemData

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        itemData = requireArguments().getParcelable(ARG_ITEM)!!

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.alert_dialog_delete, null)

            val messageTextView = view.findViewById<TextView>(R.id.message)

            val itemName = itemData.Naam
            val fullText = "Weet je zeker dat je $itemName wilt verwijderen?"

            val spannableString = SpannableString(fullText)

            val start = fullText.indexOf(itemName)
            val end = start + itemName.length

            spannableString.setSpan(
                StyleSpan(android.graphics.Typeface.BOLD),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableString.setSpan(
                RelativeSizeSpan(1.25f),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            messageTextView.text = spannableString


            builder.setView(view)

            val confirmButton = view.findViewById<Button>(R.id.confirmButton)
            val cancelButton = view.findViewById<Button>(R.id.cancelButton)

            confirmButton.setOnClickListener {
                (requireActivity() as MainActivity).viewModel.deleteItem(itemData)
                dismiss()
            }


            cancelButton.setOnClickListener {
                dismiss()
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }
}