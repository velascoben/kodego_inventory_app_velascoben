package com.kodego.inventory.app.velasco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodego.inventory.app.velasco.databinding.ActivityHomeBinding
import com.kodego.inventory.app.velasco.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data Source
        var productList = mutableListOf<Products>(
            Products(R.drawable.ic_baseline_directions_car_24,"Car Parts","This is for different car parts."),
            Products(R.drawable.ic_baseline_coffee_24,"Coffee Brands","This is for different car coffee brands."),
            Products(R.drawable.ic_baseline_folder_open_24,"Folder Types","This is for different folder types."),
            Products(R.drawable.ic_baseline_directions_car_24,"Car Parts","This is for different car parts."),
            Products(R.drawable.ic_baseline_coffee_24,"Coffee Brands","This is for different car coffee brands."),
            Products(R.drawable.ic_baseline_folder_open_24,"Folder Types","This is for different folder types."),
            Products(R.drawable.ic_baseline_directions_car_24,"Car Parts","This is for different car parts."),
            Products(R.drawable.ic_baseline_coffee_24,"Coffee Brands","This is for different car coffee brands."),
            Products(R.drawable.ic_baseline_folder_open_24,"Folder Types","This is for different folder types."),
        )

        // Pass Data Source to Adapter
        val adapter = ProductAdapter(productList)

        adapter.onItemClick = {
            val intent = Intent(this,ProductDetailActivity::class.java)
            intent.putExtra("itemImage",it.imageName)
            intent.putExtra("itemName",it.itemName)
            intent.putExtra("itemDescription",it.itemDescription)
            startActivity(intent)
        }

        binding.rvItem.adapter = adapter
        binding.rvItem.layoutManager = LinearLayoutManager(this)

        // Get data from previous screen
        var name : String? = intent.getStringExtra("nameID")

        binding.txtWelcome.text = "Welcome Back, $name!"
    }
}