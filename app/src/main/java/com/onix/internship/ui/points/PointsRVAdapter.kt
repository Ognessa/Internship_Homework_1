package com.onix.internship.ui.points

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R
import com.onix.internship.arch.adapter.BaseRecyclerAdapter
import com.onix.internship.databinding.PointListItemBinding
import com.onix.internship.objects.Point
import com.onix.internship.objects.PointsStore
import org.koin.java.KoinJavaComponent.inject

class PointsRVAdapter(private val context: Context) : BaseRecyclerAdapter<PointsRVAdapter.PointsViewHolder, Point>() {

    private val pointsStore : PointsStore by inject(PointsStore::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsViewHolder {
        return from(parent, context)
    }

    override fun onBindViewHolder(holder: PointsViewHolder, position: Int) {
        holder.bind(adapterItems[position])
        holder.binding.ivDelete.setOnClickListener {
            adapterItems.removeAt(position)
            pointsStore.pointsList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    class PointsViewHolder(val binding: PointListItemBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){
        private val pointClassesList: Array<String> = context.resources.getStringArray(R.array.class_titles)

        fun bind(it : Point){
            val pointClass = pointClassesList[it.pointClass]
            val drawableName = "ic_${pointClass.lowercase()}"

            binding.ivClassIcon.setImageDrawable(getDrawableByName(drawableName))
            binding.tvPointInfo.text = "$pointClass Level ${it.level}"
            binding.tvPointCalendarInfo.text = "Available ${it.date} at ${it.time}"
        }

        private fun getDrawableByName(name : String) : Drawable {
            return context.resources.getDrawable(context.resources
                .getIdentifier(name, "drawable", context.packageName), null)
        }
    }

    companion object{
        fun from(parent: ViewGroup, context: Context) : PointsViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.point_list_item, parent, false)
            val binding = PointListItemBinding.bind(view)
            return PointsViewHolder(binding, context)
        }
    }
}
