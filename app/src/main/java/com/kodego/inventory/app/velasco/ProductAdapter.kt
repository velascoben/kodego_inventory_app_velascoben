package com.kodego.inventory.app.velasco

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kodego.inventory.app.velasco.databinding.RowItemBinding

class ProductAdapter(val products : List<Products>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var onItemClick : ((Products) -> Unit)? = null

    inner class ProductViewHolder(val binding:RowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowItemBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.apply {
            imgProduct.setImageResource(products[position].imageName)
            tvItemName.text = products[position].itemName
            tvDescription.text = products[position].itemDescription
        }

        holder.itemView.setOnClickListener() {
            onItemClick?.invoke(products[position])
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

}