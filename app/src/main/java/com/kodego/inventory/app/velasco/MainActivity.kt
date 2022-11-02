package com.kodego.inventory.app.velasco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kodego.inventory.app.velasco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Login
        binding.btnLogin.setOnClickListener() {
            var userName : String = binding.etUsername.text.toString()
            var userPass : String = binding.etPassword.text.toString()
            checkCredential(userName,userPass)
        }
    }

    fun checkCredential(userName : String, userPass : String) : Boolean {
        val correctUsername : String = "admin"
        val correctPassword : String = "admin123"

        val correctUsername1 : String = "ben"
        val correctPassword1 : String = "ben123"

        if((correctUsername == userName) && (correctPassword == userPass)) {
//            var name : String = "Ben"
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("nameID", correctUsername)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext, "Logging in...", Toast.LENGTH_SHORT).show()
            return true
        } else if((correctUsername1 == userName) && (correctPassword1 == userPass)) {
//            var name : String = "Ben"
            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("nameID",correctUsername1)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext,"Logged in...",Toast.LENGTH_SHORT).show()
            return true
        } else {
            Toast.makeText(applicationContext,"Invalid Credentials",Toast.LENGTH_SHORT).show()
            return false
        }
    }
}