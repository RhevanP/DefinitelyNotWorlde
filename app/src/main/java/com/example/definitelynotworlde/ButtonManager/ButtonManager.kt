package com.example.definitelynotworlde.ButtonManager

import android.content.Context
import android.graphics.Color
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.definitelynotworlde.FocusManager.FocusManager
import com.example.definitelynotworlde.R
import com.example.definitelynotworlde.WordManager.WordManager
import kotlin.reflect.KVariance


class ButtonManager {
    companion object{
        private lateinit var context: Context //Allows you to use the context in a companion object

        fun setContext(con: Context){
            this.context = con
        }

        fun moveToActiveRow(buttonValidate: ImageButton, rowActive: Int, mainTableLayout: TableLayout, rowActiveObject: TableRow){
            buttonValidate.animate().y(rowActive* rowActiveObject.height + mainTableLayout.y)
//            Log.v("MainRowYPosition", buttonValidate.y.toString())
            FocusManager.moveFocusToNewRow(rowActive, mainTableLayout) // Change the focus to the next line
            resetColorToDefault(buttonValidate) // Reset to white-ish for UX reasons
        }

        fun changeColorForValidation(buttonValidate: ImageButton, rowActiveObject: TableRow, wordManager: WordManager){
            wordManager.updateUserGuess(rowActiveObject)//We already made the system, may as well reuse it
            if(wordManager.userGuessString.length == wordManager.wordToGuess.length){
                buttonValidate.setImageResource(R.drawable.ic_baseline_done_wordbigenough)
                buttonValidate.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
            }
            wordManager.resetUserGuess() //Just to make sure it's deleted before we click on the button
        }

        fun resetColorToDefault(buttonValidate: ImageButton){
            buttonValidate.setImageResource(R.drawable.ic_baseline_done_wordnotbigenough)
            buttonValidate.setBackgroundColor(ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_300))
        }

        fun gameOverWin(buttonValidate: ImageButton){
            buttonValidate.setImageResource(R.drawable.ic_win)
            buttonValidate.setBackgroundColor(ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_50))
            gameOverWinAnimation(buttonValidate)
        }

        fun gameOverWinAnimation(buttonValidate: ImageButton){
            buttonValidate.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()
        }

        fun gameOverLoose(buttonValidate: ImageButton){
            buttonValidate.setImageResource(R.drawable.ic_loose)
            buttonValidate.setBackgroundColor(ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_50))
            gameOverLooseAnimation(buttonValidate)
        }

        fun gameOverLooseAnimation(buttonValidate: ImageButton){
            var shakeLoose: Animation = AnimationUtils.loadAnimation(context, R.anim.shakeloose)
            buttonValidate.startAnimation(shakeLoose)
            //TODO : Fix this to make it work
        }
    }
}