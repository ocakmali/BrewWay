/*
 * MIT License
 *
 * Copyright (c) 2018 Mehmet Ali Ocak
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

package com.ocakmali.brewway.di

import com.ocakmali.brewway.addeditrecipe.AddEditRecipeViewModel
import com.ocakmali.brewway.addeditrecipe.coffee.SearchCoffeeViewModel
import com.ocakmali.brewway.addeditrecipe.coffeemaker.SearchCoffeeMakerViewModel
import com.ocakmali.brewway.addeditrecipe.grinder.SearchGrinderViewModel
import com.ocakmali.brewway.equipments.coffeemakers.CoffeeMakersViewModel
import com.ocakmali.brewway.equipments.coffees.CoffeesViewModel
import com.ocakmali.brewway.equipments.grinders.GrindersViewModel
import com.ocakmali.brewway.recipes.RecipesViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    //ViewModel
    viewModel { CoffeesViewModel(get(), get()) }
    viewModel { CoffeeMakersViewModel(get(), get()) }
    viewModel { GrindersViewModel(get(), get()) }
    viewModel { RecipesViewModel(get(), get()) }
    viewModel<AddEditRecipeViewModel>()
    viewModel<SearchCoffeeViewModel>()
    viewModel<SearchCoffeeMakerViewModel>()
    viewModel<SearchGrinderViewModel>()
}