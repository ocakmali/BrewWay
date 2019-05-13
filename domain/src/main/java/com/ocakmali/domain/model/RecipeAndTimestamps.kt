package com.ocakmali.domain.model

class RecipeAndTimestamps(val recipe: Recipe,
                          val timestamps: List<RecipeTimestamp> = emptyList())
