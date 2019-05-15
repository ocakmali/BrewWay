package com.ocakmali.brewway.addeditrecipe

import androidx.lifecycle.MutableLiveData
import com.crashlytics.android.Crashlytics
import com.ocakmali.brewway.SingleLiveEvent
import com.ocakmali.brewway.base.BaseViewModel
import com.ocakmali.brewway.datamodel.*
import com.ocakmali.brewway.exceptions.FetchItemException
import com.ocakmali.brewway.exceptions.SaveItemException
import com.ocakmali.domain.interactor.RecipeInterActor
import com.ocakmali.domain.model.RecipeAndTimestamps
import kotlinx.coroutines.launch

class AddEditRecipeViewModel(private val recipeInterActor: RecipeInterActor) : BaseViewModel() {

    private var recipeId = 0
    private var isDataInvalidated = false
    private var fetchedRecipeAndTimestamps: RecipeAndTimestampsView? = null
    private val isTimestampsUpdated
        get() = fetchedRecipeAndTimestamps?.timestampViews?.equals(timestamps.value)?.not()
                ?: timestamps.value.isNullOrEmpty().not()
    private val isRecipeUpdated: Boolean get() {
        with(recipe.value) {
            if (this?.title.isNullOrEmpty() && this?.equipment?.coffee == null
                    && this?.equipment?.coffeeMaker == null && this?.equipment?.grinder == null
                    && this?.equipment?.coffeeAmount == null && this?.equipment?.waterAmount == null
                    && this?.equipment?.waterTemperature == null && this?.id == 0
                    && fetchedRecipeAndTimestamps?.recipeView == null) {
                return false
            }
        }
        return fetchedRecipeAndTimestamps?.recipeView != recipe.value
    }
    val navigateUp =  SingleLiveEvent<Unit>()
    val recipe = MutableLiveData<RecipeView?>()
    val timestamps = MutableLiveData<List<TimestampView>?>()

    fun setRecipeId(id: Int) = launch {
        if (id == recipeId && isDataInvalidated.not()) {
            return@launch
        }

        recipeId = id

        if (recipeId == 0) {
            return@launch
        }

        recipeInterActor.getRecipeAndTimestampsById(recipeId)
                .result(::fetchRecipeFailed, ::fetchRecipeSucceed)
    }

    private fun fetchRecipeFailed(e: Exception) {
        Crashlytics.logException(e)
        postFailure(FetchItemException)
    }

    private fun fetchRecipeSucceed(recipeAndTimestamps: RecipeAndTimestamps) {
        val recipeAndTimestampsView = recipeAndTimestamps.toView()
        updateValues(recipeAndTimestampsView)
        isDataInvalidated = false
    }

    private fun updateValues(recipeAndTimestamps: RecipeAndTimestampsView?) {
        sortListAndPublishTimestamps(recipeAndTimestamps?.timestampViews)
        recipe.value = recipeAndTimestamps?.recipeView
        fetchedRecipeAndTimestamps = recipeAndTimestamps
    }

    fun shouldAddTimestamp(): Boolean {
        return timestamps.value?.size ?: 0 < TIMESTAMPS_MAX_SIZE
    }

    fun addTimestamp(time: Long, note: String) {
        val timestamp = TimestampView(time, note, recipeId)
        val list = timestamps.value?.plus(timestamp) ?: listOf(timestamp)
        sortListAndPublishTimestamps(list)
    }

    fun deleteTimestamp(timestamp: TimestampView) {
        val list = timestamps.value?.minus(timestamp)
        sortListAndPublishTimestamps(list)
    }

    fun updateTimestamp(old: TimestampView, timestamp: TimestampView) {
        if (old == timestamp) {
            return
        }

        val list = timestamps.value?.toMutableList()
        val index = list?.indexOf(old)
        if (index == null || index == -1) {
            return
        }

        list[index] = timestamp
        sortListAndPublishTimestamps(list)
    }

    private fun sortListAndPublishTimestamps(timestampList: List<TimestampView>?) {
        timestamps.value = timestampList?.sortedBy { it.time } ?: emptyList()
    }

    fun updateCoffee(coffeeView: CoffeeView?) {
        if (recipe.value?.equipment?.coffee == coffeeView) {
            return
        }
        recipe.value = recipe.value.copyRecipe(coffee = coffeeView)
    }

    private fun RecipeView?.copyRecipe(
            title: String? = this?.title,
            coffeeMaker: CoffeeMakerView? = this?.equipment?.coffeeMaker,
            coffee: CoffeeView? = this?.equipment?.coffee,
            grinder: GrinderView? = this?.equipment?.grinder,
            coffeeAmount: Int? = this?.equipment?.coffeeAmount,
            waterAmount: Int? = this?.equipment?.waterAmount,
            waterTemperature: Int? = this?.equipment?.waterTemperature,
            createdDate: Long = this?.createdDate ?: System.currentTimeMillis(),
            id: Int = this?.id ?: 0
    ): RecipeView {
        return RecipeView(
                title,
                RecipeView.Equipment(
                        coffeeMaker,
                        coffee,
                        grinder,
                        coffeeAmount,
                        waterAmount,
                        waterTemperature
                ),
                createdDate,
                id
        )
    }

    fun updateTitle(title: String?) {
        if (recipe.value?.title == title) {
            return
        }
        recipe.value = recipe.value.copyRecipe(title = title)
    }

    fun updateCoffeeMaker(coffeeMakerView: CoffeeMakerView?) {
        if (recipe.value?.equipment?.coffeeMaker == coffeeMakerView) {
            return
        }
        recipe.value = recipe.value.copyRecipe(coffeeMaker = coffeeMakerView)
    }

    fun updateGrinder(grinderView: GrinderView?) {
        if (recipe.value?.equipment?.grinder == grinderView) {
            return
        }
        recipe.value = recipe.value.copyRecipe(grinder = grinderView)
    }

    fun updateWaterTemperature(temperature: Int?) {
        if (recipe.value?.equipment?.waterTemperature == temperature) {
            return
        }
        recipe.value = recipe.value.copyRecipe(waterTemperature = temperature)
    }

    fun updateWaterAmount(amount: Int?) {
        if (recipe.value?.equipment?.waterAmount == amount) {
            return
        }
        recipe.value = recipe.value.copyRecipe(waterAmount = amount)
    }

    fun updateCoffeeAmount(amount: Int?) {
        if (recipe.value?.equipment?.coffeeAmount == amount) {
            return
        }
        recipe.value = recipe.value.copyRecipe(coffeeAmount = amount)
    }

    fun saveRecipe() = launch {
        val recipe = recipe.value?.toRecipe() ?: RecipeView.emptyRecipe()
        val timestampList = timestamps.value?.map { it.toTimestamp() }
        if (isRecipeUpdated || isTimestampsUpdated) {
            if (timestampList.isNullOrEmpty() || !isTimestampsUpdated) {
                recipeInterActor.addRecipe(recipe)
                        .result(::savingRecipeFailed, ::savingRecipeSucceed)
                return@launch
            }

            recipeInterActor.addRecipeAndTimestamps(recipe, timestampList)
                    .result(::savingRecipeFailed, ::savingRecipeSucceed)
        }
    }

    private fun savingRecipeFailed(exception: Exception) {
        Crashlytics.logException(exception)
        postFailure(SaveItemException)
    }

    fun shouldShowExitDialog(): Boolean {
        return isRecipeUpdated || isTimestampsUpdated
    }

    private fun savingRecipeSucceed() {
        setDataInvalidatedAndNavigate()
    }

    private fun setDataInvalidatedAndNavigate() {
        isDataInvalidated = true
        navigateUp.call()
        updateValues(null)
    }

    fun exitWithoutSaving() {
        setDataInvalidatedAndNavigate()
    }

    companion object {
        private const val TIMESTAMPS_MAX_SIZE = 20
    }
}

