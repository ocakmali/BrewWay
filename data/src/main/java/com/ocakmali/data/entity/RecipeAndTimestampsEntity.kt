package com.ocakmali.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.ocakmali.data.db.RecipeConstants
import com.ocakmali.data.db.RecipeTimestampConstants
import com.ocakmali.domain.model.RecipeAndTimestamps

fun RecipeAndTimestampsEntity.toRecipeAndTimestamp() = RecipeAndTimestamps(recipeEntity.toRecipe(),
        timestamps.map { it.toRecipeTimestamp() })

data class RecipeAndTimestampsEntity(@Embedded val recipeEntity: RecipeAndEquipments,
                                     @Relation(parentColumn = RecipeConstants.COLUMN_ID,
                                             entityColumn = RecipeTimestampConstants.COLUMN_RECIPE_ID)
                                     val timestamps: List<RecipeTimestampEntity> = emptyList())