package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.CheckGraphAdapter
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBookShowModel
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.SESSION_ID
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentCheckGraphBinding

class CheckGraphFragment : Fragment(R.layout.fragment_check_graph) {

    private lateinit var binding: FragmentCheckGraphBinding
    lateinit var viewModel: CricketGuruViewModel
    private lateinit var id: String
    private lateinit var sessionID: String
    private val sessionBookShowModel = arrayListOf<Int>()
    private val modelList = arrayListOf<SessionBookShowModel>()
    private val sessionBetList = arrayListOf<SessionBet>()
    private lateinit var checkGraphAdapter: CheckGraphAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckGraphBinding.bind(view)

        id = arguments?.getString(ID).toString()
        sessionID = arguments?.getString(SESSION_ID).toString()

        viewModel = (activity as SessionEntryActivity).viewModel

        checkGraphAdapter = CheckGraphAdapter(requireContext())

        viewModel.getAllSessions(sessionID).observe(requireActivity(), {
            Log.d(TAG, "SessionBet Data: $it")
            if (it.isNullOrEmpty()) {
                binding.tvNoCG.visibility = View.VISIBLE
                binding.rvCheckGraph.visibility = View.GONE
            } else {
                binding.tvNoCG.visibility = View.GONE
                binding.rvCheckGraph.visibility = View.VISIBLE
                it.forEach { data ->
                    sessionBetList.addAll(data.sessionBet!!)
                }

                var value: Int
                var min = 0
                var max = 0

                sessionBetList.forEach { item ->

                    max = item.actualScore!!
                }
                sessionBetList.forEach { data ->
                    value = data.actualScore!!
                    if (value < max) {
                        min = value
                    }
                }

                sessionBookShowModel.add(0, max)
                sessionBookShowModel.add(1, min)

                calculateArray(min, max)
                binding.apply {
                    rvCheckGraph.setHasFixedSize(true)
                    rvCheckGraph.adapter = checkGraphAdapter
                }

            }
        })
    }

    private fun calculateArray(min: Int, max: Int) {
        modelList.clear()
        var maxRun: Int
        var minRun: Int

        if (min == 0) {
            maxRun = max
            minRun = max
        } else {
            maxRun = max
            minRun = min
        }

        maxRun += 4
        minRun -= 4

        val length = maxRun - minRun + 1
        for (i in 0 until length) {
            val bookShowModel = SessionBookShowModel()
            bookShowModel.run = minRun + i
            for (i in sessionBetList.size - 1 downTo 0) {
                if (bookShowModel.run == sessionBetList[i].actualScore) {
                    bookShowModel.isLastRecord = true
                }
                break
            }

            bookShowModel.amount = calculateAmount(bookShowModel.run!!)
            modelList.add(bookShowModel)
        }
        Log.d(TAG, "ModelList: $modelList")
        checkGraphAdapter.differ.submitList(modelList)

    }

    private fun calculateAmount(run: Int): Double {
        var cal_amount = 0.0
        if (sessionBetList.isNotEmpty()) {
            for (i in sessionBetList.indices) {
                val sessionBet = sessionBetList[i]

                cal_amount += if (run >= sessionBet.actualScore!!) {
                    sessionBet.amount!! + sessionBet.amount!! * sessionBet.commission!! / 100
                } else {
                    sessionBet.amount!! * sessionBet.commission!! / 100 - sessionBet.amount!!
                }
                cal_amount += if (run >= sessionBet.actualScore!!) {
                    sessionBet.amount!! * sessionBet.commission!! / 100 - sessionBet.amount!!
                } else {
                    sessionBet.amount!! * sessionBet.commission!! / 100 + sessionBet.amount!!
                }
            }
        }
        return cal_amount
    }

    fun newInstance(myString: String?, sessionID: String?): CheckGraphFragment {
        val myFragment = CheckGraphFragment()
        val args = Bundle()
        args.putString(ID, myString)
        args.putString(SESSION_ID, sessionID)
        myFragment.arguments = args
        return myFragment
    }
}