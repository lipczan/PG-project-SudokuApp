package com.example.mysudoku.game

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mysudoku.view.ScoreboardActivity

class SudokuGame {

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()
    var endSudokuGame = MutableLiveData<Boolean>()
    var startingBoard = listOf<Int>()

    private var selectedRow = -1
    private var selectedCol = -1
    private val board: Board

    init {
        val generateBoard = GenerateBoard() //generate 2D Array
                                            //first 3 box fill with random numbers
                                            //
        generateBoard.solve()   //fill the rest of sudoku board with random numbers
        //val randomCellValues = generateBoard.toList()
        startingBoard = generateBoard.toList()  //save generated list to starting board

        val cells = List(9 * 9) { i -> Cell(i / 9, i % 9, 0) }

        cells.apply {
            val listOfNumbers = ArrayList<Int>()
            for (i in 1..81) listOfNumbers.add(i)   //fill with random numbers

            //TODO draw numbers depends on difficulty level
            //TODO loop based on difficulty level
            for (i in 1..80) {      //loop to print the sudoku numbers
                val n = listOfNumbers.random()
                this[n - 1].isVisible = true
                this[n - 1].isStartingCell = true
                this[n - 1].value = startingBoard[n - 1]    //write correct number form starting board using generated random index from listofNumbers
                listOfNumbers.remove(n)
            }
        }
        board = Board(9, cells)

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)    //nothing is post at beginning, we have to initiate it before first input
    }

    fun handleInput(number: Int) {    //input number
        if (selectedRow == -1 || selectedCol == -1) {
            return
        }
        if (board.getCell(selectedRow, selectedCol).isStartingCell) {      //prevent from fill starting cells
            return
        }

        board.getCell(selectedRow, selectedCol).value = number
        board.getCell(selectedRow, selectedCol).isVisible = true
        board.getCell(selectedRow, selectedCol).isError = !check(selectedRow, selectedCol)  //check user number, if it is incorrect, fill cell with red color
        cellsLiveData.postValue(board.cells)
        if (endGame()) {
            endSudokuGame.value = true
            ///END GAME
            //TODO insert player name into SQL Database
        }
    }

    fun updateSelectedCell(row: Int, col: Int) {
        if (!board.getCell(row, col).isStartingCell) {    //prevent from starting cell changes
            selectedRow = row
            selectedCol = col
            selectedCellLiveData.postValue(Pair(row, col))
        }
    }

    fun delete() {
        board.getCell(selectedRow, selectedCol).value = 0   //input blank field
        if (board.getCell(selectedRow, selectedCol).isError) {
            board.getCell(selectedRow, selectedCol).isError = false
        }
        cellsLiveData.postValue(board.cells)
        if (board.getCell(selectedRow, selectedCol).value == 0) {
            board.getCell(selectedRow, selectedCol).isVisible = false   //make deleted number invisible
        }

    }

    //TODO check if number is in row or column or in box
    //check with starting board
    fun check(row: Int, col: Int): Boolean {
        return board.getCell(row, col).value == startingBoard[row * 9 + col]
    }

    //check all cells
    fun endGame(): Boolean {
        for (c in board.cells) {
            if (!c.isVisible) return false
            if (c.isError) return false
        }
        return true
    }

    fun Context.getSharedPreference(prefsName: String, key: String): String {
        getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            ?.getString(key, "")?.let { return it }
        return "Preference doesn't exist."
    }

    fun getLevel(){
        
    }
}



