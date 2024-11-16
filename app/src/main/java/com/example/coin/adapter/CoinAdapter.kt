package com.example.coin.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CoinAdapter: RecyclerView.Adapter<CoinAdapter.MyViewModel>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewModel {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        TODO("Not yet implemented")
    }


    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    inner class MyViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}