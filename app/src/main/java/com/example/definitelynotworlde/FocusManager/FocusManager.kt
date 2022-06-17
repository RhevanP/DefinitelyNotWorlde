package com.example.definitelynotworlde.FocusManager

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow

class FocusManager {
    companion object{

        private lateinit var context: Context //Allows you to use the context in a companion object

        fun setContext(con: Context){
            context = con
        }

        fun moveFocusToNewRow(rowActive: Int, mainTableLayout: TableLayout){
            var rowActiveObject: TableRow = mainTableLayout.getChildAt(rowActive) as TableRow
            var nextFocus: EditText = rowActiveObject.getChildAt(0) as EditText
            nextFocus?.requestFocus()
            openKeyboard(nextFocus)
        }

        fun openKeyboard(nextFocus: EditText){
            val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.showSoftInput(nextFocus, InputMethodManager.SHOW_FORCED)
        }
    }
}