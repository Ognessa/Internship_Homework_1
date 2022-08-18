package com.onix.internship.ui.soundList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerListAdapter
import com.onix.internship.arch.adapter.BaseViewHolder
import com.onix.internship.databinding.RecordingItemBinding
import com.onix.internship.objects.localObjects.RecordingData

class SoundHolder(
    binding: RecordingItemBinding,
    private val onSoundClickListener: OnSoundClickListener
) : BaseViewHolder<RecordingData, RecordingItemBinding>(binding) {
    override fun bindView(position: Int) {
        binding.data = item

        binding.root.setOnClickListener {
            onSoundClickListener.soundSelected(item)
        }

        binding.playSound.setOnClickListener {
            onSoundClickListener.playSound(item)
        }
    }
}

class SoundListAdapter(
    private val onSoundClickListener: OnSoundClickListener
) : BaseRecyclerListAdapter<RecordingData>(SoundDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecordingData, out ViewDataBinding> {
        return from(parent)
    }

    private fun from(parent: ViewGroup): SoundHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recording_item, parent, false)
        val binding = RecordingItemBinding.bind(view)
        return SoundHolder(binding, onSoundClickListener)
    }

}

class SoundDiffCallback : DiffUtil.ItemCallback<RecordingData>(){
    override fun areItemsTheSame(oldItem: RecordingData, newItem: RecordingData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecordingData, newItem: RecordingData): Boolean {
        return oldItem == newItem
    }
}