package com.kodego.inventory.app.velasco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kodego.inventory.app.velasco.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from HomeActivity

        var itemName : String? = intent.getStringExtra("itemName")
        var itemDescription : String? = intent.getStringExtra("itemDescription")
        var itemImage : Int = intent.getIntExtra("itemImage",0)
        var itemQuantity : Int = intent.getIntExtra("itemQuantity",0)

        binding.itemImage.setImageResource(itemImage)
        binding.itemName.text = itemName
        binding.itemDescription.text = itemDescription
        binding.itemQuantity.text = "Stock: ${itemQuantity.toString()}"
    }
}