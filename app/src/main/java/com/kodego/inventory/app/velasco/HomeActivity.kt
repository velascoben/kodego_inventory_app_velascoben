package com.kodego.inventory.app.velasco

import android.app.Activity
import android.app.Dialog
import android.app.appsearch.SearchResults
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kodego.inventory.app.velasco.databinding.ActivityHomeBinding
import com.kodego.inventory.app.velasco.databinding.AddItemDialogBinding
import com.kodego.inventory.app.velasco.databinding.UpdateItemDialogBinding
import kotlinx.android.synthetic.main.add_item_dialog.*
import kotlinx.android.synthetic.main.row_item.*

class HomeActivity : AppCompatActivity() {
    var pickedPhoto : Uri? = null
    var pickedBitmap : Bitmap? = null

    lateinit var binding : ActivityHomeBinding
    lateinit var adapter : ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data Source
        var productList : MutableList<Products> = mutableListOf<Products>(
            Products(R.drawable.product1,"LENOVO RESCUER Y700","• Intel core i7-6700HQ (8CPUs) 4corse/8threads 2.60ghz up to 3.5GHz\n" +
                    "• 8GB RAM DDR4 2133 MHz\n" +
                    "• 128gb ssd flash\n" +
                    "• 1tb HDD\n" +
                    "• NVIDIA Geforce GTX 960m\n" +
                    "• 4gb dedicated vram DDR5\n" +
                    "• Intel HD Graphics 530\n" +
                    "• 15.6-inch display\n" +
                    "• (1920 x 1080) Full HD resolution\n" +
                    "• 8GB total video memory\n" +
                    "• Graphics Clock 1097 Mhz\n" +
                    "• Backlit keyboard",5),
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

        adapter.onDeleteClick = { item : Products, position : Int ->
            adapter.products.removeAt(position)
            adapter.notifyDataSetChanged()
        }

        binding.btnAddItem.setOnClickListener() {
            showAddItemDialog()
        }

        binding.rvItem.adapter = adapter
        binding.rvItem.layoutManager = LinearLayoutManager(this)

        // Get data from previous screen
        var name : String? = intent.getStringExtra("nameID")

        binding.txtWelcome.text = "Welcome Back, $name!"

    }

    fun pickPhoto(view: View) {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        } else {
            val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode : Int,
        permissions : Array<out String>,
        grantResults : IntArray
    ) {
        if(requestCode == 1) {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,2)
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            pickedPhoto = data.data
            if(pickedPhoto != null) {
                if(Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(this.contentResolver,pickedPhoto!!)
                    pickedBitmap = ImageDecoder.decodeBitmap(source)
                    imgButtonAdd.setImageBitmap(pickedBitmap)
                }
            }
        }
    }




    fun showUpdateDialog (item : Products, position : Int) {
        val dialog = MaterialAlertDialogBuilder(this)
//        val binding : UpdateDialogBinding = UpdateDialogBinding.inflate(layoutInflater)
        val binding : UpdateItemDialogBinding = UpdateItemDialogBinding.inflate(layoutInflater)
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

    fun showAddItemDialog () {
        val REQUEST_CODE = 100
        val dialog = MaterialAlertDialogBuilder(this)
//        val binding : UpdateDialogBinding = UpdateDialogBinding.inflate(layoutInflater)
        val binding : AddItemDialogBinding = AddItemDialogBinding.inflate(layoutInflater)
//        dialog.setContentView(binding.root)
        dialog.setView(binding.root)
//        dialog.show()
            .setTitle("ADD PRODUCT DETAILS")
            .setMessage("Enter new product details")
            .setPositiveButton("Add") { dialog, _ ->
                val name = binding.itemNewName.editText?.text.toString()
                val description = binding.itemNewDescription.editText?.text.toString()
                val quantity = binding.itemNewQuantity.editText?.text.toString()

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