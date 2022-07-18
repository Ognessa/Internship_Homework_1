package com.onix.internship.ui.askClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.databinding.AskClassFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskClassFragment : BaseFragment<AskClassFragmentBinding>(R.layout.ask_class_fragment){
    override val viewModel: AskClassViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        createClassesList(binding.rgClasses)
        binding.btnPrev.setOnClickListener { movePrev() }
        binding.btnNext.setOnClickListener { moveNext(binding.rgClasses) }

        return view
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
    }

    private fun moveNext(rgClasses: RadioGroup){
        if (rgClasses.checkedRadioButtonId != -1){
            viewModel.saveClassToPref(rgClasses.checkedRadioButtonId)
            findNavController().navigate(AskClassFragmentDirections.actionAskClassFragmentToTabMenuFragment())
        }
        else{
            Toast.makeText(requireContext(), "Select your class", Toast.LENGTH_SHORT).show()
        }
    }

    private fun movePrev(){
        findNavController().navigate(AskClassFragmentDirections.actionAskClassFragmentToAskLevelFragment())
    }

}