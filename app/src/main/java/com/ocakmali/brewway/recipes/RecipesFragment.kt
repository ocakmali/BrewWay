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

package com.ocakmali.brewway.recipes

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_recipes.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipesFragment : BaseFragment() {

    private val viewModel: RecipesViewModel by viewModel()

    override fun layoutResId() = R.layout.fragment_recipes

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = RecipesAdapter()
        rcv_recipes.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        rcv_recipes.layoutManager = layoutManager
        rcv_recipes.addItemDecoration(DividerItemDecoration(rcv_recipes.context, layoutManager.orientation))

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        fab_new_recipe.setOnClickListener {
           findNavController().navigate(RecipesFragmentDirections.actionAddEditRecipe())
        }
    }
}