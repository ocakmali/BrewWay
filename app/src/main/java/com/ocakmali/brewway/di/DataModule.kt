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

package com.ocakmali.brewway.di

import androidx.room.Room
import com.ocakmali.brewway.BuildConfig
import com.ocakmali.data.db.BrewWayDatabase
import com.ocakmali.data.repository.*
import com.ocakmali.domain.repository.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    //Room
    single {
        Room.databaseBuilder(androidApplication(),
                BrewWayDatabase::class.java,
                BuildConfig.APPLICATION_ID)
                .fallbackToDestructiveMigration()
                .build()
    }
    factory { get<BrewWayDatabase>().coffeeDao() }
    factory { get<BrewWayDatabase>().coffeeMakerDao() }
    factory { get<BrewWayDatabase>().grinderDao() }
    factory { get<BrewWayDatabase>().recipeDao() }
    factory { get<BrewWayDatabase>().recipeTimestampDao() }

    //Repository
    single<ICoffeeRepository> { CoffeeRepository(get(), get()) }
    single<ICoffeeMakerRepository> { CoffeeMakerRepository(get(), get()) }
    single<IGrinderRepository> { GrinderRepository(get(), get()) }
    single<IRecipeRepository> { RecipeRepository(get(), get(), get(), get()) }
    single<IRecipeTimestampRepository> { RecipeTimestampRepository(get(), get()) }
}
