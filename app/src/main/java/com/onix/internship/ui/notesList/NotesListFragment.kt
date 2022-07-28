package com.onix.internship.ui.notesList

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.NotesListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesListFragment : BaseFragment<NotesListFragmentBinding>(R.layout.notes_list_fragment) {

    override val viewModel: NotesListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showData()

        val adapter = NotesAdapter()

        binding.rvNotesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        viewModel.notesUpdated.observe(viewLifecycleOwner){
            adapter.setContent(it)
        }
    }
}