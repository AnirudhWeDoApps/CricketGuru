package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.LastBallsAdapter
import com.wedoapps.CricketLiveLine.Model.LastBall
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.databinding.FragmentLiveLineBinding

class LiveLineFragment : Fragment(R.layout.fragment_live_line) {

    private lateinit var binding: FragmentLiveLineBinding
    private lateinit var ballAdapter: LastBallsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLiveLineBinding.bind(view)
        ballAdapter = LastBallsAdapter()
        addBalls()
        binding.card4.rvLastBalls.apply {
            setHasFixedSize(true)
            adapter = ballAdapter
        }


    }

    private fun addBalls() {
        val ball = mutableListOf(
            LastBall(1, "N"),
            LastBall(2, "0"),
            LastBall(3, "4"),
            LastBall(4, "4"),
            LastBall(5, "2"),
            LastBall(6, "3"),
            LastBall(7, "6"),
            LastBall(8, "1"),
            LastBall(9, "4"),
            LastBall(10, "6"),
            LastBall(11, "4"),
            LastBall(12, "6"),
            LastBall(13, "N"),
            LastBall(14, "0"),
            LastBall(15, "4"),
            LastBall(16, "4"),
            LastBall(17, "2"),
            LastBall(18, "3"),
            LastBall(19, "6"),
            LastBall(20, "1"),
            LastBall(21, "4"),
            LastBall(22, "6"),
            LastBall(13, "4"),
            LastBall(24, "6")
        )
        ballAdapter.differ.submitList(ball)
    }

}