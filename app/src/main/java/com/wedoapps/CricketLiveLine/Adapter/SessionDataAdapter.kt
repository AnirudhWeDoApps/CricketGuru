package com.wedoapps.CricketLiveLine.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionData
import com.wedoapps.CricketLiveLine.databinding.LayoutMatchBetBinding

class SessionDataAdapter(private val listener: SetOn) :
    RecyclerView.Adapter<SessionDataAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(private val binding: LayoutMatchBetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sessionData: SessionData) {
            binding.apply {
                tvplayer.text = sessionData.playerName
                val sessionBetAdapter = SessionBetAdapter(object : SessionBetAdapter.SetOn {
                    override fun onDelete(sessionBet: SessionBet, position: Int) {
                        listener.onDeleteSessionBet(sessionBet, position)
                    }

                    override fun onEdit(sessionBet: SessionBet) {
                        listener.onEditSessionBet(sessionBet)
                    }
                })
                sessionBetAdapter.differ.submitList(sessionData.sessionBet)
                rvInnerLa.apply {
                    setHasFixedSize(true)
                    adapter = sessionBetAdapter
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SessionDataAdapter.SectionViewHolder {
        val binding =
            LayoutMatchBetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionDataAdapter.SectionViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val differCallback = object : DiffUtil.ItemCallback<SessionData>() {
        override fun areItemsTheSame(oldItem: SessionData, newItem: SessionData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SessionData, newItem: SessionData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOn {
        fun onDeleteSessionBet(sessionBet: SessionBet, position: Int)
        fun onEditSessionBet(sessionBet: SessionBet)
    }

}