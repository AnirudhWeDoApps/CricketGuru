package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.wedoapps.CricketLiveLine.Model.HomeMatch
import com.wedoapps.CricketLiveLine.Model.Score
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.HomeCardItemLayoutBinding

class HomeCardAdapter(private val listener: SetOnClick) :
    RecyclerView.Adapter<HomeCardAdapter.HomeCardViewHolder>() {

    inner class HomeCardViewHolder(private val binding: HomeCardItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var firestore = FirebaseFirestore.getInstance()
        private val firestoreRef = firestore.collection("MatchList")

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = differ.currentList[position]
                    if (item != null) {
                        listener.onClick(item)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(match: HomeMatch) {

            binding.apply {
                tvMatch.text = match.MatchDetail
                tvTime.text = match.CurrentDate
                tvMatchStatus.text = match.MatchStatus
                tvFirstTeam.text = match.Team1
                tvSecondTeam.text = match.Team2
                tvFStrike.text = "0"
                tvSStrike.text = "1"
                tvDayStatus.text = match.MatchResult

                Glide.with(itemView)
                    .load(R.mipmap.ic_launcher_round)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivFirstTeam)

                Glide.with(itemView)
                    .load(R.mipmap.ic_launcher_round)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivSecondTeam)
            }

            firestoreRef.document(match.id!!).collection("LiveMatch").document("ScoreTeam1")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w(TAG, "Listen Failed", error)
                        return@addSnapshotListener
                    }

                    if (value != null) {
                        val allTeam1 = value.toObject(Score::class.java)
                        binding.apply {
                            tvFInn.text = value.get("Score").toString()
                            tvOver.text = value.get("Over").toString() + "Over"
                        }
//                    val allTeam1 = ArrayList<Score>()
//                    allTeam1?.add(allTeam1)
                        Log.d(TAG, "team1: $allTeam1")
                    } else {
                        Log.d(TAG, "No Data")
                    }
                }

            firestoreRef.document(match.id!!).collection("LiveMatch").document("ScoreTeam2")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w(TAG, "Listen Failed", error)
                        return@addSnapshotListener
                    }

                    if (value != null) {
                        val allTeam2 = value.toObject(Score::class.java)
                        binding.apply {
                            tvSecondFInn.text = value.get("Score").toString()
                            tvSecondOver.text = value.get("Over").toString() + " Over"
                        }
                        Log.d(TAG, "team2: $allTeam2")
                    } else {
                        Log.d(TAG, "No Data")
                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCardViewHolder {
        val binding =
            HomeCardItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCardViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<HomeMatch>() {
        override fun areItemsTheSame(oldItem: HomeMatch, newItem: HomeMatch) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: HomeMatch, newItem: HomeMatch) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOnClick {
        fun onClick(match: HomeMatch)
    }

}