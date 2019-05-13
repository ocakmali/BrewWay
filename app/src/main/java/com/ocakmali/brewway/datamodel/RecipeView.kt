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

package com.ocakmali.brewway.datamodel

import com.ocakmali.domain.model.Recipe

internal fun RecipeView.toRecipe() = Recipe(title,
        Recipe.Equipment(equipment.coffeeMaker?.toCoffeeMaker(),
                equipment.coffee?.toCoffee(),
                equipment.grinder?.toGrinder(),
                equipment.coffeeAmount,
                equipment.waterAmount,
                equipment.waterTemperature),
        createdDate,
        id)

internal fun Recipe.toView() = RecipeView(title,
        RecipeView.Equipment(equipment.coffeeMaker?.toView(),
                equipment.coffee?.toView(),
                equipment.grinder?.toView(),
                equipment.coffeeAmount,
                equipment.waterAmount,
                equipment.waterTemperature),
        createdDate,
        id)

data class RecipeView(val title: String?,
                      val equipment: RecipeView.Equipment,
                      val createdDate: Long = System.currentTimeMillis(),
                      val id: Int = 0) {

    data class Equipment(val coffeeMaker: CoffeeMakerView?,
                         val coffee: CoffeeView?,
                         val grinder: GrinderView?,
                         val coffeeAmount: Int?,
                         val waterAmount: Int?,
                         val waterTemperature: Int?)

    companion object {
        fun emptyRecipe(): Recipe{
            return Recipe(title = null,
                    equipment = Recipe.Equipment(null,
                            null,
                            null,
                            null,
                            null,
                            null
                    ),
                    createdDate = System.currentTimeMillis(),
                    id = 0
            )
        }
    }
}