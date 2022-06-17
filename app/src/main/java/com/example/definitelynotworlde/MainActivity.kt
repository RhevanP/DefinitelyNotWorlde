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
import com.google.android.gms.ads.*

//import com.example.definitelynotworlde.tableCreation.tableCreation

class MainActivity : AppCompatActivity() {

    lateinit var mAdView: AdView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Google Ads Initialization
        MobileAds.initialize(this){
            Log.v("BANNER", "initialize Complete")
        }

        mAdView = binding.adView //The Ads Loads, Impression but is not displayed due to Google API Manager unavailable
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object:AdListener(){
            override fun onAdFailedToLoad(adError : LoadAdError) {
                Log.v("BANNER", adError.toString())
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                Log.v("BANNER", "impression")
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.v("BANNER", "Loaded")
            }
        }

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
                    if(wordManager.userGuessString.equals(wordManager.wordToGuess, true)){
                        gameManager.gameOverWin()
                        ButtonManager.gameOverWin(buttonValidation)
                    }
                    else if (gameManager.livesLeft()){
                        gameManager.gameOverLoose() //Triggers correctly
                        ButtonManager.gameOverLoose(buttonValidation)
                    }
                    else{
                        rowActive += 1 //Changing what is the active row
                        wordManager.resetUserGuess()
                        tableCreation.createNewRow(mainTable,wordManager.lengthOfWord, rowActive, wordManager, buttonValidation) //Create the new row
                        ButtonManager.moveToActiveRow(buttonValidation, rowActive, mainTable, rowActiveObject)
                    }

                }
                else if(wordManager.userGuessString.isEmpty()){
                    alertManager.emptyInput(alertManager.alertUser)
                }
                else{
                    alertManager.inputSizeLower(alertManager.alertUser, wordManager.wordToGuess.length)
                }
            }
            else if (gameManager.gameState == 1){
                ButtonManager.gameOverWinAnimation(buttonValidation)
            }
            else if(gameManager.gameState == -1){
                ButtonManager.gameOverLooseAnimation(buttonValidation)
            }
        }
    }
}