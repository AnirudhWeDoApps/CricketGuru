package com.wedoapps.CricketLiveLine.Ui.BottomSheets

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.Session.SessionEntryActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.PID
import com.wedoapps.CricketLiveLine.Utils.Constants.SESSION_ID
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentBottomCurrentSessionBinding

class CurrentSessionBottomFragment : DialogFragment() {

    private lateinit var binding: FragmentBottomCurrentSessionBinding
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var id: String
    private lateinit var sessionID: String
    private var isComm = false
    private var jsonObj: SessionBet? = SessionBet()
    private val nameList = hashSetOf<String>()

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

        id = arguments?.getString(ID).toString()
        sessionID = arguments?.getString(SESSION_ID).toString()

        jsonObj = arguments?.getParcelable(PID)
        viewModel = (activity as SessionEntryActivity).viewModel

        if (jsonObj != null) {
            val comArray = arrayListOf("Yes", "No")
            val comAdapter = ArrayAdapter(
                view.context,
                android.R.layout.simple_spinner_dropdown_item,
                comArray
            )
            comAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.ynSpinner.adapter = comAdapter
            val teamPosition = comAdapter.getPosition(yornRetrive(jsonObj?.YorN!!))
            binding.ynSpinner.setSelection(teamPosition)
            binding.apply {
                etSessionAmt.setText(jsonObj?.amount.toString())
                etSessionInn.setText(jsonObj?.rate.toString())
                etSessionOver.setText(jsonObj?.over.toString())
                etSessionFp.setText(jsonObj?.FandP.toString())
                etSessionAs.setText(jsonObj?.actualScore.toString())
                etSessionPlayerName.setText(jsonObj?.playerName.toString())
                etCommision.setText(jsonObj?.commission.toString())
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


        binding.apply {
            tvGive.setOnClickListener {
                isComm = false
                giveSelected()
            }

            tvTake.setOnClickListener {
                isComm = true
                takeSelected()
            }
        }

        viewModel.sessionNameList().observe(requireActivity(), {
            Log.d(TAG, "SessionNameList: $it")
            it.forEach { name ->
                nameList.add(name)
            }
        })

        binding.etSessionPlayerName.setOnDismissListener {
            val im =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(requireActivity().currentFocus?.applicationWindowToken, 0)
        }

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        binding.btnAdd.setOnClickListener {
            onSave()
        }

        binding.etSessionPlayerName.addTextChangedListener(etSessionPlayerNameTextWatcher)

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

    private fun giveSelected() {
        binding.apply {
            tvTake.background = null
            tvTake.setTextColor(Color.BLACK)
            tvGive.setTextColor(Color.WHITE)
            tvGive.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.select_bg)
        }
    }

    private fun takeSelected() {
        binding.apply {
            tvGive.background = null
            tvGive.setTextColor(Color.BLACK)
            tvTake.setTextColor(Color.WHITE)
            tvTake.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.reverse_select_bg)
        }
    }

    private fun commissionValue(): Double {
        return if (binding.etCommision.text.toString().isEmpty()) {
            0.0
        } else {
            binding.etCommision.text.toString().toDouble()
        }
    }

    private fun yornValue(): Int {
        return if (binding.ynSpinner.selectedItem.toString() == "Yes") {
            1
        } else {
            0
        }
    }

    private fun yornRetrive(yornValue: Int): String {
        return if (yornValue == 1) {
            "Yes"
        } else {
            "No"
        }
    }

    private val etSessionPlayerNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val names = ArrayList(nameList)
            val arrayAdapter = ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                names
            )
            binding.etSessionPlayerName.threshold = 1
            binding.etSessionPlayerName.setAdapter(arrayAdapter)
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun onSave() {
        if (validated()) {
            val sessionBet = SessionBet(
                null,
                sessionID,
                binding.etSessionAmt.text.toString().toInt(),
                binding.etSessionInn.text.toString().toInt(),
                binding.etSessionOver.text.toString(),
                binding.etSessionFp.text.toString().toInt(),
                yornValue(),
                binding.etSessionAs.text.toString().toInt(),
                binding.etSessionPlayerName.text.toString().trim(),
                commissionValue()
            )
            if (jsonObj != null) {
                viewModel.updateSessionBet(
                    jsonObj?.id!!,
                    jsonObj?.sessionID!!,
                    binding.etSessionAmt.text.toString().toInt(),
                    binding.etSessionInn.text.toString().toInt(),
                    binding.etSessionOver.text.toString(),
                    binding.etSessionFp.text.toString().toInt(),
                    yornValue(),
                    binding.etSessionAs.text.toString().toInt(),
                    binding.etSessionPlayerName.text.toString().trim(),
                    commissionValue()
                )
                dismiss()
            } else {
                viewModel.completeSessionBet(sessionBet)
                dismiss()
            }
        }
    }
}