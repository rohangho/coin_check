package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.example.Products
import com.example.myapplication.databinding.IndividualProductsBinding

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.MyViewModel>() {

    private val productList = ArrayList<Products>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewModel {
        val view =
            IndividualProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewModel(view)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.MyViewModel, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateList(list: Array<Products>) {
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }


    inner class MyViewModel(private val productBinding: IndividualProductsBinding) :
        RecyclerView.ViewHolder(productBinding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(model: Products) {

            productBinding.productName.text = model.name
            productBinding.productTag.text = model.tagline
            productBinding.ratingbar.text = model.rating.toString()
        }


    }

}