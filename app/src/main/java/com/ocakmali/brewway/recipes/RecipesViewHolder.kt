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

    private val title: TextView = view.findViewById(R.id.title)
    private val coffeeMaker: TextView = view.findViewById(R.id.coffee_maker)
    private val grinder: TextView = view.findViewById(R.id.grinder)
    private val coffee: TextView = view.findViewById(R.id.coffee)
    private val coffeeAmount: TextView = view.findViewById(R.id.coffee_amount)
    private val waterAmount: TextView = view.findViewById(R.id.water_amount)
    private val waterTemperature: TextView = view.findViewById(R.id.water_temperature)


    override fun bind(obj: RecipeView) {
        title.text = obj.title
        coffeeMaker.text = obj.equipment.coffeeMaker?.name ?: ""
        grinder.text = obj.equipment.grinder?.name ?: ""
        coffee.text = obj.equipment.coffee?.name ?: ""
        coffeeAmount.text = obj.equipment.coffeeAmount.toString()
        waterAmount.text = obj.equipment.waterAmount.toString()
        waterTemperature.text = obj.equipment.waterTemperature.toString()
    }
}