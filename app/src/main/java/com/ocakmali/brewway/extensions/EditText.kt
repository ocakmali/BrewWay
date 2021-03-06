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

package com.ocakmali.brewway.extensions

import android.text.Editable
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import com.ocakmali.brewway.view.widget.LifecycleTextWatcher

fun EditText.setOnActionDoneClickListener(action: (TextView) -> Unit) {
    setOnEditorActionListener { view, actionId, _ ->
        return@setOnEditorActionListener when(actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                action.invoke(view)
                true
            }
            else -> false
        }
    }
}

fun EditText.addLifecycleTextWatcher(lifecycle: Lifecycle, action: (Editable) -> Unit) {
    val watcher = LifecycleTextWatcher(this) {
        action.invoke(it)
    }
    watcher.registerLifecycle(lifecycle)
}