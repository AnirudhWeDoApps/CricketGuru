package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.SessionBetAdapter
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.BottomSheets.CurrentSessionBottomFragment
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.BettingActivity
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.databinding.FragmentCurrentSessionBinding

class CurrentSessionFragment : Fragment(R.layout.fragment_current_session),
    SessionBetAdapter.OnSet {

    private lateinit var binding: FragmentCurrentSessionBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var sessionAdapter: SessionBetAdapter
    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentSessionBinding.bind(view)

        id = arguments?.getString(ID).toString()

        viewModel = (activity as BettingActivity).viewModel

        sessionAdapter = SessionBetAdapter(this)

        viewModel.getAllSessions().observe(requireActivity(), {
            if (it.isEmpty()) {
                binding.tvNoCurrentSession.visibility = View.VISIBLE
                binding.rvCurrentSession.visibility = View.GONE
            } else {
                sessionAdapter.differ.submitList(it)
                binding.tvNoCurrentSession.visibility = View.GONE
                binding.rvCurrentSession.visibility = View.VISIBLE

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
            sessionSheet.arguments = bundle
            sessionSheet.setTargetFragment(this, 1)
            sessionSheet.show(parentFragmentManager, sessionSheet.tag)
        }

    }

    override fun onDeleteSession(sessionBet: SessionBet) {
        viewModel.deleteSession(sessionBet)
    }

    fun newInstance(myString: String?): CurrentSessionFragment {
        val myFragment = CurrentSessionFragment()
        val args = Bundle()
        args.putString(Constants.ID, myString)
        myFragment.arguments = args
        return myFragment
    }

}