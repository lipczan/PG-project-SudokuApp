package com.example.mysudoku.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mysudoku.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.scoreboard_site.*

internal class ScoreboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scoreboard_site)

        nicknameView.text="Nickname: \n"+getSharedPreference("playerNames", "playerName")
        levelView.text="Level: \n" + getSharedPreference("difficultyLevels", "difficultyLevel")

        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("time")
            //The key argument here must match that used in the other activity
            timeView.text= "Solve Time: \n$value"
        }



    }

    fun Context.getSharedPreference(prefsName: String, key: String): String {
        getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            ?.getString(key, "")?.let { return it }
        return "Preference doesn't exist."
    }
}