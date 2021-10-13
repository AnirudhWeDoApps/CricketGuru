package com.wedoapps.CricketLiveLine.Ui.BottomSheets

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.BettingActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.PID
import com.wedoapps.CricketLiveLine.databinding.FragmentBottomCurrentSessionBinding

class CurrentSessionBottomFragment : DialogFragment() {

    private lateinit var binding: FragmentBottomCurrentSessionBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var id: String
    private var jsonObj: SessionBet? = SessionBet()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_current_session, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.setOnShowListener {
            Handler().post {
                val bottomSheet =
                    (dialog as? BottomSheetDialog)?.findViewById<View>(R.id.design_bottom_sheet) as? FrameLayout
                bottomSheet?.let {
                    BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomCurrentSessionBinding.bind(view)

        id = arguments?.getString(ID).toString()
        jsonObj = arguments?.getParcelable(PID)
        viewModel = (activity as BettingActivity).viewModel

        if (jsonObj != null) {
            val comArray = arrayListOf("Yes", "No")
            val comAdapter = ArrayAdapter(
                view.context,
                android.R.layout.simple_spinner_dropdown_item,
                comArray
            )
            comAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.ynSpinner.adapter = comAdapter
            val teamPosition = comAdapter.getPosition(jsonObj?.YorN)
            binding.ynSpinner.setSelection(teamPosition)
            binding.apply {
                etSessionAmt.setText(jsonObj?.amount.toString())
                etSessionInn.setText(jsonObj?.innings.toString())
                etSessionOver.setText(jsonObj?.over.toString())
                etSessionFp.setText(jsonObj?.FandP.toString())
                etSessionAs.setText(jsonObj?.actualScore.toString())
                etSessionPlayerName.setText(jsonObj?.playerName.toString())
            }
        } else {
            val comArray = arrayListOf("Yes", "No")
            val comAdapter = ArrayAdapter(
                view.context,
                android.R.layout.simple_spinner_dropdown_item,
                comArray
            )
            comAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.ynSpinner.adapter = comAdapter
        }


        binding.ivCancel.setOnClickListener {
            dismiss()
        }
        binding.btnAdd.setOnClickListener {
            if (validated()) {
                if (jsonObj != null) {
                    viewModel.updateSession(
                        jsonObj?.id!!,
                        jsonObj?.matchId!!,
                        binding.etSessionAmt.text.toString().toInt(),
                        binding.etSessionInn.text.toString().toInt(),
                        binding.etSessionOver.text.toString(),
                        binding.etSessionFp.text.toString().toInt(),
                        binding.ynSpinner.selectedItem.toString(),
                        binding.etSessionAs.text.toString().toInt(),
                        binding.etSessionPlayerName.text.toString()
                    )
                    dismiss()
                } else {
                    viewModel.saveSession(
                        id,
                        binding.etSessionAmt.text.toString().toInt(),
                        binding.etSessionInn.text.toString().toInt(),
                        binding.etSessionOver.text.toString(),
                        binding.etSessionFp.text.toString().toInt(),
                        binding.ynSpinner.selectedItem.toString(),
                        binding.etSessionAs.text.toString().toInt(),
                        binding.etSessionPlayerName.text.toString()
                    )
                    dismiss()
                }
            }
        }

    }

    private fun validated(): Boolean {
        if (binding.etSessionAmt.text.isNullOrEmpty()) {
            binding.etSessionAmt.error = "Field Cannot be Empty"
            return false
        }
        if (binding.etSessionInn.text.isNullOrEmpty()) {
            binding.etSessionInn.error = "Field Cannot be Empty"
            return false
        }
        if (binding.etSessionOver.text.isNullOrEmpty()) {
            binding.etSessionOver.error = "Field Cannot be Empty"
            return false
        }
        if (binding.etSessionFp.text.isNullOrEmpty()) {
            binding.etSessionFp.error = "Field Cannot be Empty"
            return false
        }
        if (binding.etSessionAs.text.isNullOrEmpty()) {
            binding.etSessionAs.error = "Field Cannot be Empty"
            return false
        }
        if (binding.etSessionPlayerName.text.isNullOrEmpty()) {
            binding.etSessionPlayerName.error = "Field Cannot be Empty"
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