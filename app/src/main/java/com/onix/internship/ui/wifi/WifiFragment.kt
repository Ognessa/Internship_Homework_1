package com.onix.internship.ui.wifi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.WifiFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WifiFragment : BaseFragment<WifiFragmentBinding>(R.layout.wifi_fragment) {

    override val viewModel: WifiViewModel by viewModel()

    lateinit var constraintLayout: ConstraintLayout
    val textViewList : ArrayList<TextView> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        constraintLayout = binding.cl
        return view
    }

    override fun setObservers() {
        viewModel.dataList.observe(this){
            clearScreen()
            createItems(it, requireContext())
        }
    }

    fun createItems(list : ArrayList<WifiData>, context: Context){
        list.forEach { item ->
            val textView = TextView(context)
            textView.text = item.shortTitle
            textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

            constraintLayout.addView(textView)

            val params = textView.layoutParams as ConstraintLayout.LayoutParams
            params.circleConstraint = R.id.center
            params.circleRadius = item.radius * 4
            params.circleAngle = item.angle
            textView.layoutParams = params

            textView.setOnClickListener {
                val action = WifiFragmentDirections.actionWifiFragmentToMoreInfoFragment(item.scanResult)
                findNavController().navigate(action)
            }
            textViewList.add(textView)
        }
    }

    fun clearScreen(){
        textViewList.forEach { it ->
            constraintLayout.removeView(it)
        }
        textViewList.clear()
    }
}






