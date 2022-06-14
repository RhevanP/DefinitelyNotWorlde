package com.example.definitelynotworlde.AlertManager

import android.app.AlertDialog
import android.widget.Button
import android.widget.ImageButton
import com.example.definitelynotworlde.ButtonManager.ButtonManager
import com.example.definitelynotworlde.R

class alertManager(buttonValidation: ImageButton) {
    val alertUser = AlertDialog.Builder(buttonValidation.context)
    fun emptyInput(alertUser: AlertDialog.Builder){
        alertUser.setTitle(R.string.empty_input)
        alertUser.setMessage(R.string.empty_input_message)
        alertUser.show()
    }
    fun inputSizeLower(alertUser: AlertDialog.Builder, wordLength: Int){
        alertUser.setTitle(R.string.lower_input)
        alertUser.setMessage("Your word does not have $wordLength letters")
        alertUser.show()
    }
}