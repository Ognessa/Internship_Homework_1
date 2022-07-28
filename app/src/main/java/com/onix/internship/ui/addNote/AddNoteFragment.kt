package com.onix.internship.ui.addNote

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.navArgs
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.arch.ext.navigateToPrevious
import com.onix.internship.databinding.AddNoteFragmentBinding
import com.onix.internship.objects.getColor
import com.onix.internship.objects.getColorsList
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNoteFragment : BaseFragment<AddNoteFragmentBinding>(R.layout.add_note_fragment) {

    override val viewModel: AddNoteViewModel by viewModel()
    private val args : AddNoteFragmentArgs by navArgs()

    private val colorViewList = mutableListOf<View>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        setupToolbar(binding.toolbar)
        createColorMenu(binding.llColorsList)

        if (viewModel.note.value == null)
            viewModel.initValue(args.fragmentState, args.editItemId)

        Log.d("DEBUG", "${viewModel.note.value}")
    }

    override fun setObservers() {
        viewModel.note.observe(this){
            val selectColorDrawable = resources.getDrawable(R.drawable.selected_color_foreground, null)
            val transparentDrawable = resources.getDrawable(R.drawable.transparent, null)

            binding.cbIsEditable.isChecked = it.isEditable

            colorViewList.forEach { view ->
                val viewColor = (view.background as ColorDrawable).color

                if (getColor(it.color) == viewColor)
                    view.foreground = selectColorDrawable
                else
                    view.foreground = transparentDrawable
            }
        }

        viewModel.updateText.observe(this){
            binding.etTitle.text = it.title.toEditable()
            binding.etDescription.text = it.description.toEditable()
        }

        viewModel.saveNoteEvent.observe(this){
            viewModel.saveNote(args.fragmentState)
        }
        viewModel.returnToMenu.observe(this){
            navigateToPrevious()
        }
    }

    private fun createColorMenu(llColorsListContainer: LinearLayout) {
        val colorsList = getColorsList()

        colorsList?.forEach {
            val view = View(requireContext())
            llColorsListContainer.addView(view)

            view.apply {
                id = View.generateViewId()
                val size = resources.getDimension(R.dimen.choose_color_size).toInt()
                val margin = resources.getDimension(R.dimen.margin_5).toInt()

                (view.layoutParams as LinearLayout.LayoutParams).apply {
                    width = size
                    height = size
                    setMargins(margin, margin, margin, margin)
                }
                setBackgroundColor(getColor(it))

                setOnClickListener { _ ->
                    viewModel.updateColor(it)
                }
            }

            colorViewList.add(view)
        }
    }

    private fun setupToolbar(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { navigateToPrevious() }
    }

    private fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)
}