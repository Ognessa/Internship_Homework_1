package com.onix.internship.ui.moreinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onix.internship.R

class MoreInfoFragment : Fragment() {

    companion object {
        fun newInstance() = MoreInfoFragment()
    }

    private lateinit var viewModel: MoreInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.more_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoreInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}