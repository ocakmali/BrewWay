package com.ocakmali.brewway.addeditrecipe

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ocakmali.brewway.R
import com.ocakmali.brewway.view.TwoButtonAlertDialog
import com.ocakmali.brewway.view.TwoButtonAlertDialog.OnClickListener.Companion.CONTINUE
import com.ocakmali.brewway.view.TwoButtonAlertDialog.OnClickListener.Companion.EXIT
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ExitDialogFragment : DialogFragment(), TwoButtonAlertDialog.OnClickListener {

    private val addEditRecipeViewModel by sharedViewModel<AddEditRecipeViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TwoButtonAlertDialog(requireActivity(),
                R.string.exit_add_edit_recipe,
                R.string.continue_updating,
                R.string.remove,
                this)
    }

    override fun onClick(which: Int) {
        when(which) {
            CONTINUE -> dismiss()
            EXIT -> addEditRecipeViewModel.exitWithoutSaving()
        }
    }
}