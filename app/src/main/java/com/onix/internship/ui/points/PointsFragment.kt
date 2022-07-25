package com.onix.internship.ui.points

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.arch.BaseFragment
import com.onix.internship.R
import com.onix.internship.databinding.PointsFragmentBinding
import com.onix.internship.helpers.JSONHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class PointsFragment : BaseFragment<PointsFragmentBinding>(R.layout.points_fragment){
    override val viewModel: PointsViewModel by viewModel()
    private lateinit var adapter : PointsRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        adapter = PointsRVAdapter(requireContext())
        adapter.setContent(viewModel.pointList)
        binding.rwPointsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rwPointsList.adapter = adapter

        binding.fabAddPoint.setOnClickListener { getNewPoint() }

        return view
    }

    private fun getNewPoint(){
        val clipBoardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val copiedString = clipBoardManager.primaryClip?.getItemAt(0)?.text?.toString()

        try{
             copiedString?.let {
                 val point = JSONHelper().parseJSONToPoint(it)
                 viewModel.updatePointsList(point)
                 adapter.setContent(viewModel.pointList)
             }
        }catch (e : Exception){
            Toast.makeText(requireContext(), R.string.add_point_exception, Toast.LENGTH_SHORT).show()
        }
    }

}