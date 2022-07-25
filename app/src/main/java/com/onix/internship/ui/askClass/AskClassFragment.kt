package com.onix.internship.ui.askClass

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.arch.ext.navigate
import com.onix.internship.databinding.AskClassFragmentBinding
import com.onix.internship.objects.NavigateDirection
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskClassFragment : BaseFragment<AskClassFragmentBinding>(R.layout.ask_class_fragment){
    override val viewModel: AskClassViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.askClassViewModel = viewModel
        binding.navDirectionEnum = NavigateDirection.PREV

        createClassesList(binding.rgClasses)
        return view
    }

    override fun setObservers() {
        viewModel.move.observe(this){
            when(it){
                NavigateDirection.NEXT -> navigate(AskClassFragmentDirections.actionAskClassFragmentToTabMenuFragment())
                NavigateDirection.PREV -> navigate(AskClassFragmentDirections.actionAskClassFragmentToAskLevelFragment())
                else -> {}
            }
        }
    }

    private fun createClassesList(rgClasses : RadioGroup){
        rgClasses.removeAllViews()

        val list = resources.getStringArray(R.array.class_titles)
        list.forEach {
            val rb = RadioButton(requireContext())
            rb.id = list.indexOf(it)
            rb.text = it
            rgClasses.addView(rb)
        }
        rgClasses.setOnCheckedChangeListener { radioGroup, i ->
            Log.d("DEBUG", "Checked")
            viewModel.selectClass(i) }
    }

}