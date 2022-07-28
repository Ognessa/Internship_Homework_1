package com.onix.internship.ui.notesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter
import com.onix.internship.databinding.NotesItemBinding
import com.onix.internship.objects.NotesData
import com.onix.internship.objects.getColor

class NotesAdapter : BaseRecyclerAdapter<NotesAdapter.NotesHolder, NotesData>(){

    class NotesHolder(val binding : NotesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(it : NotesData){
            binding.noteData = it
            binding.tvNoteTitle.setTextColor(getColor(it.color))
            if(!it.isEditable) binding.ivEdit.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.bind(adapterItems[position])
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
