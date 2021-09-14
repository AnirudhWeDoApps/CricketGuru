package com.wedoapps.CricketLiveLine.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.databinding.LastBallsItemLayoutBinding

class LastBallsAdapter(private val data: MutableList<String>) :
    RecyclerView.Adapter<LastBallsAdapter.LastBallViewHolder>() {

    inner class LastBallViewHolder(private val binding: LastBallsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ball: String) {
            binding.tvBall.apply {
                text = ball
                background = when (ball) {
                    "0" -> ContextCompat.getDrawable(context, R.drawable.circle_bg)
                    "N" -> ContextCompat.getDrawable(context, R.drawable.no_circle_bg)
                    "4" -> ContextCompat.getDrawable(context, R.drawable.four_circle_bg)
                    "6" -> ContextCompat.getDrawable(context, R.drawable.six_circle_bg)
                    "W" -> ContextCompat.getDrawable(context, R.drawable.no_circle_bg)
                    else -> ContextCompat.getDrawable(context, R.drawable.circle_bg)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastBallViewHolder {
        val binding =
            LastBallsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LastBallViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LastBallViewHolder, position: Int) {
        val currentItem = data[position]

        if (currentItem.isNotEmpty()) {
            holder.bind(currentItem)
        } else {
            holder.bind("")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}