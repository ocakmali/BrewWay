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

import androidx.room.Room
import com.example.data.db.BrewWayDatabase
import com.example.data.repository.CoffeeMakerRepository
import com.example.data.repository.CoffeeRepository
import com.ocakmali.brewway.BuildConfig
import com.ocakmali.domain.repository.ICoffeeMakerRepository
import com.ocakmali.domain.repository.ICoffeeRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

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

    //Repository
    single<ICoffeeRepository> { CoffeeRepository(get(), get()) }
    single<ICoffeeMakerRepository> { CoffeeMakerRepository(get(), get()) }
}
