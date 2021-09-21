package com.example.mysudoku.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.mysudoku.R
import com.example.mysudoku.game.Player
import kotlinx.android.synthetic.main.settings_site.*

internal open class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_site)

        emptyError.visibility = View.INVISIBLE //hide error until button click
        levelError.visibility = View.INVISIBLE

//        checkedBoxEasy.isChecked
//        checkedBoxHard.isChecked
//        checkedBoxMadness.isChecked

        //var radioId=checkedBoxGroup.checkedRadioButtonId
        //var radioButton = findViewById<RadioButton>(radioId)

        var playerNameString: String

        deleteButton.setOnClickListener {
            var tmpC = false
            var tmpP = false

            playerNameString = nicknameText.text.toString()

            if (checkedBoxEasy.isChecked || checkedBoxHard.isChecked || checkedBoxMadness.isChecked) {
                tmpC = true
                levelError.visibility = View.INVISIBLE
            } else {
                tmpC = false
                levelError.visibility = View.VISIBLE
            }

            if (playerNameString.isEmpty()) {
                tmpP = false
                emptyError.visibility = View.VISIBLE
            } else {
                tmpP = true
                emptyError.visibility = View.INVISIBLE
            }

            if (tmpP && tmpC) {

                //intentPlayerName(playerNameString)

                //              val intent = Intent(this@SettingsActivity, MainActivity::class.java)
//                //val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                //              intent.putExtra("playerName", playerNameString)
//                startActivity(intent)

                val text = "Your preferences've saved. \nNickname: " + playerNameString
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(this, text, duration)
                toast.show()

//                val intentPrompt = Intent(this, StartupActivity::class.java)
//                startActivity(intentPrompt)

                //setSharedPreference("pref_name","player",playerNameString)

                if (checkedBoxEasy.isChecked) {
                    setSharedPreference("playerNames","playerName",playerNameString)
                    setSharedPreference("difficultyLevels","difficultyLevel","easy")
                }
                if (checkedBoxHard.isChecked) {
                    setSharedPreference("playerNames","playerName",playerNameString)
                    setSharedPreference("difficultyLevels","difficultyLevel","hard")
                }
                if (checkedBoxMadness.isChecked) {
                    setSharedPreference("playerNames","playerName",playerNameString)
                    setSharedPreference("difficultyLevels","difficultyLevel","madness")
                }
            }
        }
    }

    fun Context.setSharedPreference(prefsName: String, key: String, value: String) {
        getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit().apply { putString(key, value); apply() }
    }
}