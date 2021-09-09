package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.BowlerListAdapter
import com.wedoapps.CricketLiveLine.Adapter.ScoreCardBattingAdapter
import com.wedoapps.CricketLiveLine.Adapter.WicketListAdapter
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.MainActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentScorecardBinding

class ScorecardFragment(val id: String) : Fragment(R.layout.fragment_scorecard) {

    private lateinit var binding: FragmentScorecardBinding
    private var isExpanded = false
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var scoreCardBattingAdapter: ScoreCardBattingAdapter
    private lateinit var scoreCardBattingAdapter2: ScoreCardBattingAdapter
    private lateinit var bowlerListAdapter: BowlerListAdapter
    private lateinit var bowlerListAdapter2: BowlerListAdapter
    private lateinit var wicketListAdapter: WicketListAdapter
    private lateinit var wicketListAdapter2: WicketListAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScorecardBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        scoreCardBattingAdapter = ScoreCardBattingAdapter()
        scoreCardBattingAdapter2 = ScoreCardBattingAdapter()
        bowlerListAdapter = BowlerListAdapter()
        bowlerListAdapter2 = BowlerListAdapter()
        wicketListAdapter = WicketListAdapter()
        wicketListAdapter2 = WicketListAdapter()

        binding.linearLayout1.setOnClickListener {
            if (!isExpanded) {
                binding.apply {
                    ivScoreTeam1Arrow.rotationX = 180F
                    team1ScoreHeader.root.visibility = View.VISIBLE
                    rv1Team1.visibility = View.VISIBLE
                    tv1Extras.visibility = View.VISIBLE
                    ivBowlingImg.visibility = View.VISIBLE
                    textView9.visibility = View.VISIBLE
                    team1BowlerHeader.root.visibility = View.VISIBLE
                    rvBowlingTeam1.visibility = View.VISIBLE
                    ivWicketImg.visibility = View.VISIBLE
                    textView11.visibility = View.VISIBLE
                    team1FowHeader.root.visibility = View.VISIBLE
                    rvWicketTeam1.visibility = View.VISIBLE

                }
                isExpanded = true
            } else {
                binding.apply {
                    ivScoreTeam1Arrow.rotationX = 0F
                    team1ScoreHeader.root.visibility = View.GONE
                    rv1Team1.visibility = View.GONE
                    tv1Extras.visibility = View.GONE
                    ivBowlingImg.visibility = View.GONE
                    textView9.visibility = View.GONE
                    team1BowlerHeader.root.visibility = View.GONE
                    rvBowlingTeam1.visibility = View.GONE
                    ivWicketImg.visibility = View.GONE
                    textView11.visibility = View.GONE
                    team1FowHeader.root.visibility = View.GONE
                    rvWicketTeam1.visibility = View.GONE
                }
                isExpanded = false
            }
        }

        binding.linearLayout2.setOnClickListener {
            if (!isExpanded) {
                binding.apply {
                    ivScoreTeam2Arrow.rotationX = 180F
                    team2ScoreHeader.root.visibility = View.VISIBLE
                    rv1Team2.visibility = View.VISIBLE
                    tv2Extras.visibility = View.VISIBLE
                    ivBall2Img.visibility = View.VISIBLE
                    textView10.visibility = View.VISIBLE
                    team2BowlerHeader.root.visibility = View.VISIBLE
                    rvBowlingTeam2.visibility = View.VISIBLE
                    ivWicketImg2.visibility = View.VISIBLE
                    textView12.visibility = View.VISIBLE
                    team2FowHeader.root.visibility = View.VISIBLE
                    rvWicketTeam2.visibility = View.VISIBLE
                }
                isExpanded = true
            } else {
                binding.apply {
                    ivScoreTeam2Arrow.rotationX = 0F
                    team2ScoreHeader.root.visibility = View.GONE
                    rv1Team2.visibility = View.GONE
                    tv2Extras.visibility = View.GONE
                    ivBall2Img.visibility = View.GONE
                    textView10.visibility = View.GONE
                    team2BowlerHeader.root.visibility = View.GONE
                    rvBowlingTeam2.visibility = View.GONE
                    ivWicketImg2.visibility = View.GONE
                    textView12.visibility = View.GONE
                    team2FowHeader.root.visibility = View.GONE
                    rvWicketTeam2.visibility = View.GONE
                }
                isExpanded = false
            }
        }

        viewModel.apply {
            getAllTeam1(id).observe(viewLifecycleOwner, {
                binding.tvScoreTeam1Score.text = it.Score + " ( ${it.Over} )"
            })

            getAllTeam2(id).observe(viewLifecycleOwner, {
                binding.tvScoreTeam2Score.text = it.Score + " ( ${it.Over} )"
            })

            getSpecificIdDetail(id).observe(viewLifecycleOwner, {
                binding.tvScoreTeam1Name.text = it.Team1
                binding.tvScoreTeam2Name.text = it.Team2

            })

            getTeam1Extras(id).observe(viewLifecycleOwner, {
                Log.d(TAG, "extras 1: $it")
                binding.tv1Extras.text = getString(R.string.extras) + it
            })

            getTeam2Extras(id).observe(viewLifecycleOwner, {
                Log.d(TAG, "extras 2: $it")
                binding.tv2Extras.text = getString(R.string.extras) + it
            })

            getScoreDetails1(id).observe(viewLifecycleOwner, {
                Log.d(TAG, "onViewCreated ScoreCardTeam1: ${it.playerScore}")
                scoreCardBattingAdapter.differ.submitList(it.playerScore)

            })

            getScoreDetails2(id).observe(viewLifecycleOwner, {
                Log.d(TAG, "onViewCreated ScoreCardTeam2: ${it.playerScore}")
                scoreCardBattingAdapter2.differ.submitList(it.playerScore2)
            })

            getBowlerList2(id).observe(viewLifecycleOwner, {
                Log.d(TAG, "onViewCreated Bowler List 2: ${it.team2List}")
                bowlerListAdapter2.differ.submitList(it.team2List)
            })

            getBowlerList1(id).observe(viewLifecycleOwner, {
                Log.d(TAG, "onViewCreated Bowler List 1: ${it.team1List}")
                bowlerListAdapter.differ.submitList(it.team1List)
            })

            getWicketList1(id).observe(viewLifecycleOwner, {
                Log.d(TAG, "onViewCreated WIcket List 1: ${it.wicketList1}")
                wicketListAdapter.differ.submitList(it.wicketList1)
            })

            getWicketList2(id).observe(viewLifecycleOwner, {
                Log.d(TAG, "onViewCreated WIcket List 2: ${it.wicketList2}")
                wicketListAdapter2.differ.submitList(it.wicketList2)
            })
        }


        binding.rv1Team1.apply {
            setHasFixedSize(true)
            adapter = scoreCardBattingAdapter
        }

        binding.rv1Team2.apply {
            setHasFixedSize(true)
            adapter = scoreCardBattingAdapter2
        }

        binding.rvBowlingTeam1.apply {
            setHasFixedSize(true)
            adapter = bowlerListAdapter
        }

        binding.rvBowlingTeam2.apply {
            setHasFixedSize(true)
            adapter = bowlerListAdapter2
        }

        binding.rvWicketTeam1.apply {
            setHasFixedSize(true)
            adapter = wicketListAdapter
        }

        binding.rvWicketTeam2.apply {
            setHasFixedSize(true)
            adapter = wicketListAdapter2
        }
    }
}