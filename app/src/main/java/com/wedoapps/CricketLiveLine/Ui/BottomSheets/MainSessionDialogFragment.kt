package com.wedoapps.CricketLiveLine.Ui.BottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.wedoapps.CricketLiveLine.Model.SessionBet.MainSession
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.BettingActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.PID
import com.wedoapps.CricketLiveLine.databinding.FragmentDialogMainSessionBinding

class MainSessionDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogMainSessionBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var id: String
    private var teamArray = arrayListOf<String>()
    private var team1: String = ""
    private var team2: String = ""
    private var jsonObj: MainSession? = MainSession()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_main_session, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDialogMainSessionBinding.bind(view)

        id = arguments?.getString(ID).toString()
        jsonObj = arguments?.getParcelable(PID)

        viewModel = (activity as BettingActivity).viewModel

        if (jsonObj != null) {

            viewModel.getInfo(jsonObj?.matchID.toString()).observe(requireActivity(), {
                team1 = it.Team1.toString()
                team2 = it.Team2.toString()



                teamArray = arrayListOf(team1, team2, "Draw")
                val teamAdapter = ArrayAdapter(
                    view.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    teamArray
                )
                teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sessionTeamSpinner.adapter = teamAdapter
                val teamPosition = teamAdapter.getPosition(jsonObj?.selectedTeamName)
                binding.sessionTeamSpinner.setSelection(teamPosition)
            })

            binding.apply {
                binding.etNewSessionName.setText(jsonObj!!.sessionName)
            }
        } else {

            viewModel.getInfo(id).observe(requireActivity(), {
                team1 = it.Team1.toString()
                team2 = it.Team2.toString()

                teamArray = arrayListOf(team1, team2, "Draw")
                val teamAdapter = ArrayAdapter(
                    view.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    teamArray
                )
                teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sessionTeamSpinner.adapter = teamAdapter
            })
        }
        binding.btnAddNew.setOnClickListener {
            if (validated()) {
                if (jsonObj != null) {
                    viewModel.updateMainSession(
                        jsonObj?.id!!,
                        jsonObj?.matchID!!,
                        binding.etNewSessionName.text.toString(),
                        binding.sessionTeamSpinner.selectedItem.toString()
                    )
                    dismiss()
                } else {
                    viewModel.insertMainSession(
                        id,
                        binding.etNewSessionName.text.toString(),
                        binding.sessionTeamSpinner.selectedItem.toString()
                    )
                    dismiss()
                }
            }
        }

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

    }

    private fun validated(): Boolean {
        if (binding.etNewSessionName.text.isNullOrEmpty()) {
            binding.etNewSessionName.error = "Field Cannot be Empty"
            return false
        }
        return true
    }


    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog?.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

}