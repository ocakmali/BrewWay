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
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ocakmali.data.db.CoffeeConstants
import com.ocakmali.data.db.CoffeeMakerConstants
import com.ocakmali.data.db.GrinderConstants
import com.ocakmali.data.db.RecipeConstants
import com.ocakmali.data.db.RecipeConstants.COLUMN_COFFEE_AMOUNT
import com.ocakmali.data.db.RecipeConstants.COLUMN_COFFEE_ID
import com.ocakmali.data.db.RecipeConstants.COLUMN_COFFEE_MAKER_ID
import com.ocakmali.data.db.RecipeConstants.COLUMN_CREATED_DATE
import com.ocakmali.data.db.RecipeConstants.COLUMN_GRINDER_ID
import com.ocakmali.data.db.RecipeConstants.COLUMN_ID
import com.ocakmali.data.db.RecipeConstants.COLUMN_TITLE
import com.ocakmali.data.db.RecipeConstants.COLUMN_WATER_AMOUNT
import com.ocakmali.data.db.RecipeConstants.COLUMN_WATER_TEMPERATURE
import com.ocakmali.data.db.RecipeConstants.TABLE_NAME
import com.ocakmali.domain.model.Recipe

internal fun Recipe.toEntity() = RecipeEntity(title,
        equipment.coffeeMaker?.id,
        equipment.coffee?.id,
        equipment.grinder?.id,
        equipment.coffeeAmount,
        equipment.waterAmount,
        equipment.waterTemperature,
        createdDate,
        id)

@Entity(tableName = TABLE_NAME,
        foreignKeys = [
            ForeignKey(entity = CoffeeMakerEntity::class,
                    parentColumns = arrayOf(CoffeeMakerConstants.COLUMN_ID),
                    childColumns = arrayOf(RecipeConstants.COLUMN_COFFEE_MAKER_ID),
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = CoffeeEntity::class,
                    parentColumns = arrayOf(CoffeeConstants.COLUMN_ID),
                    childColumns = arrayOf(RecipeConstants.COLUMN_COFFEE_ID),
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = GrinderEntity::class,
                    parentColumns = arrayOf(GrinderConstants.COLUMN_ID),
                    childColumns = arrayOf(RecipeConstants.COLUMN_GRINDER_ID),
                    onDelete = ForeignKey.CASCADE)
        ])
data class RecipeEntity(@ColumnInfo(name = COLUMN_TITLE) val title: String,
                        @ColumnInfo(name = COLUMN_COFFEE_MAKER_ID) val coffeeMakerId: Int?,
                        @ColumnInfo(name = COLUMN_COFFEE_ID) val coffeeId: Int?,
                        @ColumnInfo(name = COLUMN_GRINDER_ID) val grinderId: Int?,
                        @ColumnInfo(name = COLUMN_COFFEE_AMOUNT) val coffeeAmount: Int,
                        @ColumnInfo(name = COLUMN_WATER_AMOUNT) val waterAmount: Int,
                        @ColumnInfo(name = COLUMN_WATER_TEMPERATURE) val waterTemperature: Int,
                        @ColumnInfo(name = COLUMN_CREATED_DATE) val createdDate: Long,
                        @PrimaryKey(autoGenerate = true)
                        @ColumnInfo(name = COLUMN_ID) val id: Int)