package com.wedoapps.CricketLiveLine.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Utils.Constants.PLAYER
import com.wedoapps.CricketLiveLine.databinding.RvPlayerItemLayoutBinding


class PlayerListAdapter(private val data: List<String>) :
    RecyclerView.Adapter<PlayerListAdapter.PlayerListViewHolder>() {

    inner class PlayerListViewHolder(private val binding: RvPlayerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val storageRef = FirebaseStorage.getInstance().reference

        fun bind(name: String) {
            binding.apply {
                tvPlayerName.text = name

                storageRef.child(PLAYER + name.trim() + ".png")
                    .downloadUrl.addOnSuccessListener { uri ->

                        Glide.with(itemView)
                            .load(uri)
                            .placeholder(R.drawable.ic_cricketer_place_holder)
                            .into(ivPlayerImage)

                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerListViewHolder {
        val binding =
            RvPlayerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerListViewHolder, position: Int) {
        val currentItem = data[position]

        if (currentItem.isNotEmpty()) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}