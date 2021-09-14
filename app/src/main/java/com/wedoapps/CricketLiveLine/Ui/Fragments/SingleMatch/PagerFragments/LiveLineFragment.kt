package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wedoapps.CricketLiveLine.Adapter.LastBallsAdapter
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.ViewPagerActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentLiveLineBinding

class LiveLineFragment(val id: String) : Fragment(R.layout.fragment_live_line) {

    private lateinit var binding: FragmentLiveLineBinding
    private lateinit var viewModel: CricketGuruViewModel

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLiveLineBinding.bind(view)

        viewModel = (activity as ViewPagerActivity).viewModel

        val card1 = binding.card1
        val card3 = binding.card3
        val card4 = binding.card4
        viewModel.apply {

            getAllTeam1(id).observe(requireActivity(), {
                binding.tvPlayScore.text = it?.Score + " ( ${it?.Over} )"
            })

            getAllTeam2(id).observe(requireActivity(), {
                binding.tvOppScore.text = it?.Score + " ( ${it?.Over} )"
            })

            getRunRate(id).observe(requireActivity(), {
                Log.d(TAG, " RunRate: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card1.apply {
                    tvCrr.text = map["CRR"]
                    tvMessage.text = map["OtherInfo"]
                    tvRrr.text = map["RRR"]
                }
            })

            getBatsman1(id).observe(requireActivity(), {
                Log.d(TAG, "Batsman1: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card3.apply {
                    tvFirstBatsman.text = map["BatsmanName"]
                    tvFirstRun.text = map["Run"]
                    tvFirstBalls.text = map["Ball"]
                    tvFirst4s.text = map["4s"]
                    tvFirst6s.text = map["6s"]
                    tvFirstStrikeRate.text = map["SR"]
                }

            })

            getBatsman2(id).observe(requireActivity(), {
                Log.d(TAG, "Batsman2: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card3.apply {
                    tvSecondBatsman.text = map["BatsmanName"]
                    tvSecondRun.text = map["Run"]
                    tvSecondBalls.text = map["Ball"]
                    tvSecond4s.text = map["4s"]
                    tvSecond6s.text = map["6s"]
                    tvSecondStrikeRate.text = map["SR"]
                }
            })

            getBowlerInfo(id).observe(requireActivity(), {
                Log.d(TAG, " Bowler Info: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                val text =
                    map["BowlerName"] + " " + map["Wicket"] + "-" + map["Run"] + " " + "( ${map["Over"]} Over)"
                card3.tvBowlerInfo.text = text
            })

            getPartnership(id).observe(requireActivity(), {
                Log.d(TAG, " Partnership: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                card3.tvPartnership.text = map["Partnership"]
            })

            getBallXRun(id).observe(requireActivity(), {
                Log.d(TAG, " BallXRun: $it")
            })

            getSessionLambi(id).observe(requireActivity(), {
                Log.d(TAG, " SessionLambi: $it")
            })

            getLambiBallXRun(id).observe(requireActivity(), {
                Log.d(TAG, " LambiBallXRun: $it")
            })

            getLiveMatch(id).observe(requireActivity(), {
                Log.d(TAG, " LiveMatch: $it")
            })

            getSession(id).observe(requireActivity(), {
                Log.d(TAG, " Session: $it")
            })

            getLastBall(id).observe(requireActivity(), {
                Log.d(TAG, " LastBall: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card4.apply {
                    tvBall1.text = map["Ball1"]
                    tvBall2.text = map["Ball2"]
                    tvBall3.text = map["Ball3"]
                    tvBall4.text = map["Ball4"]
                    tvBall5.text = map["Ball5"]
                    tvBall6.text = map["Ball6"]
                }

            })

            getBallByBall(id).observe(requireActivity(), {
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                binding.tvDay.text = map["BallByBall"]
            })

            getOtherMessage(id).observe(requireActivity(), {
                Log.d(TAG, "Other Message: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                card3.tvLastWicket.text = map["Message"]
            })
        }
    }
}
