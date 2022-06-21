package com.onix.internship.ui.tictactoe

import com.onix.internship.arch.BaseViewModel

class TicTacToeViewModel : BaseViewModel() {
    /*TODO init
    *  TicTacToeObject
    *  isUserMakeChoice
    *  */
    var ticTacToeObject = TicTacToeObject(Symbol.CROSS, Symbol.CIRCLE)
    private val  isUserMakeChoice : Boolean = false

    fun initStartValues(symbol: Symbol, width : Float, height : Float){
        when(symbol){
            Symbol.CIRCLE -> ticTacToeObject = TicTacToeObject(symbol, Symbol.CROSS)
            Symbol.CROSS -> ticTacToeObject = TicTacToeObject(symbol, Symbol.CIRCLE)
        }
        ticTacToeObject.initTicTacToeCells(width, height)
    }

}