package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Match

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.MatchBetAdapter
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.BottomSheets.MatchBottomSheetFragment
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.BettingActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.PID
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentActiveMatchBinding


class ActiveMatchFragment : Fragment(R.layout.fragment_active_match),
    MatchBetAdapter.SetOn, MatchBottomSheetFragment.OnMatchBetListener {

    private lateinit var binding: FragmentActiveMatchBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var matchAdapter: MatchBetAdapter
    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentActiveMatchBinding.bind(view)

        id = arguments?.getString(ID).toString()
        viewModel = (activity as BettingActivity).viewModel

        matchAdapter = MatchBetAdapter(this)
        viewModel.getSpecificIdDetail(id).observe(requireActivity(), {
            binding.apply {
                tvTeam1.text = it.Team1
                tvTeam2.text = it.Team2
            }
        })

        viewModel.getAllMatchBet(id).observe(requireActivity(), {
            if (it.isEmpty()) {
                binding.tvNoActiveMatch.visibility = View.VISIBLE
                binding.rvActiveMatch.visibility = View.GONE
            } else {
                val betList = mutableListOf<MatchBet>()
                Log.d(TAG, "MatchDataTest: $it")
                binding.tvNoActiveMatch.visibility = View.GONE
                binding.rvActiveMatch.visibility = View.VISIBLE
                it.sortedBy { k -> k.playerName }
                it.forEach { data ->
                    val bet = MatchBet(row_type = 2, playerName = data.playerName)
                    if (betList.find { k -> k.playerName == bet.playerName && k.row_type == 2 } == null) {
                        betList.add(bet)
                    }
                    betList.addAll(data.matchBet!!)
                }
                Log.d(TAG, "BetList: $betList")
                matchAdapter.differ.submitList(betList)
            }
        })



        binding.rvActiveMatch.apply {
            setHasFixedSize(true)
            adapter = matchAdapter
        }



        binding.fabAddMatch.setOnClickListener {
            val matchSheet = MatchBottomSheetFragment()
            val bundle = Bundle()
            bundle.putString(ID, id)
            matchSheet.arguments = bundle
            matchSheet.setTargetFragment(this, 1)
            matchSheet.show(parentFragmentManager, matchSheet.tag)
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getAllMatchBet(id).observe(requireActivity(), {
            var temp = 0
            var temp1 = 0
            if (it.isNullOrEmpty()) {
                binding.tvTeam1Total.text = temp.toString()
                binding.tvTeam2Total.text = temp1.toString()
            } else {
                it.forEach { data1 ->
                    if (data1.matchBet.isNullOrEmpty()) {
                        viewModel.deleteMatchBet(data1)
                    }
                    data1.matchBet?.forEach { data ->
                        temp += data.team1Value!!
                        temp1 += data.team2Value!!
                        binding.tvTeam1Total.text = temp.toString()
                        binding.tvTeam2Total.text = temp1.toString()
                    }
                }
            }
        })
    }

    override fun onSheetClose(name: String) {
        viewModel.getAllMatchBet(id).observe(requireActivity(), {
            var temp = 0
            var temp1 = 0
            if (it.isNullOrEmpty()) {
                binding.tvTeam1Total.text = temp.toString()
                binding.tvTeam2Total.text = temp1.toString()
            } else {
                it.forEach { data1 ->
                    data1.matchBet?.forEach { data ->
                        temp += data.team1Value!!
                        temp1 += data.team2Value!!
                        binding.tvTeam1Total.text = temp.toString()
                        binding.tvTeam2Total.text = temp1.toString()
                    }
                }
            }
        })
    }

    fun newInstance(myString: String?): ActiveMatchFragment {
        val myFragment = ActiveMatchFragment()
        val args = Bundle()
        args.putString(ID, myString)
        myFragment.arguments = args
        return myFragment
    }

    override fun onDelete(matchBet: MatchBet, position: Int) {
        viewModel.deleteItemMatch(matchBet.id!!, matchBet.playerName!!)
        matchAdapter.notifyItemRemoved(position)
    }

    override fun onEdit(matchBet: MatchBet) {
        val matchSheet = MatchBottomSheetFragment()
        val bundle = Bundle()
        bundle.putParcelable(PID, matchBet)
        matchSheet.arguments = bundle
        matchSheet.setTargetFragment(this, 1)
        matchSheet.show(parentFragmentManager, matchSheet.tag)
    }

}