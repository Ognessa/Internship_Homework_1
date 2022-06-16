package com.onix.internship.ui.tictactoe

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
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

    val ticTacToeCells = mutableListOf<TicTacToeCell>()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        textAlign = Paint.Align.CENTER
        strokeWidth = paintSize
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    init{
        val x0 = 0.0f
        val x1 = (width/3).toFloat()
        val x2 = (width/3*2).toFloat()
        val x3 = width.toFloat()

        val y0 = 0.0f
        val y1 = (height/3).toFloat()
        val y2 = (height/3*2).toFloat()
        val y3 = height.toFloat()

        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x0, y0, x1, y1) ))
        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x1, y0, x2, y1) ))
        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x2, y0, x3, y1) ))
        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x0, y1, x1, y2) ))
        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x1, y1, x2, y2) ))
        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x2, y1, x3, y2) ))
        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x0, y2, x1, y3) ))
        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x1, y2, x2, y3) ))
        ticTacToeCells.add(TicTacToeCell( Symbol.EMPTY, Cell(x2, y2, x3, y3) ))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
        drawCells()
        ticTacToeCells.forEach { it ->
            when(it.symbol){
                Symbol.CIRCLE -> drawCircle(it.cell)
                Symbol.CROSS -> drawCross(it.cell)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                updateGame(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_UP -> {}
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

    private fun drawCross(cell : Cell){
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

    fun updateGame(x : Float, y : Float){
        for(i in 0 until ticTacToeCells.size){
            val cell = ticTacToeCells[i] .cell
            if((cell.startX <= x && x <= cell.endX)
                && (cell.startY <= y && y <= cell.endY)
                && ticTacToeCells[i] .symbol == Symbol.EMPTY){
                ticTacToeCells[i] = TicTacToeCell(Symbol.CROSS, cell)
            }
        }
        ticTacToeCells.forEach { it ->
            Log.d("DEBUG", "${ticTacToeCells.indexOf(it)}: ${it.symbol}")}
    }
}