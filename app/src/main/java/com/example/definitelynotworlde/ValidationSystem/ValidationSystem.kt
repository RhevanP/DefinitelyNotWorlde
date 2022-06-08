package com.example.definitelynotworlde.ValidationSystem

import android.graphics.Color
import android.util.Log
import android.widget.EditText
import android.widget.TableRow
import com.example.definitelynotworlde.WordManager.WordManager

class ValidationSystem {
    companion object{

        var comparisonWord = ""

        fun validationSystem(rowActiveObject : TableRow, wordManager: WordManager){
            validationSubSystem(rowActiveObject, wordManager, "g")
            validationSubSystem(rowActiveObject, wordManager)
//            Log.v("ComparisonWord", comparisonWord)
            comparisonWord = "" //So we reset for every guess
        }

        fun validationSubSystem(rowActiveObject : TableRow, wordManager: WordManager, colorValidation : String = "y/r"){
            for(i in 0..rowActiveObject.childCount){
                var childActive = rowActiveObject.getChildAt(i)
                if(rowActiveObject.getChildAt(i) is EditText){
                    var editTextActive = childActive as EditText
                    colorValidation(editTextActive, wordManager,i, colorValidation) //To validate the input (Red, green, blue)
                }
            }
        }

        private fun colorValidation(editTextActive: EditText, wordManager: WordManager, i: Int, colorValidation: String){
            if(colorValidation == "g"){ //In case it's green validation, we accept all greens
                if(wordManager.wordToGuess[i].equals(wordManager.userGuessString[i], true)){
                    editTextActive.setTextColor(Color.rgb(0,153,51)) //Green
                    comparisonWord += wordManager.userGuessString[i] //Storing in memory what was the letter to avoid duplicated yellow
                    editTextActive.isEnabled = false // Forced to repeat it since we do 2 loops : 1 for green, 1 for red/yellow
                }
            }

            else if(colorValidation != "g" && editTextActive.isEnabled){ //In case we're testing for yellow or red
                if(wordManager.wordToGuess.contains(wordManager.userGuessString[i], true) &&
                    countOccurences(wordManager.wordToGuess, wordManager.userGuessString[i]) > countOccurences(comparisonWord, wordManager.userGuessString[i]))
                // 2nd condition is to avoid a letter to counted twice as yellow when there's only 1 of it in the word
                {
                    editTextActive.setTextColor(Color.rgb(204,153,0)) //Yellow
                    comparisonWord += wordManager.userGuessString[i]
                    editTextActive.isEnabled = false
                }
                else{
                    editTextActive.setTextColor(Color.rgb(153,0,0)) //Red
                    editTextActive.isEnabled = false
                }
            }
        }

        fun countOccurences(s:String, ch:Char):Int{
            return s.filter{ it.lowercaseChar() == ch.lowercaseChar()}.count()
        }
    }
}