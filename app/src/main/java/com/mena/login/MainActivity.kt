package com.mena.login

import ApiService
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val loginButton: Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(emailEditText.windowToken, 0)
            inputMethodManager.hideSoftInputFromWindow(passwordEditText.windowToken, 0)
            val username = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() && password.isEmpty() ) {
                showMessage("${resources.getString(R.string.no_int)}")
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                return@setOnClickListener
            }
           if  (username.isEmpty() ){
                showMessage("${resources.getString(R.string.no_user)}")
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
               return@setOnClickListener
           }
            if (password.isEmpty()){
                showMessage("${resources.getString(R.string.no_pass)}")
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                return@setOnClickListener
            }

            else {
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                login(username, password)
            }
        }
    }

    private fun login(username: String, password: String) {
        val apiService = ApiService()
        val statv = 200
        apiService.login(username, password) { loginResponse, errorMessage ->
            if (loginResponse != null && loginResponse.status?.toInt() == statv) {
                showMessage("${resources.getString(R.string.login_don)}")
                val intent = Intent(this, Home::class.java)
                intent.putExtra("TOKEN_KEY", loginResponse.token)
                startActivity(intent)
            } else {
                // التعامل مع حالة فاشلة
                showMessage("${resources.getString(R.string.rong_int)}")
            }
        }
    }

    private fun showMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

}
