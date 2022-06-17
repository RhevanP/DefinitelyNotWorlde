package com.example.definitelynotworlde.WordManager

import android.util.Log
import android.widget.TableLayout
import android.widget.EditText
import android.widget.TableRow
import androidx.core.view.children
import java.lang.StringBuilder

class WordManager {
    var wordToGuess: String = ""
    var lengthOfWord: Int = 0

    val wordsPotential: List<String> = listOf("Test", "Means", "Tea")
    val lengthOfPotentialWords: Int = wordsPotential.size

    var userGuessList: MutableList<String> = mutableListOf()
    var userGuessString: String = ""

    var mapAnswerForHintChanges = mutableMapOf<Int, String>()
    var mapUserBasedInput = mutableMapOf<Int, String>()

    fun newWordGuess(){
        wordToGuess = wordsPotential[(0 until lengthOfPotentialWords).toList().random()]
        lengthOfWord = wordToGuess.length
        for (i in 0 until lengthOfWord){
            mapAnswerForHintChanges[i] = wordToGuess[i].toString() //This is to store what place is which letter
            mapUserBasedInput[i] = "?" //To have something in case there's nothing
        }
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