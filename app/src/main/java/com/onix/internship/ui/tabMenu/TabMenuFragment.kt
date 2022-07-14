package com.onix.internship.ui.tabMenu

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.CalendarDialogBinding
import com.onix.internship.databinding.TabMenuFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TabMenuFragment : BaseFragment<TabMenuFragmentBinding>(R.layout.tab_menu_fragment) {

    override val viewModel: TabMenuViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        //setup navController
        val navHost = childFragmentManager.findFragmentById(R.id.tabNavFragment) as NavHostFragment
        val navController = navHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        //round bottom navigation corners
        val radius = resources.getDimension(R.dimen.shape_radius)
        val shapeDrawable : MaterialShapeDrawable = binding.bottomNavigationView.background as MaterialShapeDrawable
        shapeDrawable.shapeAppearanceModel = shapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()

        binding.fabAddNewTask.setOnClickListener { showCalendarDialog() }

        return view
    }

    private fun showCalendarDialog(){
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.calendar_dialog, null)
        val binding = CalendarDialogBinding.bind(view)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val alert = builder.create()
        alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.cvCalendar.setOnDateChangeListener { _, year, mouth, day ->
            viewModel.setSelectedCalendarDate(year, mouth, day)
        }
        binding.btnCancel.setOnClickListener { alert.cancel() }
        binding.btnSave.setOnClickListener { showAddTaskFragment(alert, viewModel.getSelectedCalendarDate()) }

        alert.show()
    }

    private fun showAddTaskFragment(alert : AlertDialog, currentDate : String){
        alert.cancel()
        findNavController().navigate(TabMenuFragmentDirections.actionTabMenuFragmentToAddTaskFragment(currentDate))
    }

}