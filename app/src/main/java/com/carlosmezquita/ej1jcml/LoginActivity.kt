package com.carlosmezquita.ej1jcml

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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
        title = "Inicia sesión"
        setContentView(view)
        binding.loginButton.setOnClickListener { loginAuthentication() }
        binding.passwordInput.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun loginAuthentication() {
        Utils().hideSoftKeyboard(this@LoginActivity)
        if (!(binding.passwordInput.text.toString() == "liceo" && binding.userInput.text.toString() == "admin")) {
            binding.passwordInput.setText("")
            binding.userInput.setText("")
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
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        super.finish()
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            loginAuthentication()
            return true
        }
        return false
    }

}