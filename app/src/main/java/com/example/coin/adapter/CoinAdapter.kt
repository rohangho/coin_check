package com.example.coin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.coin.R
import com.example.coin.databinding.IndividualCoinLayoutBinding
import com.example.coin.model.BaseCoinModel

class CoinAdapter: RecyclerView.Adapter<CoinAdapter.MyViewModel>() {

    private val coinList = ArrayList<BaseCoinModel>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewModel {


        val view =  IndividualCoinLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MyViewModel(view)
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.bind( coinList[position])
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        );
        anim.setDuration(1000);
        holder.itemView.startAnimation(anim);
    }


    override fun getItemCount(): Int {
        return  coinList.size
    }

    fun updateList(list: List<BaseCoinModel>) {
        coinList.clear()
        coinList.addAll(list)
        notifyDataSetChanged()
    }


    inner class MyViewModel(private val coinBinding: IndividualCoinLayoutBinding) : RecyclerView.ViewHolder(coinBinding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(model: BaseCoinModel) {

            coinBinding.coinName.text   = model.name
            coinBinding.coinAcronym.text = model.symbol
            if(model.isNew == true)
                coinBinding.newIcon.visibility = View.VISIBLE
            if(model.isActive == false)
            coinBinding.coinImg.setImageResource(R.drawable.inactive)
            else
            {
                if(model.type.equals("coin",true))
                    coinBinding.coinImg.setImageResource(R.drawable.coin_active)
                else
                    coinBinding.coinImg.setImageResource(R.drawable.token_active)

            }
        }


    }
}