package com.example.definitelynotworlde.tableCreation

import android.content.Context
import android.content.res.Resources
import android.text.*
import android.text.method.DigitsKeyListener
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.text.isDigitsOnly
import com.example.definitelynotworlde.ButtonManager.ButtonManager
import com.example.definitelynotworlde.R
import com.example.definitelynotworlde.WordManager.WordManager
import java.lang.NumberFormatException


class tableCreation {
    companion object {
        private lateinit var context: Context //Allows you to use the context in a companion object

        fun setContext(con: Context){
            context = con
        }

        fun pixelConverterFromDP(dp: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                Resources.getSystem().displayMetrics
            ).toInt()
        }

        fun pixelConverterFromSP(sp: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp,
                Resources.getSystem().displayMetrics
            )
        }

        fun LimitInput(InputText: EditText, s: CharSequence) { //To not allow the user to enter numbers BUT allowing him to overwrite a cell
//            Log.v("CursorDebug", InputText.selectionStart.toString())

            if(InputText.text.length > 1 && InputText.selectionStart != 1){
                InputText.setText(
                    InputText.text.toString().drop(1)
                )
//                Log.v("CursorDebug","Cursor was on the right")
            }
            else if(InputText.text.length > 1 && InputText.selectionStart == 1){
                InputText.setText(
                    InputText.text.toString().dropLast(1)
                )
//                Log.v("CursorDebug","Cursor was on the LEFT")
            }
            InputText.setSelection(InputText.text.length) //So the cursor ALWAYS appear at the end of the character
        }

        class InputFilterTextOnly(): InputFilter {
            private val allowedChars = ('a'..'z').plus('A'..'Z').joinToString("")
            override fun filter(
                source: CharSequence,
                start: Int,
                end: Int,
                dest: Spanned,
                dstart: Int,
                dend: Int
            ): CharSequence? {
//                Log.v("FILTERCUSTOM", dest.toString())
//                Log.v("FILTERCUSTOM", source.toString())
                return source.filter { it in allowedChars }
            }
        }

        fun SetupInputText(InputText: EditText, lengthOfWord: Int){
            InputText.setHint(R.string.fill_boxes)
            InputText.filters = arrayOf(InputFilter.LengthFilter(2), InputFilterTextOnly(),InputFilter.AllCaps())
            InputText.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            InputText.textSize = pixelConverterFromDP(20f).toFloat()
            InputText.width = pixelConverterFromDP(60f*5/lengthOfWord)
            InputText.height = pixelConverterFromDP(100f)
            InputText.gravity = Gravity.CENTER
        }

        fun ChangeFocusOfInput(InputText: EditText){
            if (InputText.text.toString() != null && InputText.text.isNotEmpty()) {
                val next = InputText.focusSearch(View.FOCUS_RIGHT) // or FOCUS_FORWARD
                next?.requestFocus()
            }
        }

        fun createNewRow(tableMain: TableLayout, lengthOfWord: Int, rowActive: Int,wordManager: WordManager, buttonValidate: ImageButton) {
            var newTableRow: TableRow = TableRow(tableMain.context)

            for (i in 0 until lengthOfWord) { //Setup every single EditText in there

                var newUserInputTextField = EditText(newTableRow.context)
                SetupInputText(newUserInputTextField, lengthOfWord) // Take care of all the formatting

                //For the click to force the input to be at the end of the case
                newUserInputTextField.setOnClickListener(View.OnClickListener {
                    newUserInputTextField.setSelection(newUserInputTextField.text.length)
                })

                //This manages the change of focus to the next EditText AND manage the number of letters inside the cases
                newUserInputTextField.addTextChangedListener(object : TextWatcher{ //To trigger whenever the user change the text
                    override fun afterTextChanged(s: Editable) {
                        ChangeFocusOfInput(newUserInputTextField)
                        ButtonManager.changeColorForValidation(buttonValidate, newTableRow,wordManager)
                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int,
                                                   count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence, start: Int,
                                               before: Int, count: Int) {
                        if(s.isNotEmpty()){
                            LimitInput(newUserInputTextField,s) // To limit the output to 1 character
                        }
                    }
                })

                //Add the row to the table
                newTableRow.addView(newUserInputTextField, i)
            }

            tableMain.addView(newTableRow)
        }
    }
}