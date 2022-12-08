package com.carlosmezquita.ej1jcml

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carlosmezquita.ej1jcml.databinding.LoginActivityBinding
import com.carlosmezquita.ej1jcml.playerlist.PlayerListActivity
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        title = "Inicia sesión";
        setContentView(view)
        binding.loginButton.setOnClickListener { loginAuthentication() }
    }

    private fun loginAuthentication() {
        hideSoftKeyboard(this)
        if (!(binding.passwordInput.text.toString() == "1234" && binding.userInput.text.toString() == "admin")) {
            val text = "Wrong username or password."
            val duration = Toast.LENGTH_SHORT
            Snackbar.make(
                binding.root,
                "Los datos introducidos son incorrectos.",
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null)
                .show()
            return
        }
        val intent = Intent(this@LoginActivity, PlayerListActivity::class.java)
        startActivity(intent)
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }
}