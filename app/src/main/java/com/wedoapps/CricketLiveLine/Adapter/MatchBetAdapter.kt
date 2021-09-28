package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.databinding.LayoutMatchBetBinding

class MatchBetAdapter(val listener: SetOn) :
    RecyclerView.Adapter<MatchBetAdapter.MatchBetViewHolder>() {

    inner class MatchBetViewHolder(private val binding: LayoutMatchBetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var firestore = FirebaseFirestore.getInstance()
        private val firestoreRef = firestore.collection("MatchList")

        @SuppressLint("SetTextI18n")
        fun bind(matchBet: MatchBet) {
            binding.apply {

                firestoreRef.document(matchBet.matchID.toString())
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            Log.w(Constants.TAG, "Listen Failed", error)
                            return@addSnapshotListener
                        }

                        if (value != null) {
                            binding.tvTeam1.text = value.get("Team1").toString()
                            binding.tvTeam2.text = value.get("Team2").toString()

                            when (matchBet.team) {
                                value.get("Team1") -> {
                                    binding.tvTeam1.background =
                                        ContextCompat.getDrawable(
                                            itemView.context,
                                            R.drawable.trans_round_rect_border
                                        )
                                }
                                value.get("Team2") -> {
                                    binding.tvTeam2.background =
                                        ContextCompat.getDrawable(
                                            itemView.context,
                                            R.drawable.trans_round_rect_border
                                        )
                                }
                                else -> {
                                    binding.tvTeam1.background =
                                        ContextCompat.getDrawable(
                                            itemView.context,
                                            R.drawable.trans_round_rect_border
                                        )
                                    binding.tvTeam2.background =
                                        ContextCompat.getDrawable(
                                            itemView.context,
                                            R.drawable.trans_round_rect_border
                                        )
                                }
                            }

                        } else {
                            Log.d(Constants.TAG, "NO DATA")
                        }
                    }

                tvTeam1Value.text = matchBet.team1Value.toString()
                tvTeam2Value.text = matchBet.team2Value.toString()
                tvType.text = matchBet.type
                tvPlayerName.text = matchBet.playerName
                tvRateXAmt.text = "${matchBet.amount} * ${matchBet.rate}"
                tvTime.text = matchBet.date

                btnDelete.setOnClickListener {
                    listener.onDelete(matchBet)
                }


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchBetViewHolder {
        val binding =
            LayoutMatchBetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchBetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchBetViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<MatchBet>() {
        override fun areItemsTheSame(oldItem: MatchBet, newItem: MatchBet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MatchBet, newItem: MatchBet): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOn {
        fun onDelete(matchBet: MatchBet)
//        fun addValues(team1Value: Int, team2Value: Int)
    }
}