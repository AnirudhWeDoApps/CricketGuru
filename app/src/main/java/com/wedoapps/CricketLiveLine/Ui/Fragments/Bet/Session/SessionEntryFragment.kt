package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.SessionBetAdapter
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.BottomSheets.CurrentSessionBottomFragment
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.Utils.Constants.SESSION_ID
import com.wedoapps.CricketLiveLine.databinding.FragmentSessionEntryBinding

class SessionEntryFragment : Fragment(R.layout.fragment_session_entry), SessionBetAdapter.SetOn {

    private lateinit var binding: FragmentSessionEntryBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var sessionAdapter: SessionBetAdapter
    private lateinit var id: String
    private lateinit var sessionID: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSessionEntryBinding.bind(view)

        id = arguments?.getString(Constants.ID).toString()
        sessionID = arguments?.getString(SESSION_ID).toString()

        viewModel = (activity as SessionEntryActivity).viewModel

        sessionAdapter = SessionBetAdapter(this)

        viewModel.getAllSessions(sessionID).observe(requireActivity(), {
            if (it.isEmpty()) {
                binding.tvNoCurrentSession.visibility = View.VISIBLE
                binding.rvCurrentSession.visibility = View.GONE
            } else {
                val betList = mutableListOf<SessionBet>()
                binding.tvNoCurrentSession.visibility = View.GONE
                binding.rvCurrentSession.visibility = View.VISIBLE
                it.sortedBy { k -> k.playerName }
                it.forEach { data ->
                    val bet = SessionBet(row_type = 2, playerName = data.playerName)
                    if (betList.find { k -> k.playerName == bet.playerName && k.row_type == 2 } == null) {
                        betList.add(bet)
                    }
                    betList.addAll(data.sessionBet!!)
                    if (data.sessionBet.isNullOrEmpty()) {
                        viewModel.deleteSession(data)
                    }
                }
                sessionAdapter.differ.submitList(betList)
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

    @SuppressLint("NewApi")
    override fun onDelete(sessionBet: SessionBet, position: Int) {
        viewModel.deleteItemSession(sessionBet.id!!, sessionBet.playerName.toString())
        sessionAdapter.notifyItemRemoved(position)
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllSessions(sessionID).observe(requireActivity(), {
            it.forEach { data ->
                if (data.sessionBet.isNullOrEmpty()) {
                    viewModel.deleteSession(data)
                }
            }
        })
    }

    override fun onEdit(sessionBet: SessionBet) {
        val sessionSheet = CurrentSessionBottomFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constants.PID, sessionBet)
        sessionSheet.arguments = bundle
        sessionSheet.setTargetFragment(this, 1)
        sessionSheet.show(parentFragmentManager, sessionSheet.tag)
    }
}