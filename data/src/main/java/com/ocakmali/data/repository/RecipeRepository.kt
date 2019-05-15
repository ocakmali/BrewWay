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

package com.ocakmali.data.repository

import com.ocakmali.common.DispatchersProvider
import com.ocakmali.common.Result
import com.ocakmali.data.dao.RecipeDao
import com.ocakmali.data.dao.RecipeTimestampDao
import com.ocakmali.data.db.BrewWayDatabase
import com.ocakmali.data.entity.toEntity
import com.ocakmali.data.entity.toRecipe
import com.ocakmali.data.entity.toRecipeAndTimestamp
import com.ocakmali.domain.model.Recipe
import com.ocakmali.domain.model.RecipeTimestamp
import com.ocakmali.domain.repository.IRecipeRepository
import kotlinx.coroutines.withContext

class RecipeRepository(private val recipeDao: RecipeDao,
                       private val timestampDao: RecipeTimestampDao,
                       private val db: BrewWayDatabase,
                       private val dispatchers: DispatchersProvider) : IRecipeRepository {

    override fun loadRecipes() = recipeDao.loadRecipes().map { it.toRecipe() }

    override suspend fun addRecipe(recipe: Recipe) = withContext(dispatchers.io) {
        Result.value { recipeDao.insert(recipe.toEntity()) }
    }

    override suspend fun getRecipeAndTimestampsById(recipeId: Int) = withContext(dispatchers.io) {
        Result.value { recipeDao.getRecipeAndTimestampsById(recipeId).toRecipeAndTimestamp() }
    }

    override suspend fun addRecipeAndTimestamps(recipe: Recipe, timestamps: List<RecipeTimestamp>) = withContext(dispatchers.io) {
        Result.value {
            db.runInTransaction {
                val recipeId: Long = recipeDao.addRecipe(recipe.toEntity())
                timestampDao.insert(timestamps.map {
                    it.copy(recipeId = recipeId.toInt()).toEntity()
                })
            }
        }
    }

    override suspend fun addRecipes(recipes: List<Recipe>) = withContext(dispatchers.io) {
        Result.value { recipeDao.insert(recipes.map { it.toEntity() }) }
    }

    override suspend fun deleteRecipe(recipe: Recipe) = withContext(dispatchers.io) {
        Result.value { recipeDao.delete(recipe.toEntity()) }
    }
}