package com.carlosmezquita.ej1jcml

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.carlosmezquita.ej1jcml.databinding.EditPlayerActivityBinding

class Utils {
    fun checkParameters(binding: EditPlayerActivityBinding): Boolean {
        var valid = true

        if (binding.playerCode.text.toString() == "") {
            binding.playerCode.error = "Introduce un número válido"
            valid = false
        }
        if (binding.playerName.text.toString() == "") {
            binding.playerName.error = "Introduce un nombre válido"
            valid = false

        }
        if (binding.playerPrice.text.toString() == "") {
            binding.playerPrice.error = "Introduce un precio válido"
            valid = false

        }
        if (binding.playerScore.text.toString() == "") {
            binding.playerScore.error = "Introduce una puntuación válida"
            valid = false

        }
        return valid
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            AppCompatActivity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }
}