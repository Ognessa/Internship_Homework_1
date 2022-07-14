package com.onix.internship.ui.addTask

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.*
import com.onix.internship.objects.Tag
import com.onix.internship.objects.TimeType
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

        binding.viewModel = viewModel
        viewModel.setSelectedCalendarDate(args.currentDate)

        binding.tvPlanDate.setOnClickListener { showCalendarDialog() }
        binding.tvFromTime.setOnClickListener { showEditTimeDialog(TimeType.TIME_FROM) }
        binding.tvToTime.setOnClickListener { showEditTimeDialog(TimeType.TIME_UNTIL) }
        binding.btnCreateTask.setOnClickListener { createTask() }
        binding.btnAddNewTag.setOnClickListener { showNewTagDialog() }

        NavigationUI.setupActionBarWithNavController(requireActivity() as AppCompatActivity, findNavController())

        registerForContextMenu(binding.ivDescriptionContextMenu)

        return view
    }

    override fun setObservers() {
        viewModel.tagList.observe(this){
            binding.llTagContainer.removeAllViews()
            it.forEach { item ->
                binding.llTagContainer.addView(createTagTextView(item))
            }
        }
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
        var selectedDate = ""
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.calendar_dialog, null)
        val binding = CalendarDialogBinding.bind(view)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val alert = builder.create()
        alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.cvCalendar.setOnDateChangeListener { _, year, mouth, day ->
            selectedDate = viewModel.getSelectedCalendarDate(year, mouth, day)
        }
        binding.btnCancel.setOnClickListener { alert.cancel() }
        binding.btnSave.setOnClickListener {
            alert.cancel()
            if(selectedDate.isNotEmpty()) viewModel.setSelectedCalendarDate(selectedDate)
        }

        alert.show()
    }

    private fun showNewTagDialog(){
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.new_tag_dialog, null)
        val binding = NewTagDialogBinding.bind(view)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val alert = builder.create()
        alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCancel.setOnClickListener { alert.cancel() }
        binding.btnSave.setOnClickListener {
            val text = binding.etAddTag.text.toString()
            if(text.isNotEmpty()){
                val tag = Tag(text)
                tag.randomColor()
                viewModel.addNewTag(tag)
                alert.cancel()
            }
            else{
                Toast.makeText(requireContext(), "Tag cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        alert.show()
    }

    private fun createTagTextView(tag : Tag): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.tag_item, null)
        val binding = TagItemBinding.bind(view)
        val textView = binding.tvTagText

        textView.setTextColor(tag.textColor)
        textView.text = tag.tag

        val radius = resources.getDimension(R.dimen.shape_radius)
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()

        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        shapeDrawable.fillColor = ColorStateList.valueOf(tag.backColor)

        ViewCompat.setBackground(textView, shapeDrawable)

        return view
    }

    private fun showEditTimeDialog(timeType: TimeType){
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.edit_time_dialog, null)
        val binding = EditTimeDialogBinding.bind(view)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val alert = builder.create()
        alert.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCancel.setOnClickListener { alert.cancel() }
        binding.btnSave.setOnClickListener {
            val hours = binding.npHourSpinner.value
            val minutes = binding.npMinutesSpinner.value
            if(timeType == TimeType.TIME_FROM) viewModel.updateTimeFrom(hours, minutes)
            else viewModel.updateTimeUntil(hours, minutes)
            alert.cancel()
        }

        alert.show()
    }

    private fun createTask(){
        findNavController().navigate(AddTaskFragmentDirections.actionAddTaskFragmentToTabMenuFragment())
    }

}