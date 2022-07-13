package com.onix.internship.ui.addTask

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.AddTaskFragmentBinding
import com.onix.internship.databinding.CalendarDialogBinding
import com.onix.internship.databinding.EditTimeDialogBinding
import com.onix.internship.databinding.NewTagDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddTaskFragment : BaseFragment<AddTaskFragmentBinding>(R.layout.add_task_fragment){

    override val viewModel: AddTaskViewModel by viewModel()
    private val args : AddTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.tvPlanDate.text = args.currentDate

        binding.tvPlanDate.setOnClickListener { showCalendarDialog() }
        binding.tvFromTime.setOnClickListener { showEditTimeDialog() }
        binding.tvToTime.setOnClickListener { showEditTimeDialog() }
        binding.btnCreateTask.setOnClickListener { createTask() }
        binding.btnAddNewTag.setOnClickListener { showNewTagDialog() }

        NavigationUI.setupActionBarWithNavController(requireActivity() as AppCompatActivity, findNavController())

        registerForContextMenu(binding.ivDescriptionContextMenu)

        return view
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.format_align_menu, menu)
    }

    private fun showCalendarDialog(){
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.calendar_dialog, null)
        val binding = CalendarDialogBinding.bind(view)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val alert = builder.create()
        alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.cvCalendar.setOnDateChangeListener { calendarView, year, mouth, day ->
            //TODO
        }
        binding.btnCansel.setOnClickListener { alert.cancel() }
        binding.btnSave.setOnClickListener { /*TODO*/ }

        alert.show()
    }

    private fun showNewTagDialog(){
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.new_tag_dialog, null)
        val binding = NewTagDialogBinding.bind(view)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val alert = builder.create()
        alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCansel.setOnClickListener { alert.cancel() }
        binding.btnSave.setOnClickListener { /*TODO*/ }

        alert.show()
    }

    private fun showEditTimeDialog(){
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.edit_time_dialog, null)
        val binding = EditTimeDialogBinding.bind(view)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val alert = builder.create()
        alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCansel.setOnClickListener { alert.cancel() }
        binding.btnSave.setOnClickListener { /*TODO*/ }

        alert.show()
    }

    private fun createTask(){
        findNavController().navigate(AddTaskFragmentDirections.actionAddTaskFragmentToTabMenuFragment())
    }

}