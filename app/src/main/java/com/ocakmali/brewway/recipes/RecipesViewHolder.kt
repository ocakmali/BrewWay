/*
 * MIT License
 *
 * Copyright (c) 2019 Mehmet Ali Ocak
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ocakmali.brewway.recipes

import android.view.View
import android.widget.TextView
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseViewHolder
import com.ocakmali.brewway.datamodel.RecipeView

class RecipesViewHolder(view: View) : BaseViewHolder<RecipeView>(view) {

    private val titleTextView: TextView = view.findViewById(R.id.title)
    private val coffeeMakerTextView: TextView = view.findViewById(R.id.coffee_maker)
    private val grinderTextView: TextView = view.findViewById(R.id.grinder)
    private val coffeeTextView: TextView = view.findViewById(R.id.coffee)
    private val coffeeAmountTextView: TextView = view.findViewById(R.id.coffee_amount)
    private val waterAmountTextView: TextView = view.findViewById(R.id.water_amount)
    private val waterTemperatureTextView: TextView = view.findViewById(R.id.water_temperature)


    override fun bind(obj: RecipeView) {
        titleTextView.text = obj.title ?: "${itemView.context.getString(R.string.recipe)}#${obj.id}"
        with(obj.equipment) {
            coffeeMakerTextView.setTextAndVisibility(coffeeMaker?.name)
            grinderTextView.setTextAndVisibility(grinder?.name)
            setCoffeeText(this)
            setWaterText(this)
        }
    }

    private fun setWaterText(equipment: RecipeView.Equipment) {
        with(equipment) {
            if (waterAmount == null && waterTemperature == null) {
                waterAmountTextView.visibility = View.GONE
                waterTemperatureTextView.visibility = View.GONE
            } else {
                waterAmountTextView.visibility = View.VISIBLE
                waterTemperatureTextView.visibility = View.VISIBLE
                waterAmountTextView.text = waterAmount?.toString()
                        ?: itemView.context.getText(R.string.amount_not_specified)
                waterTemperatureTextView.text = waterTemperature?.toString()
                        ?: itemView.context.getText(R.string.temperature_not_specified)
            }
        }
    }

    private fun setCoffeeText(equipment: RecipeView.Equipment) {
        with(equipment) {
            if (coffee?.name.isNullOrBlank() && coffeeAmount?.toString().isNullOrBlank()) {
                coffeeTextView.visibility = View.GONE
                coffeeAmountTextView.visibility = View.GONE
            } else {
                coffeeTextView.visibility = View.VISIBLE
                coffeeAmountTextView.visibility = View.VISIBLE
                coffeeTextView.text = coffee?.name
                        ?: itemView.context.getText(R.string.coffee_not_specified)
                coffeeAmountTextView.text = coffeeAmount?.toString()
                        ?: itemView.context.getText(R.string.amount_not_specified)
            }
        }
    }

    private fun TextView.setTextAndVisibility(text: String?) {
        if (text.isNullOrBlank()) {
            visibility = View.GONE
            return
        }
        visibility = View.VISIBLE
        setText(text)
    }
}