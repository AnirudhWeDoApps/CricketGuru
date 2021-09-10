package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.PlayerListAdapter
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.MainActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentInfoBinding

class InfoFragment(val id: String) : Fragment(R.layout.fragment_info) {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel: CricketGuruViewModel
    private var isExpanded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        viewModel.getInfo(id).observe(viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated GetINFO: $it ")

            if (it.TeamSquade?.Team1.isNullOrBlank() && it.TeamSquade?.Team2.isNullOrBlank()) {
                binding.apply {
                    tvNoSquade.visibility = View.VISIBLE
                    infoTeam1.visibility = View.INVISIBLE
                    infoTeam2.visibility = View.INVISIBLE
                }
            } else {
                binding.apply {
                    tvNoSquade.visibility = View.GONE
                    infoTeam1.visibility = View.VISIBLE
                    infoTeam2.visibility = View.VISIBLE
                }
                val filterTeam1: List<String> =
                    it.TeamSquade!!.Team1!!.split(",").map { playerName ->
                        playerName.trim()
                    }

                val filterTeam2: List<String> =
                    it.TeamSquade!!.Team2!!.split(",").map { playerName ->
                        playerName.trim()
                    }

                val playerAdapter = PlayerListAdapter(filterTeam1)
                val playerAdapter2 = PlayerListAdapter(filterTeam2)

                binding.rvPlayerList1.apply {
                    setHasFixedSize(true)
                    adapter = playerAdapter
                }

                binding.rvPlayerList2.apply {
                    setHasFixedSize(true)
                    adapter = playerAdapter2
                }
            }

            binding.apply {

                tvInfoTeam1Name.text = it.Team1
                tvInfoTeam2Name.text = it.Team2


                infoTeam1.setOnClickListener {
                    if (!isExpanded) {
                        ivInfoTeam1Arrow.rotationX = 180F
                        rvPlayerList1.visibility = View.VISIBLE
                        isExpanded = true
                    } else {
                        ivInfoTeam1Arrow.rotationX = 0F
                        rvPlayerList1.visibility = View.GONE
                        isExpanded = false
                    }
                }

                infoTeam2.setOnClickListener {
                    if (!isExpanded) {
                        ivInfoTeam2Arrow.rotationX = 180F
                        rvPlayerList2.visibility = View.VISIBLE
                        isExpanded = true
                    } else {
                        ivInfoTeam2Arrow.rotationX = 0F
                        rvPlayerList2.visibility = View.GONE
                        isExpanded = false
                    }
                }

                tvSeries.text = it.Series
                tvMatch.text = it.NoOfMatch
                tvDate.text = it.CurrentDate
                tvVenue.text = it.Venue
                tvToss.text = it.TossInfo
                tvFirstInning.text = it.Avg1stInnings
                tvSecondInning.text = it.Avg2ndInnings
                tvHighestTotal.text = it.HighestTotal
                tvLowestTotal.text = it.LowestTotal
                tvHighestChased.text = it.HighestChased
                tvLowestDefending.text = it.LowestDefended
                tvHead.text = it.Head2Head
                tvTeamFirstNameShort.text = it.TeamForm!!.NameTeam1
                tvTeamTwoNameShort.text = it.TeamForm!!.NameTeam2
                formTeam1.text = it.TeamForm!!.FormTeam1
                formTeam2.text = it.TeamForm!!.FormTeam2
                tvUmpire.text = it.Umpire
                tvThirdUmpire.text = it.ThirdUmpire
                tvRefree.text = it.Referee
                tvMom.text = it.MOM
            }


        })


    }

}
