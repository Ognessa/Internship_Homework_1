package com.onix.internship.ui.tictactoe

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

enum class Symbol{ EMPTY, CROSS, CIRCLE }

/**
* (startX ; startY) -> upper left corner
* (endX ; endY) -> lower right corner
* */
class Cell(
    val startX : Float,
    val startY : Float,
    val endX : Float,
    val endY : Float)

class TicTacToeCell(
    var symbol: Symbol,
    val cell : Cell
)

class TicTacToeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    val symbolsPadding = 50.0f //make cross and circle smaller
    val paintSize = 10.0f
    lateinit var canvas : Canvas

    var ticTacToeObject : TicTacToeObject = TicTacToeViewModel().ticTacToeObject

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        textAlign = Paint.Align.CENTER
        strokeWidth = paintSize
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
        drawCells()
        if(ticTacToeObject.getTicTacToeCells().isNotEmpty()){
            ticTacToeObject.getTicTacToeCells().forEach { it ->
                when(it.symbol){
                    Symbol.CIRCLE -> drawCircle(it.cell)
                    Symbol.CROSS -> drawCross(it.cell)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN){
                ticTacToeObject.userTouchUpdate(event.x, event.y)
                invalidate()
        }
        return true
    }

    private fun drawCells(){
        //vertical lines
        canvas.drawLine(1.0f, 0.0f, 1.0f, height.toFloat(), paint)
        canvas.drawLine((width/3).toFloat(), 0.0f, (width/3).toFloat(), height.toFloat(), paint)
        canvas.drawLine((width/3*2).toFloat(), 0.0f, (width/3*2).toFloat(), height.toFloat(), paint)
        canvas.drawLine((width-1).toFloat(), 0.0f, (width-1).toFloat(), height.toFloat(), paint)
        //horizontal lines
        canvas.drawLine(0.0f, 1.0f, width.toFloat(), 1.0f, paint)
        canvas.drawLine(0.0f, (height/3).toFloat(), width.toFloat(), (height/3).toFloat(), paint)
        canvas.drawLine(0.0f, (height/3*2).toFloat(), width.toFloat(), (height/3*2).toFloat(), paint)
        canvas.drawLine(0.0f, (height-1).toFloat(), width.toFloat(), (height-1).toFloat(), paint)
    }

    private fun drawCircle(cell : Cell){
        val cx = (cell.startX + cell.endX) / 2
        val cy = (cell.startY + cell.endY) / 2
        val r = (cell.endY - cell.startY) / 2 - symbolsPadding
        canvas.drawCircle(cx, cy, r, paint)
    }

    private fun drawCross(cell : Cell) {
        canvas.drawLine(
            cell.startX + symbolsPadding,
            cell.startY + symbolsPadding,
            cell.endX - symbolsPadding,
            cell.endY - symbolsPadding,
            paint)
        canvas.drawLine(
            cell.endX - symbolsPadding,
            cell.startY + symbolsPadding,
            cell.startX + symbolsPadding,
            cell.endY - symbolsPadding,
            paint)
    }
}