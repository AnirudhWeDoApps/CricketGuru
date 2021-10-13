package com.wedoapps.CricketLiveLine.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.wedoapps.CricketLiveLine.Model.HomeMatch
import com.wedoapps.CricketLiveLine.Model.Score
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.HomeCardItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeCardAdapter(private val listener: SetOnClick) :
    RecyclerView.Adapter<HomeCardAdapter.HomeCardViewHolder>() {

    inner class HomeCardViewHolder(private val binding: HomeCardItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val storageRef = FirebaseStorage.getInstance().reference
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

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(match: HomeMatch) {

            binding.apply {
                tvMatch.text = match.MatchDetail
                tvMatchStatus.text = match.MatchStatus
                tvFirstTeam.text = match.Team1
                tvSecondTeam.text = match.Team2
                tvDayStatus.text = match.MatchResult
                val sdf =
                    SimpleDateFormat("dd MMMM, h:mm a", Locale.ENGLISH)
                val cal = Calendar.getInstance(Locale.ENGLISH)
                cal.timeInMillis = match.MatchDate!! * 1000L
                tvTime.text = sdf.format(cal.time)


                storageRef.child("FlagIcon/" + match.Team1?.trim { it <= ' ' } + ".png")
                    .downloadUrl.addOnSuccessListener { uri ->

                        Glide.with(itemView.context)
                            .load(uri)
                            .centerCrop()
                            .placeholder(R.drawable.imgpsh_fullsize_anim)
                            .into(ivFirstTeam)
                    }.addOnFailureListener {
                        ivFirstTeam.setImageDrawable(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.imgpsh_fullsize_anim
                            )
                        )
                    }

                storageRef.child("FlagIcon/" + match.Team2?.trim { it <= ' ' } + ".png")
                    .downloadUrl.addOnSuccessListener { uri ->

                        Glide.with(itemView.context)
                            .load(uri)
                            .centerCrop()
                            .placeholder(R.drawable.imgpsh_fullsize_anim)
                            .into(ivSecondTeam)
                    }.addOnFailureListener {
                        ivSecondTeam.setImageDrawable(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.imgpsh_fullsize_anim
                            )
                        )
                    }
            }

            firestoreRef.document(match.id!!).collection("MatchRate").document("Match")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w(TAG, "Listen Failed", error)
                        return@addSnapshotListener
                    }

                    if (value != null) {
                        binding.apply {
                            tvFStrike.text = value.get("Rate1").toString()
                            tvSStrike.text = value.get("Rate2").toString()
                        }
                    }
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
                            tvOver.text = value.get("Over").toString() + " Over"
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