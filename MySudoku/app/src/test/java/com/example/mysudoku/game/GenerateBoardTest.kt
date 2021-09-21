package com.example.mysudoku.game

import org.junit.Test

class GenerateBoardTest {

    @Test
    fun randomBoard() {
        //val generateBoard = GenerateBoard()
        //generateBoard.randomBoard()
        val listOfNumbers = ArrayList<Int>()
        for (i in 1..81) listOfNumbers.add(i)
        println(listOfNumbers)
        val n = listOfNumbers.random()
        println(n)
        listOfNumbers.remove(n)
        println(listOfNumbers)
    }


    @Test
    fun allBoardNumbers() {
        val logic = GenerateBoard()
        println("Sudoku grid to solve")
        logic.display()
        if (logic.solve()) {
            println("Sudoku Grid solved with simple BT \n${logic.toList()}")
            logic.display()
        } else {
            println("Unsolvable")
        }
    }

    //Draw numbers for one row PO SKOSIE 3 kwadraty!!!
    @Test
    fun drawNumbersIn3Square() {
        for (i in 0..10) {
            val logic = GenerateBoard()
            logic.display()
        }
        println("=========================================")
    }



}