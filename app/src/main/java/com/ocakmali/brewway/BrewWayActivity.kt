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

package com.ocakmali.brewway

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_brew_way.*

class BrewWayActivity : AppCompatActivity() {

    private val navController:NavController  by lazy { findNavController(R.id.nav_host) }

    private var navigationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when(destination.id) {
            R.id.dest_recipes,
            R.id.dest_timer,
            R.id.dest_equipment -> bottomNavVisible(true)
            else -> bottomNavVisible(false)
        }
    }

    private fun bottomNavVisible(visibility: Boolean) {
        if(visibility) {
            if (bottom_nav.visibility != View.VISIBLE) {
                TransitionManager.beginDelayedTransition(main_container)
                bottom_nav.visibility = View.VISIBLE
                app_bar.visibility = View.VISIBLE
            }
        } else {
            if (bottom_nav.visibility != View.GONE) {
                TransitionManager.beginDelayedTransition(main_container)
                bottom_nav.visibility = View.GONE
                app_bar.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brew_way)
        setupToolbar(navController)
        setupBottomNav(navController)
    }

    private fun setupToolbar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.dest_recipes,
                R.id.dest_timer,
                R.id.dest_equipment)
        )
        toolbar.setupWithNavController(navController,appBarConfiguration)
    }

    private fun setupBottomNav(navController: NavController) {
        bottom_nav?.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        navController.addOnDestinationChangedListener(navigationListener)
    }

    override fun onStop() {
        super.onStop()
        navController.removeOnDestinationChangedListener(navigationListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
