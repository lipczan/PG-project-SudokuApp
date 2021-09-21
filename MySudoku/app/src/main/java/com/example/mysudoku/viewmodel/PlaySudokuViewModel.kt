package com.example.mysudoku.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mysudoku.game.SudokuGame

class PlaySudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()
}