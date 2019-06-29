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

package com.ocakmali.brewway.timer

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseFragment
import com.ocakmali.brewway.extensions.toast
import kotlinx.android.synthetic.main.fragment_timer.*
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class TimerFragment : BaseFragment() {

    private val timerController: TimerController by currentScope.inject {
            parametersOf(0L, requireContext(), viewLifecycleOwner)
    }

    override fun layoutResId(): Int  = R.layout.fragment_timer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timerController.time.observe(viewLifecycleOwner, Observer { time ->
            text_time.text = time
        })

        btn_start.setOnClickListener {
            if (timerController.startServiceIfNotStarted().not()) {
                toast(R.string.cant_start_timer)
            }
        }

        btn_stop.setOnClickListener {
            timerController.stop()
        }
    }
}