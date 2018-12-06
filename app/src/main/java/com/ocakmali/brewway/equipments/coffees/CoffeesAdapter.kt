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

package com.ocakmali.brewway.equipments.coffees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BasePagedListAdapter
import com.ocakmali.brewway.datamodel.CoffeeView

class CoffeesAdapter(private val listener: CoffeesActionListener)
    : BasePagedListAdapter<CoffeeView>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_equipments, parent, false)
        return CoffeesViewHolder(listener, view)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<CoffeeView>(){
            override fun areItemsTheSame(oldItem: CoffeeView, newItem: CoffeeView) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CoffeeView, newItem: CoffeeView) = oldItem == newItem
        }
    }
}