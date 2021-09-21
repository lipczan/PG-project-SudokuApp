package com.example.mysudoku.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysudoku.R
import com.example.mysudoku.game.Cell
import com.example.mysudoku.view.custom.SudokuBoardView
import com.example.mysudoku.viewmodel.PlaySudokuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.os.SystemClock
import android.widget.Toast
import com.example.mysudoku.game.Player
import kotlinx.android.synthetic.main.activity_main.submitBackButton
import java.text.SimpleDateFormat
import java.util.*


open class MainActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: PlaySudokuViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SudokuBoardView.registerListener(this)

        viewModel = ViewModelProvider(this).get(PlaySudokuViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(
            this,
            { updateSelectedCellUI(it) })
        viewModel.sudokuGame.cellsLiveData.observe(this, { updateCells(it) })

        //When and game then open scoreboard
        //observe game
        viewModel.sudokuGame.endSudokuGame.observe(
            this,
            {
                //TODO save to database score

                val playerTime=finalTime()
                toasttoast("Congratulations! Your solve time: ",playerTime)

                //setSharedPreference("playerTimes", "playerTime", playerTime)

                //forward solve time to scoreboard
                val intent = Intent(this, ScoreboardActivity::class.java)
                intent.putExtra("time", playerTime)
                startActivity(intent)
            }
        )

        val buttons = listOf(
            oneButton,
            twoButton,
            threeButton,
            fourButton,
            fiveButton,
            sixButton,
            sevenButton,
            eightButton,
            nineButton
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.sudokuGame.handleInput(index + 1)
            }
        }
        submitBackButton.setOnClickListener { viewModel.sudokuGame.delete() }
        startChronometer()

        textNameCell.text = "Nickname: " + getSharedPreference("playerNames", "playerName")
        textLevelCell.text = "Level: " + getSharedPreference("difficultyLevels", "difficultyLevel")

    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        SudokuBoardView.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        SudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }

    private fun startChronometer() {
        chronometer.start()
    }

    //stop chronometer and output time
    private fun stopChronometer() {
        chronometer.stop()
    }

    fun Context.setSharedPreference(prefsName: String, key: String, value: String) {
        getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit().apply { putString(key, value); apply() }
    }

    fun Context.getSharedPreference(prefsName: String, key: String): String {
        getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            ?.getString(key, "Please enter your nickname in settings!")?.let { return it }
        return "Preference doesn't exist."
    }

    fun finalTime(): String {
        stopChronometer()
        val saveTime = SystemClock.elapsedRealtime() - chronometer.getBase()
        val seconds = (saveTime / 1000 % 60)
        val minutes=(seconds/60)

        val pads=String.format("%02d" , seconds)
        val padm=String.format("%02d" , minutes)

        return "$padm:$pads"
    }

    fun toasttoast(title:String,text:String){
        val text = "$title$text"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(this, text, duration)
        toast.show()
    }
}





