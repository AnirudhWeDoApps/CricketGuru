package com.wedoapps.CricketLiveLine.Ui.BottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.BettingActivity
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.databinding.FragmentBottomCurrentSessionBinding

class CurrentSessionBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomCurrentSessionBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_current_session, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomCurrentSessionBinding.bind(view)

        id = arguments?.getString(Constants.ID).toString()

        viewModel = (activity as BettingActivity).viewModel

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        val comArray = arrayListOf("Yes", "No")
        val comAdapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_dropdown_item,
            comArray
        )
        comAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ynSpinner.adapter = comAdapter

        binding.btnAdd.setOnClickListener {
            if (validated()) {
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

}