package com.example.definitelynotworlde.WordManager

import android.util.Log
import android.widget.TableLayout
import android.widget.EditText
import android.widget.TableRow
import androidx.core.view.children

class WordManager {
    var wordToGuess: String = ""
    var lengthOfWord: Int = 0

    val wordsPotential: List<String> = listOf("Test", "Means", "Tea")
    val lengthOfPotentialWords: Int = wordsPotential.size

    var userGuessList: MutableList<String> = mutableListOf()
    var userGuessString: String = ""

    fun newWordGuess(){
        wordToGuess = wordsPotential[(0 until lengthOfPotentialWords).toList().random()]
        lengthOfWord = wordToGuess.length
        Log.v("WordToGuess", wordToGuess)
    }

    fun updateUserGuess(mainRow:TableRow){
        for(i in 0..mainRow.childCount){
            if(mainRow.getChildAt(i) is EditText){
                userGuessList.add((mainRow.getChildAt(i) as EditText).text.toString())
            }
        }
        userGuessString = userGuessList.joinToString("")
//        Log.v("WordGuessed", userGuessString)
    }

    fun resetUserGuess(){
        userGuessList.clear()
        userGuessString = ""
    }
}