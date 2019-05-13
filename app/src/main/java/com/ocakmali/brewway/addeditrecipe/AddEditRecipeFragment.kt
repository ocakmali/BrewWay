package com.ocakmali.brewway.addeditrecipe

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ocakmali.brewway.R
import com.ocakmali.brewway.base.BaseFragment
import com.ocakmali.brewway.datamodel.TimestampView
import com.ocakmali.brewway.extensions.setOnActionDoneClickListener
import com.ocakmali.brewway.extensions.toast
import kotlinx.android.synthetic.main.fragment_add_edit_recipe.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddEditRecipeFragment : BaseFragment() {

    private val viewModel by sharedViewModel<AddEditRecipeViewModel>()
    private val args by navArgs<AddEditRecipeFragmentArgs>()

    override fun layoutResId(): Int = R.layout.fragment_add_edit_recipe

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.setRecipeId(args.recipeId)

        requireActivity()
                .onBackPressedDispatcher
                .addCallback(viewLifecycleOwner) {
                    showExitDialog()
                }

        initTitleEditText()
        initCoffeeTextView()
        initCoffeeMakerTextView()
        initGrinderTextView()
        initCoffeeAmountTextView()
        initWaterAmountTextView()
        initWaterTemperatureTextView()
        initRecyclerView()
        initToolbarButtons()

        viewModel.navigateUp.observe(viewLifecycleOwner, Observer {
            val inputManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            val view = requireActivity().currentFocus ?: View(requireActivity())
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
            findNavController().navigateUp()
        })

        tv_add_edit_timestamp.setOnClickListener {
            if (viewModel.shouldAddTimestamp()) {
                findNavController()
                        .navigate(
                                AddEditRecipeFragmentDirections
                                        .actionToTimestampDialog()
                )
            } else {
                toast(R.string.timestamps_size_exceed)
            }
        }

        viewModel.recipe.observe(viewLifecycleOwner, Observer { recipe ->
            if (recipe == null) {
                return@Observer
            }
            with(recipe.equipment) {
                tv_coffee.updateTextAndSetRightDrawableClickListener(
                        coffee?.name,
                        R.string.coffee) { viewModel.updateCoffee(null) }

                tv_coffee_maker.updateTextAndSetRightDrawableClickListener(
                        coffeeMaker?.name,
                        R.string.coffee_maker) { viewModel.updateCoffeeMaker(null) }

                tv_water_temp.updateTextAndSetRightDrawableClickListener(
                        waterTemperature?.toString(),
                        R.string.temp) { viewModel.updateWaterTemperature(null) }

                tv_water_amount.updateTextAndSetRightDrawableClickListener(
                        waterAmount?.toString(),
                        R.string.amt) { viewModel.updateWaterAmount(null) }

                tv_coffee_amount.updateTextAndSetRightDrawableClickListener(
                        coffeeAmount?.toString(),
                        R.string.amt) { viewModel.updateCoffeeAmount(null) }

                tv_grinder.updateTextAndSetRightDrawableClickListener(
                        grinder?.name,
                        R.string.grinder) { viewModel.updateGrinder(null) }
            }
            if (et_title.text?.toString() != recipe.title) {
                et_title.setText(recipe.title)
            }
        })
    }

    private fun initTitleEditText() {
        et_title.setOnActionDoneClickListener {
            viewModel.updateTitle(it.text.toString())
        }
    }

    private fun initToolbarButtons() {
        btn_exit.setOnClickListener {
            showExitDialog()
        }

        btn_save.setOnClickListener {
            viewModel.saveRecipe()
        }
    }

    private fun showExitDialog() {
        if (viewModel.shouldShowExitDialog()) {
            findNavController().navigate(AddEditRecipeFragmentDirections.actionToExitDialog())
            return
        }
        findNavController().navigateUp()
    }

    private fun initRecyclerView() {
        val adapter = AddEditTimestampAdapter(object : AddEditTimestampAdapter.ActionListener {
            override fun onTextClick(timestamp: TimestampView) {
                findNavController()
                        .navigate(AddEditRecipeFragmentDirections.actionToTimestampDialog(timestamp))
            }

            override fun onDeleteClick(item: TimestampView) {
                viewModel.deleteTimestamp(item)
            }
        })

        rcv_timestamps.adapter = adapter
        rcv_timestamps.layoutManager = LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL,
                false)
        rcv_timestamps.addItemDecoration(DividerItemDecoration(requireActivity(),
                RecyclerView.VERTICAL))


        viewModel.timestamps.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun initWaterTemperatureTextView() {
        tv_water_temp.setOnClickListener {
            findNavController()
                    .navigate(AddEditRecipeFragmentDirections.actionToWaterTemperatureDialog())
        }
    }

    private fun initWaterAmountTextView() {
        tv_water_amount.setOnClickListener {
            findNavController()
                    .navigate(AddEditRecipeFragmentDirections.actionToWaterAmountDialog())
        }
    }

    private fun initCoffeeAmountTextView() {
        tv_coffee_amount.setOnClickListener {
            findNavController()
                    .navigate(AddEditRecipeFragmentDirections.actionToCoffeeAmountDialog())
        }
    }

    private fun initGrinderTextView() {
        tv_grinder.setOnClickListener {
            findNavController().navigate(AddEditRecipeFragmentDirections.actionToSearchGrinder())
        }
    }

    private fun initCoffeeMakerTextView() {
        tv_coffee_maker.setOnClickListener {
           findNavController().navigate(AddEditRecipeFragmentDirections.actionToSearchCoffeeMaker())
        }
    }

    private fun initCoffeeTextView() {
        tv_coffee.setOnClickListener {
            findNavController().navigate(AddEditRecipeFragmentDirections.actionToSearchCoffee())
        }
    }

    private fun TextView.updateTextAndSetRightDrawableClickListener(
            text: String?,
            @StringRes defaultTextRes: Int,
            rightDrawableClick: () -> Unit) {

        if (text == getText()) {
            return
        }

        if (text == null) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawablesRelative[0],
                    null,
                    null,
                    null)
            setText(defaultTextRes)
            setOnTouchListener(null)
            return
        }

        setText(text)
        setCompoundDrawablesRelativeWithIntrinsicBounds(compoundDrawablesRelative[0],
                null,
                resources.getDrawable(R.drawable.ic_close, null),
                null)
        setOnTouchListener { _, event ->
            if(event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (right - compoundDrawablesRelative[2].bounds.width() - paddingRight)) {
                    rightDrawableClick.invoke()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }
}
