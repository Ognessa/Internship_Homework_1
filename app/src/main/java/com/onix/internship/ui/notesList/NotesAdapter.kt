package com.onix.internship.ui.notesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter
import com.onix.internship.databinding.NotesItemBinding
import com.onix.internship.objects.NotesData

class NotesAdapter(
    private val expandNote: (NotesData) -> Unit,
    private val editNote: (NotesData) -> Unit
    ) :
    BaseRecyclerAdapter<NotesAdapter.NotesHolder, NotesData>(){

    class NotesHolder(val binding : NotesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(it : NotesData, expandNote: (NotesData) -> Unit, editNote: (NotesData) -> Unit){
            binding.noteData = it
            binding.ivHideDescription.setOnClickListener { _ ->
                expandNote(it)
            }
            binding.ivEdit.setOnClickListener { _ ->
                editNote(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.bind(adapterItems[position], expandNote, editNote)
    }

    companion object{
        fun from(parent: ViewGroup) : NotesHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.notes_item, parent, false)
            val binding = NotesItemBinding.bind(view)
            return NotesHolder(binding)
        }
    }
}
