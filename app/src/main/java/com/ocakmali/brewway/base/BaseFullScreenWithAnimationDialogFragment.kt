package com.ocakmali.brewway.base

import android.os.Bundle
import com.ocakmali.brewway.R

abstract class BaseFullScreenWithAnimationDialogFragment : BaseFullScreenDialogFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setWindowAnimations(R.style.dialog_animation_slide)
    }
}