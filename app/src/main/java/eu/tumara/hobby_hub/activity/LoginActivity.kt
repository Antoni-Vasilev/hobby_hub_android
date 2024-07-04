package eu.tumara.hobby_hub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import eu.tumara.hobby_hub.R

class LoginActivity : AppCompatActivity() {

    private lateinit var btnRegisterNow: TextView
    private lateinit var btnForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        init()
    }

    private fun init() {
        btnRegisterNow = findViewById(R.id.btnRegisterNow)
        btnForgotPassword = findViewById(R.id.btnForgotPassword)

        btnRegisterNow.setOnClickListener {
            showRegister()
        }
        btnForgotPassword.setOnClickListener {
            showForgotPassword()
        }
    }

    private fun showRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun showForgotPassword() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
}