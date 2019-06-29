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

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.ocakmali.brewway.addeditrecipe.AddEditRecipeViewModel
import com.ocakmali.brewway.addeditrecipe.coffee.SearchCoffeeViewModel
import com.ocakmali.brewway.addeditrecipe.coffeemaker.SearchCoffeeMakerViewModel
import com.ocakmali.brewway.addeditrecipe.grinder.SearchGrinderViewModel
import com.ocakmali.brewway.equipments.coffeemakers.CoffeeMakersViewModel
import com.ocakmali.brewway.equipments.coffees.CoffeesViewModel
import com.ocakmali.brewway.equipments.grinders.GrindersViewModel
import com.ocakmali.brewway.recipes.RecipesViewModel
import com.ocakmali.brewway.timer.Timer
import com.ocakmali.brewway.timer.notification.ITimerNotificationProvider
import com.ocakmali.brewway.timer.TimerController
import com.ocakmali.brewway.timer.TimerFragment
import com.ocakmali.brewway.timer.notification.TimerNotificationController
import com.ocakmali.brewway.timer.notification.TimerNotificationProvider
import com.ocakmali.brewway.timer.service.TimerService
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    //ViewModel
    viewModel { CoffeesViewModel(get()) }
    viewModel { CoffeeMakersViewModel(get()) }
    viewModel { GrindersViewModel(get()) }
    viewModel { RecipesViewModel(get()) }
    viewModel { AddEditRecipeViewModel(get()) }
    viewModel { SearchCoffeeViewModel(get()) }
    viewModel { SearchCoffeeMakerViewModel(get()) }
    viewModel { SearchGrinderViewModel(get()) }

    factory<ITimerNotificationProvider>(named<TimerNotificationProvider>()) { (context: Context) ->
        TimerNotificationProvider(context)
    }

    scope(named<TimerFragment>()) {
        scoped { (id: Long, context: Context, lifecycleOwner: LifecycleOwner) ->
            TimerController(id, context, lifecycleOwner,
                get(named<TimerNotificationProvider>()) { parametersOf(context) },
                get()
            )
        }
    }

    scope(named<TimerService>()) {
        scoped { (service: TimerService, scope: CoroutineScope, context: Context) ->
            TimerNotificationController(
                service,
                scope,
                context
            )
        }

        scoped { (scope: CoroutineScope) -> Timer(get(), scope) }
    }
}