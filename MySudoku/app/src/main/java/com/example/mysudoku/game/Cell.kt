package com.example.mysudoku.game

class Cell(
    val row: Int,
    val col: Int,
    var value: Int,
    var isStartingCell: Boolean = false,
    var isVisible: Boolean = false,
    var isError: Boolean = false
)        //store information about specific cell

