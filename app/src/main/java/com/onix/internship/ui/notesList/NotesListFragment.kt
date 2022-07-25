package com.onix.internship.ui.notesList

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.NotesListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesListFragment : BaseFragment<NotesListFragmentBinding>(R.layout.notes_list_fragment){

    override val viewModel: NotesListViewModel by viewModel()

}