package com.onix.internship.ui.tictactoe

class TicTacToeObject(
    private val userSymbol : Symbol,
    private val computerSymbol : Symbol) {

    private val ticTacToeCells = mutableListOf<TicTacToeCell>()

    fun getUserSymbol() : Symbol {return userSymbol}
    fun getComputerSymbol() : Symbol {return computerSymbol}
    fun getTicTacToeCells() : MutableList<TicTacToeCell> {return ticTacToeCells}

    fun initTicTacToeCells(width : Float, height : Float){
        val x0 = 0.0f
        val x1 = width/3.0f
        val x2 = width/3.0f*2.0f
        val x3 = width

        val y0 = 0.0f
        val y1 = height/3.0f
        val y2 = height/3.0f*2.0f
        val y3 = height

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

    fun userTouchUpdate(x: Float, y : Float){
        ticTacToeCells.forEach{ it ->
            val cell = it.cell
            if((cell.startX <= x && x <= cell.endX)
                && (cell.startY <= y && y <= cell.endY)
                && it.symbol == Symbol.EMPTY){
                ticTacToeCells[ticTacToeCells.indexOf(it)].symbol = userSymbol
            }
        }
    }

    fun computerTouchUpdate(){
        val indexes = arrayListOf<Int>()
        ticTacToeCells.forEach {
            if(it.symbol == Symbol.EMPTY)
                indexes.add(ticTacToeCells.indexOf(it))
        }
        if(indexes.isNotEmpty())
            ticTacToeCells[indexes.random()].symbol = computerSymbol
    }

}