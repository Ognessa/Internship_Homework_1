package com.onix.internship.ui.wifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.WifiFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class WifiFragment : BaseFragment<WifiFragmentBinding>(R.layout.wifi_fragment) {

    override val viewModel: WifiViewModel by viewModel()

    lateinit var wifiManager : WifiManager
    lateinit var constraintLayout: ConstraintLayout
    val list : ArrayList<WifiData> = arrayListOf()
    val MAX_LIST_SIZE = 16

    val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val wifiScanList : List<ScanResult> = wifiManager.scanResults
            updateList(wifiScanList)
            createItems(list, requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        constraintLayout = binding.cl

        wifiManager = requireContext().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.startScan()

        return view
    }

    override fun onResume() {
        requireContext().registerReceiver(wifiReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        super.onResume()
    }

    override fun onPause() {
        requireContext().unregisterReceiver(wifiReceiver)
        super.onPause()
    }

    fun updateList(list : List<ScanResult>){
        val tempList = arrayListOf<WifiData>()

        if(list.size <= MAX_LIST_SIZE){
            list.forEach { it ->
                val index = list.indexOf(it)
                tempList.add(WifiData(it.SSID, abs(it.level), 360.0f / MAX_LIST_SIZE * index))
            }
        } else {
            for (i in 0..MAX_LIST_SIZE)
                tempList.add(WifiData(list[i].SSID, abs(list[i].level), 360.0f / MAX_LIST_SIZE * i))
        }

        this.list.clear()
        this.list.addAll(tempList)
    }

    fun createItems(list : ArrayList<WifiData>, context: Context){
        list.forEach { it ->
            val textView = TextView(context)
            textView.text = it.title
            textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

            constraintLayout.addView(textView)

            val params = textView.layoutParams as ConstraintLayout.LayoutParams
            params.circleConstraint = R.id.center
            params.circleRadius = it.level * 4
            params.circleAngle = it.angle
            textView.layoutParams = params
        }

        constraintLayout.invalidate()
    }
}