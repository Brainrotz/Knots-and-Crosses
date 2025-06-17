package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var registerButton: Button
    private lateinit var goToLoginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailField = findViewById(R.id.editTextRegisterEmail)
        passwordField = findViewById(R.id.editTextRegisterPassword)
        confirmPasswordField = findViewById(R.id.editTextRegisterConfirmPassword)
        registerButton = findViewById(R.id.registerButton)
        goToLoginText = findViewById(R.id.goToLoginText)

        registerButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save credentials in SharedPreferences
            val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("registered_email", email)
                putString("registered_password", password)
                apply()
            }

            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()

            // Redirect to login page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        goToLoginText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
