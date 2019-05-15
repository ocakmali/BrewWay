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

package com.ocakmali.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ocakmali.data.BaseDao
import com.ocakmali.data.db.RecipeConstants
import com.ocakmali.data.db.RecipeConstants.COLUMN_CREATED_DATE
import com.ocakmali.data.entity.RecipeAndEquipments
import com.ocakmali.data.entity.RecipeAndTimestampsEntity
import com.ocakmali.data.entity.RecipeEntity

@Dao
abstract class RecipeDao : BaseDao<RecipeEntity> {

    @Query(""" SELECT * FROM RecipeAndEquipments ORDER BY $COLUMN_CREATED_DATE DESC""")
    abstract fun loadRecipes(): DataSource.Factory<Int, RecipeAndEquipments>

    @Insert(onConflict = REPLACE)
    abstract fun addRecipe(recipeEntity: RecipeEntity): Long

    @Query("SELECT * FROM RecipeAndEquipments WHERE ${RecipeConstants.COLUMN_ID} == :recipeId")
    abstract fun getRecipeAndTimestampsById(recipeId: Int): RecipeAndTimestampsEntity
}