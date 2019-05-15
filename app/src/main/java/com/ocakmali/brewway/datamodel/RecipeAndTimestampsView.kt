package com.ocakmali.brewway.datamodel

import com.ocakmali.domain.model.RecipeAndTimestamps

fun RecipeAndTimestamps.toView() = RecipeAndTimestampsView(recipe.toView(),
        timestamps.map { it.toView() })

data class RecipeAndTimestampsView(val recipeView: RecipeView,
                                   val timestampViews: List<TimestampView>)