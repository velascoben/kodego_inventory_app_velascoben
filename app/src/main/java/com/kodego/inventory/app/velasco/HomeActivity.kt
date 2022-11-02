package com.kodego.inventory.app.velasco

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kodego.inventory.app.velasco.databinding.ActivityHomeBinding
import com.kodego.inventory.app.velasco.databinding.ActivityMainBinding
import com.kodego.inventory.app.velasco.databinding.CustomDialogBinding
import com.kodego.inventory.app.velasco.databinding.UpdateDialogBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding : ActivityHomeBinding
    lateinit var adapter : ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data Source
        var productList = mutableListOf<Products>(
            Products(R.drawable.ic_baseline_directions_car_24,"Car Parts","This is for different car parts.",5),
            Products(R.drawable.ic_baseline_coffee_24,"Coffee Brands","This is for different car coffee brands.",10),
            Products(R.drawable.ic_baseline_folder_open_24,"Folder Types","This is for different folder types.",15),
            Products(R.drawable.ic_baseline_directions_car_24,"Car Parts","This is for different car parts.",20),
            Products(R.drawable.ic_baseline_coffee_24,"Coffee Brands","This is for different car coffee brands.",25),
            Products(R.drawable.ic_baseline_folder_open_24,"Folder Types","This is for different folder types.",30),
            Products(R.drawable.ic_baseline_directions_car_24,"Car Parts","This is for different car parts.",35),
            Products(R.drawable.ic_baseline_coffee_24,"Coffee Brands","This is for different car coffee brands.",40),
            Products(R.drawable.ic_baseline_folder_open_24,"Folder Types","This is for different folder types.",45),
        )

        // Pass Data Source to Adapter
        adapter = ProductAdapter(productList)

        adapter.onItemClick = {
            val intent = Intent(this,ProductDetailActivity::class.java)
            intent.putExtra("itemImage",it.imageName)
            intent.putExtra("itemName",it.itemName)
            intent.putExtra("itemDescription",it.itemDescription)
            intent.putExtra("itemQuantity",it.itemQuantity)
            startActivity(intent)
        }

        adapter.onUpdateClick = { item : Products, position : Int ->
            showUpdateDialog(item, position)
        }

        binding.rvItem.adapter = adapter
        binding.rvItem.layoutManager = LinearLayoutManager(this)

        // Get data from previous screen
        var name : String? = intent.getStringExtra("nameID")

        binding.txtWelcome.text = "Welcome Back, $name!"

    }


    fun showUpdateDialog (item : Products, position : Int) {
        val dialog = MaterialAlertDialogBuilder(this)
//        val binding : UpdateDialogBinding = UpdateDialogBinding.inflate(layoutInflater)
        val binding : CustomDialogBinding = CustomDialogBinding.inflate(layoutInflater)
//        dialog.setContentView(binding.root)
        dialog.setView(binding.root)
//        dialog.show()
            .setTitle("UPDATE PRODUCT DETAILS")
            .setMessage("Enter changes in product details")
            .setPositiveButton("Update") { dialog, _ ->
                val name = binding.itemName.editText?.text.toString()
                val description = binding.itemDescription.editText?.text.toString()
                val quantity = binding.itemQuantity.editText?.text.toString()

                if(name!="") {
                    adapter.products[position].itemName = name
                }

                if(description != "") {
                    adapter.products[position].itemDescription = description
                }

                if(quantity != "") {
                    adapter.products[position].itemQuantity = quantity.toInt()
                }

                adapter.notifyDataSetChanged()

                /**
                 * Do as you wish with the data here --
                 * Download/Clone the repo from my Github to see the entire implementation
                 * using the link provided at the end of the article.
                 */

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                displayMessage("Operation Cancelled")
                dialog.dismiss()
            }
            .show()

//        binding.btnUpdate.setOnClickListener() {
//            var newQuantity : Int = binding.etQuantity.text.toString().toInt()
//            adapter.products[position].itemQuantity = newQuantity
//            adapter.notifyDataSetChanged()
//            dialog.dismiss()
//        }
    }
    private fun displayMessage(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}