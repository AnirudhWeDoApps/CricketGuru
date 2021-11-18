package com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.Adapter.MainSessionAdapter
import com.wedoapps.CricketLiveLine.Model.SessionBet.MainSession
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.BottomSheets.MainSessionDialogFragment
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.BettingActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.PID
import com.wedoapps.CricketLiveLine.Utils.Constants.SESSION_ID
import com.wedoapps.CricketLiveLine.Utils.Constants.SESSION_NAME
import com.wedoapps.CricketLiveLine.databinding.FragmentMainSessionBinding

class MainSessionFragment : Fragment(R.layout.fragment_main_session), MainSessionAdapter.OnClicks {

    private lateinit var binding: FragmentMainSessionBinding
    private lateinit var id: String
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var mainAdapter: MainSessionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainSessionBinding.bind(view)

        id = arguments?.getString(ID).toString()
        viewModel = (activity as BettingActivity).viewModel
        mainAdapter = MainSessionAdapter(this)

        viewModel.getAllMainSession(id).observe(requireActivity(), {
            if (it.isNullOrEmpty()) {
                binding.rvMainSession.visibility = View.GONE
                binding.tvNomain.visibility = View.VISIBLE
            } else {
                binding.tvNomain.visibility = View.GONE
                binding.rvMainSession.visibility = View.VISIBLE
                mainAdapter.differ.submitList(it)
            }
        })

        binding.rvMainSession.apply {
            setHasFixedSize(true)
            adapter = mainAdapter
        }

        binding.fabAddSession.setOnClickListener {
            val mainSession = MainSessionDialogFragment()
            val bundle = Bundle()
            bundle.putString(ID, id)
            mainSession.arguments = bundle
            mainSession.setTargetFragment(this, 1)
            mainSession.show(parentFragmentManager, mainSession.tag)
        }
    }


    fun newInstance(myString: String?): MainSessionFragment {
        val myFragment = MainSessionFragment()
        val args = Bundle()
        args.putString(ID, myString)
        myFragment.arguments = args
        return myFragment
    }

    override fun onEdit(mainSession: MainSession) {
        val mainFragment = MainSessionDialogFragment()
        val args = Bundle()
        args.putParcelable(PID, mainSession)
        mainFragment.arguments = args
        mainFragment.setTargetFragment(this, 1)
        mainFragment.show(parentFragmentManager, mainFragment.tag)
    }

    override fun onDelete(mainSession: MainSession) {
        viewModel.deleteMainSession(mainSession)
    }

    override fun onClick(mainSession: MainSession) {
        val intent = Intent(requireContext(), SessionEntryActivity::class.java)
        intent.putExtra(ID, id)
        intent.putExtra(SESSION_ID, mainSession.id)
        intent.putExtra(SESSION_NAME, mainSession.sessionName)
        startActivity(intent)

    }

}