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

package com.ocakmali.data.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.ocakmali.data.db.CoffeeConstants
import com.ocakmali.data.db.CoffeeMakerConstants
import com.ocakmali.data.db.GrinderConstants
import com.ocakmali.data.db.RecipeConstants
import com.ocakmali.data.db.RecipeConstants.COLUMN_COFFEE_AMOUNT
import com.ocakmali.data.db.RecipeConstants.COLUMN_CREATED_DATE
import com.ocakmali.data.db.RecipeConstants.COLUMN_ID
import com.ocakmali.data.db.RecipeConstants.COLUMN_TITLE
import com.ocakmali.data.db.RecipeConstants.COLUMN_WATER_AMOUNT
import com.ocakmali.data.db.RecipeConstants.COLUMN_WATER_TEMPERATURE
import com.ocakmali.data.db.RecipeConstants.TABLE_NAME
import com.ocakmali.domain.model.Recipe

internal fun RecipeAndEquipments.toRecipe() = Recipe(title,
        Recipe.Equipment(coffeeMaker.toCoffeeMaker(),
                coffee.toCoffee(),
                grinder.toGrinder(),
                coffeeAmount,
                waterAmount,
                waterTemp
        ),
        createdDate,
        id)

@DatabaseView(value = """
    SELECT $COLUMN_TITLE, $COLUMN_COFFEE_AMOUNT, $COLUMN_WATER_AMOUNT, $COLUMN_WATER_TEMPERATURE, $COLUMN_CREATED_DATE, $COLUMN_ID,
     ${GrinderConstants.TABLE_NAME}.*,
      ${CoffeeConstants.TABLE_NAME}.*,
       ${CoffeeMakerConstants.TABLE_NAME}.* FROM $TABLE_NAME
    INNER JOIN ${GrinderConstants.TABLE_NAME} ON ${GrinderConstants.COLUMN_ID} = ${RecipeConstants.COLUMN_GRINDER_ID}
    INNER JOIN ${CoffeeConstants.TABLE_NAME} ON ${CoffeeConstants.COLUMN_ID} = ${RecipeConstants.COLUMN_COFFEE_ID}
    INNER JOIN ${CoffeeMakerConstants.TABLE_NAME} ON ${CoffeeMakerConstants.COLUMN_ID} = ${RecipeConstants.COLUMN_COFFEE_MAKER_ID}
    """)
data class RecipeAndEquipments(@ColumnInfo(name = COLUMN_TITLE) val title: String,
                               @ColumnInfo(name = COLUMN_COFFEE_AMOUNT) val coffeeAmount: Int,
                               @ColumnInfo(name = COLUMN_WATER_AMOUNT) val waterAmount: Int,
                               @ColumnInfo(name = COLUMN_WATER_TEMPERATURE) val waterTemp: Int,
                               @ColumnInfo(name = COLUMN_CREATED_DATE) val createdDate: Long,
                               @ColumnInfo(name = COLUMN_ID) val id: Int,
                               @Embedded val grinder: GrinderEntity,
                               @Embedded val coffee: CoffeeEntity,
                               @Embedded val coffeeMaker: CoffeeMakerEntity)