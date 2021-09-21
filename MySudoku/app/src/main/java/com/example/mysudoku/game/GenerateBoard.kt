package com.example.mysudoku.game

//
class GenerateBoard {
    private val board: Array<IntArray> = drawNumbersIn3Square() //generate 2D array

    // check if possible number is already in a row
    private fun isInRow(row: Int, number: Int): Boolean {
        for (i in 0 until 9) if (board[row][i] == number) return true
        return false
    }

    // check if possible number is already in a column
    private fun isInCol(col: Int, number: Int): Boolean {
        for (i in 0 until 9) if (board[i][col] == number) return true
        return false
    }

    // check if possible number is in 3x3 box
    private fun isInBox(row: Int, col: Int, number: Int): Boolean {
        val r = row - row % 3
        val c = col - col % 3
        for (i in r until r + 3) for (j in c until c + 3) if (board[i][j] == number) return true
        return false
    }

    // combined method to check if a number possible to a row,col position is ok
    private fun isAllOk(row: Int, col: Int, number: Int): Boolean{
        return !isInRow(row, number) && !isInCol(col, number) && !isInBox(row, col, number)
    }

    //recursive BackTracking algorithm.
    fun solve(): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                // we search an empty cell
                if (board[row][col] == 0) {
                    // we try possible numbers
                    for (number in 1..9) {
                        if (isAllOk(row, col, number)) {// number ok. it respects sudoku constraints
                            board[row][col] = number
                            if (solve()) { // we start backtracking recursively
                                return true
                            } else { // if not a solution, we empty the cell and we continue
                                board[row][col] = 0
                            }
                        }
                    }
                    return false // we return false
                }
            }
        }
        return true // sudoku solved
    }

    fun randomBoard(){
        //First is number of Row or Col or Box IntArray - sudoku numbers
        val mapRow: MutableMap<Int, IntArray> = mutableMapOf()
        val mapCol: MutableMap<Int, IntArray> = mutableMapOf()
        val mapBox: MutableMap<Int, IntArray> = mutableMapOf()
        for(i in 0..8){
            mapRow[i] = intArrayOf(1,2,3,4,5,6,7,8,9)
            mapCol[i] = intArrayOf(1,2,3,4,5,6,7,8,9)
            mapBox[i] = intArrayOf(1,2,3,4,5,6,7,8,9)
        }
        //Draw box number
        val boxNumber = (0..8).random()

    }


    //Convert from 2x2 array to List
    fun toList(): List<Int>{
        val list = mutableListOf<Int>()
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                list.add(board[i][j])
            }
        }
        return list
    }

    fun display() {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                print(" " + board[i][j])
            }
            println()
        }
        println()
    }

    companion object{
        fun drawNumbersIn3Square()
        : Array<IntArray> {
            val notFullBoard: Array<IntArray> = arrayOf(intArrayOf(0,0,0,0,0,0,0,0,0),
                intArrayOf(0,0,0,0,0,0,0,0,0),
                intArrayOf(0,0,0,0,0,0,0,0,0),
                intArrayOf(0,0,0,0,0,0,0,0,0),
                intArrayOf(0,0,0,0,0,0,0,0,0),
                intArrayOf(0,0,0,0,0,0,0,0,0),
                intArrayOf(0,0,0,0,0,0,0,0,0),
                intArrayOf(0,0,0,0,0,0,0,0,0),
                intArrayOf(0,0,0,0,0,0,0,0,0))

            val squareRow:ArrayList<Int> = arrayListOf(0,1,2)
            val squareColumn:ArrayList<Int> = arrayListOf(0,1,2)
            var n: Int
            for(i in 0 .. 2){
                val numbersToDraw = arrayListOf(1,2,3,4,5,6,7,8,9)
                val r = squareRow.random()
                squareRow.remove(r)
                val c = squareColumn.random()
                squareColumn.remove(c)
                //println("$r $c ")
                for(j in 0..2){
                    for(k in 0..2){
                        n = numbersToDraw.random()
                        numbersToDraw.remove(n)
                        notFullBoard[j+r*3][k+c*3] = n
                    }

                }

            }
            return notFullBoard
        }

    }
}