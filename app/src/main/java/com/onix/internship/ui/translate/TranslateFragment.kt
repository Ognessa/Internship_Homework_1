package com.onix.internship.ui.translate

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.data.HistoryItem
import com.onix.internship.databinding.FragmentTranslateBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TranslateFragment : BaseFragment<FragmentTranslateBinding>(R.layout.fragment_translate) {

    override val viewModel: TranslateViewModel by viewModel()
    val adapter = HistoryRVAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val dictArray = viewModel.getDictsTitles()

        binding.tvDictionary.setOnClickListener { changeDictDialog(dictArray) }
        binding.btnFindWord.setOnClickListener { findWord(binding) }

        //adjust RecyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvHistory.layoutManager = layoutManager
        binding.rvHistory.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.rvHistory.adapter = adapter

        return view
    }

    override fun setObservers() {
        //observe history list
        viewModel.history.observe(this){
            adapter.setContent(it)
        }
        //observe directory title changed
        viewModel.currentDict.observe(this){
            binding.tvDictionary.text = it
        }
    }

    //show dialog with dictionaries list
    private fun changeDictDialog(dictArray : ArrayList<String>){
        val context = requireContext()
        val scroll = ScrollView(context)
        val rg= RadioGroup(context)

        dictArray.forEach { item ->
            val rb = RadioButton(context)
            rb.text = item
            rg.addView(rb)
            if(viewModel.currentDict.value == item) rb.isChecked = true
            rb.setOnClickListener { viewModel.changeDict(item) }
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

    private fun findWord(binding: FragmentTranslateBinding){
        val key = binding.etEnterWord.text.toString().lowercase()
        val value = viewModel.findWord(binding.tvDictionary.text.toString(), key)

        if(value.isNotEmpty()){
            viewModel.updateHistory(HistoryItem(binding.tvDictionary.text.toString(), key, value.first()))
            val action = TranslateFragmentDirections
                .actionTranslateFragmentToResultFragment(key, value)
            findNavController().navigate(action)
        }
        else Snackbar.make(binding.cl, R.string.translate_error, Snackbar.LENGTH_SHORT).show()
    }
}