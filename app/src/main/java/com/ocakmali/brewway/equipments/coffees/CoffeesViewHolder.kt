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

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.ocakmali.brewway.R
import com.ocakmali.brewway.datamodel.CoffeeView
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener


class CoffeesViewHolder(view: View, private val listener: CoffeesActionListener) : RecyclerView.ViewHolder(view){
    private val title: EditText = view.findViewById(R.id.tv_coffee)
    private val delete: ImageButton = view.findViewById(R.id.btn_delete)

    fun bind(coffeeView: CoffeeView) {
        title.setText(coffeeView.name)

        title.setOnActionDoneClickListener {
            listener.onDoneClick(coffeeView.copy(name = it.text.toString()))
            it.clearFocus()
        }

        delete.setOnClickListener { listener.onDeleteClick(coffeeView) }
    }
}