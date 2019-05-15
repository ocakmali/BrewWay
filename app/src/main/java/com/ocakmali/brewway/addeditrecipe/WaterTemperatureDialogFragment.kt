package com.ocakmali.brewway.addeditrecipe

import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.ocakmali.brewway.R
import com.ocakmali.brewway.view.NumberPickerDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WaterTemperatureDialogFragment : DialogFragment(), NumberPickerDialog.OnNumberSetListener {

    private val addEditRecipeViewModel by sharedViewModel<AddEditRecipeViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val value = addEditRecipeViewModel.recipe.value?.equipment?.waterTemperature ?: 95
        return NumberPickerDialog(context = requireActivity(),
                numberSetListener = this,
                title = R.string.set_water_temperature,
                minValue = 0,
                value = value,
                maxValue = 100)

    }

    override fun onNumberSet(view: NumberPicker, value: Int) {
      addEditRecipeViewModel.updateWaterTemperature(value)
    }
}