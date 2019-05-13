package com.ocakmali.brewway.addeditrecipe

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.ocakmali.brewway.R
import kotlinx.android.synthetic.main.dialog_timestamp.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TimestampDialogFragment : DialogFragment() {

    private val addEditRecipeViewModel by sharedViewModel<AddEditRecipeViewModel>()
    private val args by navArgs<TimestampDialogFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_timestamp, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timestamp = args.timestamp
        val titleResId = if(timestamp == null) R.string.add_timestamp else R.string.update_timestamp

        dialog?.setTitle(titleResId)
        time_picker.time = timestamp?.time ?: 0
        et_note.setText(timestamp?.note)

        et_note.post {
            if (et_note.requestFocus()) {
                val inputManager =
                        et_note.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.showSoftInput(et_note, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        btn_done.setOnClickListener {
            et_note.clearFocus()
            time_picker.clearFocus()
            val time = time_picker.time
            val note = et_note.text?.toString()?.trim() ?: ""
            if (timestamp == null) {
                addEditRecipeViewModel.addTimestamp(time, note)
            } else {
                addEditRecipeViewModel.updateTimestamp(timestamp,
                            timestamp.copy(time = time, note = note)
                )
            }
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        window?.setLayout((size.x * 0.95).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
    }
}