package com.onix.internship.ui.translate

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.onix.internship.databinding.FragmentTranslateBinding

class TranslateFragment : BaseFragment<FragmentTranslateBinding>(R.layout.fragment_translate) {

    override val viewModel: TranslateViewModel by viewModel()
    val adapter = HistoryRVAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val dictArray = requireContext().resources.getStringArray(R.array.dictionaries)
        binding.tvDictionary.text = dictArray[0]

        binding.tvDictionary.setOnClickListener { changeDict(binding.tvDictionary, dictArray) }
        binding.btnFindWord.setOnClickListener { findWord(binding.cl) }

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        binding.rvHistory.layoutManager = layoutManager
        binding.rvHistory.adapter = adapter
        return view
    }

    override fun setObservers() {
        viewModel.history.observe(this){
            adapter.setContent(it)
        }
    }

    private fun changeDict(tvDict : TextView, dictArray : Array<out String>){
        val context = requireContext()
        val scroll = ScrollView(context)
        val rg= RadioGroup(context)

        dictArray.forEach { item ->
            val rb = RadioButton(context)
            rb.text = item

            rb.setOnClickListener {
                tvDict.text = item
            }

            rg.addView(rb)
        }
        scroll.addView(rg)

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.alert_title)
            .setView(scroll)
            .setPositiveButton(R.string.close_btn_text) { dialog, _ ->
                dialog.cancel()
            }

        val alert = builder.create()
        alert.show()
    }

    private fun findWord(cl : ConstraintLayout){
        val key = binding.etEnterWord.text.toString().lowercase()
        val value = viewModel.findWord(binding.tvDictionary.text.toString(), key)
        if(value != null){
            viewModel.updateHistory(key, value)
            val action = TranslateFragmentDirections
                .actionTranslateFragmentToResultFragment(key, viewModel.arrayListToString(value))
            findNavController().navigate(action)
        }
        else
            Snackbar.make(cl, R.string.translate_error, Snackbar.LENGTH_SHORT).show()
    }
}