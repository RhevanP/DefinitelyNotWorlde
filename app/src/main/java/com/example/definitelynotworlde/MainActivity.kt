package com.example.definitelynotworlde

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.*
import com.example.definitelynotworlde.AlertManager.alertManager
import com.example.definitelynotworlde.ButtonManager.ButtonManager
import com.example.definitelynotworlde.FocusManager.FocusManager
import com.example.definitelynotworlde.GameManager.gameManager
import com.example.definitelynotworlde.ValidationSystem.ValidationSystem
import com.example.definitelynotworlde.WordManager.WordManager
import com.example.definitelynotworlde.databinding.ActivityMainBinding
import com.example.definitelynotworlde.tableCreation.tableCreation

//import com.example.definitelynotworlde.tableCreation.tableCreation

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FocusManager.setContext(this) //create the context variable for using it in a static object!
        ButtonManager.setContext(this)
        tableCreation.setContext(this)

        val mainTable: TableLayout = binding.mainTable
        val buttonValidation: ImageButton = binding.validbutton
        val wordManager: WordManager = WordManager()
        val alertManager: alertManager = alertManager(buttonValidation)

        var rowActive = 0
        var mLastClickTime = SystemClock.elapsedRealtime()-1000

        wordManager.newWordGuess() //New word to guess generation

        tableCreation.createNewRow(mainTable,wordManager.lengthOfWord, rowActive, wordManager, buttonValidation) //First creation to guess the word

        buttonValidation.setOnClickListener{
            if(SystemClock.elapsedRealtime() - mLastClickTime >= 1000 && gameManager.gameState == 0){ //So we avoid the double click and some potential bugs in the code
                var rowActiveObject: TableRow = mainTable.getChildAt(rowActive) as TableRow
                wordManager.updateUserGuess(rowActiveObject) // Updating the user guess based on the input
                if(wordManager.userGuessString.length == wordManager.wordToGuess.length){
                    ValidationSystem.validationSystem(rowActiveObject, wordManager) // Validating user input and color code
                    mLastClickTime = SystemClock.elapsedRealtime() // Making sure the user can't spam the button
                    gameManager.tryRecording()
                    if(wordManager.userGuessString == wordManager.wordToGuess.lowercase()){
                        gameManager.gameOverWin()
                        //TODO : buttonValidate changing to something to party the win
                    }
                    else if (gameManager.livesLeft()){
                        gameManager.gameOverLoose()
                        //TODO : buttonValidate in red with a cross
                    }
                    else{
                        rowActive += 1 //Changing what is the active row
                        wordManager.resetUserGuess()
                        tableCreation.createNewRow(mainTable,wordManager.lengthOfWord, rowActive, wordManager, buttonValidation) //Create the new row
                        ButtonManager.moveToActiveRow(buttonValidation, rowActive, mainTable, rowActiveObject)
                    }

                }
                else if(wordManager.userGuessString.length == 0){
                    alertManager.emptyInput(alertManager.alertUser)
                }
                else{
                    alertManager.inputSizeLower(alertManager.alertUser)
                }
            }
        }

        //TODO : The keyboard is opening on numbers and NOT on letters
        //TODO : The focus doesn't change to the new line as expected
        //TODO : Center the view when the inputs are BELOW the keyboard for the user
        //TODO : Alert Manager should be saying the length of the word, not 5 by default.
        //TODO : The first input ALSO HAS to be a capital letter in the table creation
        //TODO : increase the number of words available
        //TODO : Banners at the top instead of the text
        //TODO : Reset button
    }
}