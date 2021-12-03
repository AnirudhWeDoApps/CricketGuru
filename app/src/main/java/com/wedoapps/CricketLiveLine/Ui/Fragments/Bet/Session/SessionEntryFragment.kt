package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.SessionDataAdapter
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionData
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.BottomSheets.CurrentSessionBottomFragment
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.Utils.Constants.SESSION_ID
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentSessionEntryBinding

class SessionEntryFragment : Fragment(R.layout.fragment_session_entry),
    SessionDataAdapter.SetOn {

    private lateinit var binding: FragmentSessionEntryBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var sessionAdapter: SessionDataAdapter
    private lateinit var id: String
    private lateinit var sessionID: String
    private val partyIDList = hashSetOf<String>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSessionEntryBinding.bind(view)

        id = arguments?.getString(Constants.ID).toString()
        sessionID = arguments?.getString(SESSION_ID).toString()

        viewModel = (activity as SessionEntryActivity).viewModel

        sessionAdapter = SessionDataAdapter(this)

        viewModel.getAllSessionsList(sessionID).observe(requireActivity(), {
            if (it.isEmpty()) {
                binding.tvNoCurrentSession.visibility = View.VISIBLE
                binding.rvCurrentSession.visibility = View.GONE
            } else {
                val sessionBetArrayList = arrayListOf<SessionBet>()
                val sessionDataArrayList = arrayListOf<SessionData>()
                sessionBetArrayList.addAll(it)
                binding.tvNoCurrentSession.visibility = View.GONE
                binding.rvCurrentSession.visibility = View.VISIBLE
                for (i in sessionBetArrayList.indices) {
                    val sessionBet = sessionBetArrayList[i]
                    partyIDList.add(sessionBet.playerName.toString())
                }
                val arrSet = ArrayList(partyIDList)
                for (i in arrSet.indices) {
                    val setValue = arrSet[i]
                    val sessionData = SessionData()
                    for (j in 0 until sessionBetArrayList.size) {
                        val sessionBet = sessionBetArrayList[j]
                        if (setValue == sessionBet.playerName.toString()) {
                            sessionData.sessionID = sessionBet.sessionID
                            sessionData.playerName = sessionBet.playerName
                            sessionData.sessionBet.add(sessionBet)
                        }
                    }
                    sessionDataArrayList.add(sessionData)
                    Log.d(TAG, "onSessionEntryFragment: $sessionDataArrayList")
                }
                sessionAdapter.differ.submitList(sessionDataArrayList)
            }
        })

        binding.rvCurrentSession.apply {
            setHasFixedSize(true)
            adapter = sessionAdapter
        }

        binding.fabAddSession.setOnClickListener {
            val sessionSheet = CurrentSessionBottomFragment()
            val bundle = Bundle()
            bundle.putString(Constants.ID, id)
            bundle.putString(SESSION_ID, sessionID)
            sessionSheet.arguments = bundle
            sessionSheet.setTargetFragment(this, 1)
            sessionSheet.show(parentFragmentManager, sessionSheet.tag)
        }
    }


    fun newInstance(myString: String?, sessionID: String?): SessionEntryFragment {
        val myFragment = SessionEntryFragment()
        val args = Bundle()
        args.putString(Constants.ID, myString)
        args.putString(SESSION_ID, sessionID)
        myFragment.arguments = args
        return myFragment
    }

    override fun onDeleteSessionBet(sessionBet: SessionBet, position: Int) {
        Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show()
        viewModel.deleteSessionBet(sessionBet)
        sessionAdapter.notifyItemRemoved(position)
    }

    override fun onEditSessionBet(sessionBet: SessionBet) {
        val sessionSheet = CurrentSessionBottomFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constants.PID, sessionBet)
        sessionSheet.arguments = bundle
        sessionSheet.setTargetFragment(this, 1)
        sessionSheet.show(parentFragmentManager, sessionSheet.tag)
    }
}