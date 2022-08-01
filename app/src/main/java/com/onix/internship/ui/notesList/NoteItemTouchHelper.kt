package com.onix.internship.ui.notesList

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.onix.internship.R

class NoteItemTouchHelper(
    val adapter: NotesAdapter,
    val viewModel: NotesListViewModel,
    val resources: Resources
    ) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val notesData = adapter.adapterItems[position]
        viewModel.deleteItem(notesData)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
        val width = height / 3

        val paint = Paint()
        paint.color = resources.getColor(R.color.red, null)
        val background = RectF(
            itemView.right.toFloat() + dX,
            itemView.top.toFloat(),
            itemView.right.toFloat(),
            itemView.bottom.toFloat()
        )
        c.drawRect(background, paint)

        val icon = resources.getDrawable(R.drawable.ic_delete, null).toBitmap()
        val iconDest = RectF(
            itemView.right.toFloat() - 2*width,
            itemView.top.toFloat() + width/2,
            itemView.right.toFloat() - width/2,
            itemView.bottom.toFloat() - width/2
        )
        c.drawBitmap(icon, null, iconDest, paint)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}