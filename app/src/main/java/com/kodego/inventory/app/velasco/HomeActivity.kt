package com.kodego.inventory.app.velasco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kodego.inventory.app.velasco.databinding.ActivityHomeBinding
import com.kodego.inventory.app.velasco.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from previous screen
        var name : String? = intent.getStringExtra("nameID")

        //binding.txtWelcome.text = "Welcome Back, $name!"
    }
}