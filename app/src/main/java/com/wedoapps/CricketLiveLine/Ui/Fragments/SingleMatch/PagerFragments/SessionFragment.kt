package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.SessionAdapter
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.ViewPagerActivity
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.databinding.FragmentSessionBinding

class SessionFragment() : Fragment(R.layout.fragment_session) {

    private lateinit var binding: FragmentSessionBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSessionBinding.bind(view)

        id = arguments?.getString(ID).toString()

        viewModel = (activity as ViewPagerActivity).viewModel

        val sessionAdapter1 = SessionAdapter()
        val sessionAdapter2 = SessionAdapter()

        viewModel.getSpecificIdDetail(id).observe(requireActivity(), {

            if (it.SessionHistoryInfo1 == null && it.SessionHistoryInfo2 == null) {
                binding.sessionNested.visibility = View.GONE
                binding.tvNoHistory.visibility = View.VISIBLE
            } else if (it.SessionHistoryInfo1 == null) {
                binding.apply {
                    tvNoHistory.visibility = View.GONE
                    sessionNested.visibility = View.VISIBLE
                    rvSession1.visibility = View.GONE
                    tvSession1.visibility = View.GONE
                    tvSession2.text = it.Team2
                    sessionAdapter2.differ.submitList(it.SessionHistoryInfo2)
                }
            } else if (it.SessionHistoryInfo2 == null) {
                binding.apply {
                    tvNoHistory.visibility = View.GONE
                    sessionNested.visibility = View.VISIBLE
                    rvSession2.visibility = View.GONE
                    tvSession1.text = it.Team1
                    tvSession2.visibility = View.GONE
                    sessionAdapter1.differ.submitList(it.SessionHistoryInfo1)
                }
            } else {
                binding.apply {
                    tvNoHistory.visibility = View.GONE
                    sessionNested.visibility = View.VISIBLE
                    tvSession1.text = it.Team1
                    tvSession2.text = it.Team2
                    sessionAdapter1.differ.submitList(it.SessionHistoryInfo1)
                    sessionAdapter2.differ.submitList(it.SessionHistoryInfo2)
                }
            }
        })

        binding.rvSession1.apply {
            setHasFixedSize(true)
            adapter = sessionAdapter1
        }

        binding.rvSession2.apply {
            setHasFixedSize(true)
            adapter = sessionAdapter2
        }

    }


    fun newInstance(myString: String?): SessionFragment {
        val myFragment = SessionFragment()
        val args = Bundle()
        args.putString(Constants.ID, myString)
        myFragment.arguments = args
        return myFragment
    }

}