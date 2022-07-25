package com.onix.internship.ui.addNote

import com.onix.internship.R
import com.onix.internship.arch.BaseFragment
import com.onix.internship.databinding.AddNoteFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNoteFragment : BaseFragment<AddNoteFragmentBinding>(R.layout.add_note_fragment){
    override val viewModel: AddNoteViewModel by viewModel()

}