package com.wedoapps.CricketLiveLine.Ui.BottomSheets

import android.content.Context
import android.content.DialogInterface
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.BettingActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.PID
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.databinding.FragmentBottomMatchBinding


class MatchBottomSheetFragment : DialogFragment() {

    private lateinit var binding: FragmentBottomMatchBinding
    private var isSelected = false
    private var isComm = false
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var onBottom: OnMatchBetListener
    private lateinit var id: String
    private var jsonObj: MatchBet? = MatchBet()
    private var team1: String = ""
    private var team2: String = ""
    private var teamArray = arrayListOf<String>()
    private val matchList = arrayListOf<MatchBet>()
    private var old1 = 0
    private var old2 = 0
    private var old3 = 0
    private var totalScore1 = 0
    private var totalScore2 = 0
    private var totalScore3 = 0
    private var currentScore1 = 0
    private var currentScore2 = 0
    private var currentScore3 = 0
    private var isInitialText = false
    private val nameList = hashSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomMatchBinding.bind(view)

        id = arguments?.getString(ID).toString()

        jsonObj = arguments?.getParcelable(PID)
        viewModel = (activity as BettingActivity).viewModel

        viewModel.getAllMatchBet(id).observe(requireActivity(), {
            binding.apply {
                if (!it.isNullOrEmpty()) {
                    it.forEach { data1 ->
                        matchList.addAll(listOf(data1))
                        old1 += data1.team1Value!!
                        old2 += data1.drawValue!!
                        old3 += data1.team2Value!!
                    }
                    currentScore1 = 0
                    currentScore2 = 0
                    currentScore3 = 0
                } else {
                    totalScore1 = 0
                    totalScore1 = 0
                    totalScore1 = 0
                    currentScore1 = 0
                    currentScore1 = 0
                    currentScore1 = 0
                }
            }
        })

        if (jsonObj != null) {

            viewModel.getInfo(jsonObj?.matchID.toString()).observe(requireActivity(), {
                team1 = it.Team1.toString()
                team2 = it.Team2.toString()

                binding.tvT1.text = team1
                binding.tvT3.text = team2


                teamArray = arrayListOf(team1, team2, "Draw")
                val teamAdapter = ArrayAdapter(
                    view.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    teamArray
                )
                teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.teamSpinner.adapter = teamAdapter
                val teamPosition = teamAdapter.getPosition(jsonObj?.team)
                    binding.teamSpinner.setSelection(teamPosition)
            })

            binding.apply {
                /*
                tvCr1.text = jsonObj?.team1Value.toString()
                tvCr2.text = jsonObj?.drawValue.toString()
                tvCr3.text = jsonObj?.team2Value.toString()*/
                etRate.setText(jsonObj?.rate.toString())
                etAmount.setText(jsonObj?.amount.toString())
                /* if (!etRate.hasFocus() && !etAmount.hasFocus()) {
                     etRate.removeTextChangedListener(textWatcher2)
                     etAmount.removeTextChangedListener(textWatcher)
                 }*/
                if (jsonObj?.type.equals("Khai")) {
                    isSelected = false
                    khaiSelected()
                } else {
                    isSelected = true
                    lagaiSelected()
                }
                etPlayerName.setText(jsonObj?.playerName.toString())
                if (!matchList.isNullOrEmpty()) {
                    totalScore1 += old1
                    totalScore2 += old2
                    totalScore3 += old3
                    tvScore1.text = totalScore1.toString()
                    tvScore2.text = totalScore2.toString()
                    tvScore3.text = totalScore3.toString()
                }
            }
        } else {
            viewModel.getInfo(id).observe(requireActivity(), {
                team1 = it.Team1.toString()
                team2 = it.Team2.toString()
                binding.tvT1.text = team1
                binding.tvT3.text = team2

                teamArray = arrayListOf(team1, team2, "Draw")
                val teamAdapter = ArrayAdapter(
                    view.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    teamArray
                )
                teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.teamSpinner.adapter = teamAdapter
            })
        }

        viewModel.matchBetNameList().observe(requireActivity(), {
            Log.d(TAG, "MatchBet NameList: $it")
            it.forEach { name ->
                nameList.add(name)
            }
        })

        binding.etPlayerName.setOnDismissListener {
            val im =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(
                requireActivity().currentFocus?.applicationWindowToken,
                0
            )
        }


        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        binding.apply {
            tvLagai.setOnClickListener {
                isSelected = true
                lagaiSelected()
                setData(
                    binding.etAmount.text.toString(),
                    binding.etRate.text.toString(),
                    binding.teamSpinner.selectedItem.toString()
                )

                tvKhai.setOnClickListener {
                    isSelected = false
                    khaiSelected()
                    setData(
                        binding.etAmount.text.toString(),
                        binding.etRate.text.toString(),
                        binding.teamSpinner.selectedItem.toString()
                    )
                }

                tvGive.setOnClickListener {
                    isComm = false
                    giveSelected()
                }

                tvTake.setOnClickListener {
                    isComm = true
                    takeSelected()
                }
            }
        }
        binding.btnAdd.setOnClickListener {
            onSave()
        }

        binding.etRate.addTextChangedListener(textWatcher2)
        binding.etAmount.addTextChangedListener(textWatcher)
        binding.etPlayerName.addTextChangedListener(etPlayerNameTextWatcher)

        binding.teamSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    val team = p0?.getItemAtPosition(p2).toString()
                    setData(
                        binding.etAmount.text.toString(),
                        binding.etRate.text.toString(),
                        team
                    )
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

    }

    private val etPlayerNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val names = ArrayList(nameList)
            val arrayAdapter = ArrayAdapter(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                names
            )
            binding.etPlayerName.threshold = 1
            binding.etPlayerName.setAdapter(arrayAdapter)
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (isInitialText) {
                isInitialText = false
            } else {
                setData(
                    p0,
                    binding.etRate.text.toString(),
                    binding.teamSpinner.selectedItem.toString()
                )
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private
    val textWatcher2 = object : TextWatcher {
        override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
        }

        override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
        ) {
            if (isInitialText) {
                isInitialText = false
            } else {
                setData(
                    binding.etAmount.text.toString(),
                    p0,
                    binding.teamSpinner.selectedItem.toString()
                )
            }

        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun validated(): Boolean {
        if (binding.etRate.text.isNullOrEmpty()) {
            binding.etRate.error = "Field Cannot be Empty"
            return false
        }
        if (binding.etAmount.text.isNullOrEmpty()) {
            binding.etAmount.error = "Field Cannot be Empty"
            return false
        }
        if (binding.etPlayerName.text.isNullOrEmpty()) {
            binding.etPlayerName.error = "Field Cannot be Empty"
            return false
        }
        return true
    }

    interface OnMatchBetListener {
        fun onSheetClose()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onBottom.onSheetClose()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onBottom = targetFragment as OnMatchBetListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnMatchBetListener")
        }
    }

    private fun getValue1(amount: Int, rate: Int): Int {
        return amount * rate / 100
    }

    private fun khaiSelected() {
        binding.apply {
            tvLagai.background = null
            tvLagai.setTextColor(Color.BLACK)
            tvKhai.setTextColor(Color.WHITE)
            tvKhai.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.select_bg)
        }
    }

    private fun lagaiSelected() {
        binding.apply {
            tvKhai.background = null
            tvKhai.setTextColor(Color.BLACK)
            tvLagai.setTextColor(Color.WHITE)
            tvLagai.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.reverse_select_bg)
        }
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

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog?.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun setData(p0: CharSequence?, rate: CharSequence?, team: String?) {

        binding.apply {
            if (p0.isNullOrEmpty() || rate.isNullOrEmpty()) {
                totalScore1 = 0
                totalScore2 = 0
                totalScore3 = 0
                currentScore1 = 0
                currentScore2 = 0
                currentScore3 = 0
            } else {
                if (team == team1) {
                    if (isSelected) {
                        totalScore2 = -p0.toString().toInt()
                        totalScore3 = -p0.toString().toInt()
                        totalScore1 = getValue1(
                            p0.toString().toInt(),
                            rate.toString().toInt()
                        )
                        currentScore2 = -p0.toString().toInt()
                        currentScore3 = -p0.toString().toInt()
                        currentScore1 = getValue1(
                            p0.toString().toInt(),
                            rate.toString().toInt()
                        )
                    } else {
                        totalScore2 = p0.toString().toInt()
                        totalScore3 = p0.toString().toInt()
                        totalScore1 = getValue1(
                            p0.toString().toInt(),
                            -rate.toString().toInt()
                        )
                        currentScore2 = p0.toString().toInt()
                        currentScore3 = p0.toString().toInt()
                        currentScore1 = getValue1(
                            p0.toString().toInt(),
                            -rate.toString().toInt()
                        )
                    }
                } else if (team == team2) {
                    if (isSelected) {
                        totalScore1 = -p0.toString().toInt()
                        totalScore2 = -p0.toString().toInt()
                        totalScore3 = getValue1(
                            p0.toString().toInt(),
                            rate.toString().toInt()
                        )
                        currentScore1 = -p0.toString().toInt()
                        currentScore2 = -p0.toString().toInt()
                        currentScore3 = getValue1(
                            p0.toString().toInt(),
                            rate.toString().toInt()
                        )
                    } else {
                        totalScore1 = p0.toString().toInt()
                        totalScore2 = p0.toString().toInt()
                        totalScore3 = getValue1(
                            p0.toString().toInt(),
                            -rate.toString().toInt()
                        )
                        currentScore1 = p0.toString().toInt()
                        currentScore2 = p0.toString().toInt()
                        currentScore3 = getValue1(
                            p0.toString().toInt(),
                            -rate.toString().toInt()
                        )
                    }
                } else {
                    if (isSelected) {
                        totalScore1 = -p0.toString().toInt()
                        totalScore3 = -p0.toString().toInt()
                        totalScore2 = getValue1(
                            p0.toString().toInt(),
                            rate.toString().toInt()
                        )
                        currentScore1 = -p0.toString().toInt()
                        currentScore3 = -p0.toString().toInt()
                        currentScore2 = getValue1(
                            p0.toString().toInt(),
                            rate.toString().toInt()
                        )
                    } else {
                        totalScore1 = p0.toString().toInt()
                        totalScore3 = p0.toString().toInt()
                        totalScore2 = getValue1(
                            p0.toString().toInt(),
                            -rate.toString().toInt()
                        )
                        currentScore1 = p0.toString().toInt()
                        currentScore3 = p0.toString().toInt()
                        currentScore2 = getValue1(
                            p0.toString().toInt(),
                            -rate.toString().toInt()
                        )
                    }
                }
            }

            if (!matchList.isNullOrEmpty() || jsonObj != null) {
                totalScore1 += old1
                totalScore2 += old2
                totalScore3 += old3
            }

            tvScore1.text = totalScore1.toString()
            tvScore2.text = totalScore2.toString()
            tvScore3.text = totalScore3.toString()
            tvCr1.text = currentScore1.toString()
            tvCr2.text = currentScore2.toString()
            tvCr3.text = currentScore3.toString()
        }
    }

    private fun onSave() {
        if (validated()) {
            when (binding.teamSpinner.selectedItem) {
                team1 -> {
                    if (isSelected) {
                        // Lagai Portion
                        val bet = MatchBet(
                            null,
                            id,
                            binding.etRate.text.toString().toInt(),
                            binding.etAmount.text.toString().toInt(),
                            binding.tvLagai.text.toString(),
                            binding.teamSpinner.selectedItem.toString(),
                            false,
                            binding.etPlayerName.text.toString().trim(),
                            getValue1(
                                binding.etAmount.text.toString()
                                    .toInt(),
                                binding.etRate.text.toString().toInt()
                            ),
                            -binding.etAmount.text.toString().toInt(),
                            -binding.etAmount.text.toString().toInt()
                        )
                        if (jsonObj != null) {
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString().trim(),
                                getValue1(
                                    binding.etAmount.text.toString()
                                        .toInt(),
                                    binding.etRate.text.toString().toInt()
                                ),
                                -binding.etAmount.text.toString().toInt(),
                                -binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        } else {
                            viewModel.saveMatchBet(bet)
                            dismiss()
                        }
                    } else {
                        // Khai Portion
                        val bet = MatchBet(
                            null,
                            id,
                            binding.etRate.text.toString().toInt(),
                            binding.etAmount.text.toString().toInt(),
                            binding.tvKhai.text.toString(),
                            binding.teamSpinner.selectedItem.toString(),
                            false,
                            binding.etPlayerName.text.toString().trim(),
                            getValue1(
                                binding.etAmount.text.toString()
                                    .toInt(),
                                -binding.etRate.text.toString().toInt()
                            ),
                            binding.etAmount.text.toString().toInt(),
                            binding.etAmount.text.toString().toInt()
                        )
                        if (jsonObj != null) {
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString().trim(),
                                getValue1(
                                    binding.etAmount.text.toString()
                                        .toInt(),
                                    -binding.etRate.text.toString().toInt()
                                ),
                                binding.etAmount.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        } else {
                            viewModel.saveMatchBet(bet)
                            dismiss()
                        }
                    }
                }
                team2 -> {
                    if (isSelected) {
                        // Lagai Portion
                        val bet = MatchBet(
                            null,
                            id,
                            binding.etRate.text.toString().toInt(),
                            binding.etAmount.text.toString().toInt(),
                            binding.tvLagai.text.toString(),
                            binding.teamSpinner.selectedItem.toString(),
                            false,
                            binding.etPlayerName.text.toString().trim(),
                            -binding.etAmount.text.toString().toInt(),
                            getValue1(
                                binding.etAmount.text.toString().toInt(),
                                binding.etRate.text.toString().toInt()
                            ),
                            -binding.etAmount.text.toString().toInt()
                        )
                        if (jsonObj != null) {
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString().trim(),
                                -binding.etAmount.text.toString().toInt(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    binding.etRate.text.toString().toInt()
                                ),
                                -binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        } else {
                            viewModel.saveMatchBet(bet)
                            dismiss()
                        }
                    } else {
                        // Khai Portion
                        val bet = MatchBet(
                            null,
                            id,
                            binding.etRate.text.toString().toInt(),
                            binding.etAmount.text.toString().toInt(),
                            binding.tvKhai.text.toString(),
                            binding.teamSpinner.selectedItem.toString(),
                            false,
                            binding.etPlayerName.text.toString().trim(),
                            binding.etAmount.text.toString().toInt(),
                            getValue1(
                                binding.etAmount.text.toString().toInt(),
                                -binding.etRate.text.toString().toInt()
                            ),
                            binding.etAmount.text.toString().toInt()
                        )
                        if (jsonObj != null) {
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString().trim(),
                                binding.etAmount.text.toString().toInt(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    -binding.etRate.text.toString().toInt()
                                ),
                                binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        } else {
                            viewModel.saveMatchBet(bet)
                            dismiss()
                        }
                    }
                }
                else -> {
                    if (isSelected) {
                        // Lagai Portion
                        val bet = MatchBet(
                            null,
                            id,
                            binding.etRate.text.toString().toInt(),
                            binding.etAmount.text.toString().toInt(),
                            binding.tvLagai.text.toString(),
                            binding.teamSpinner.selectedItem.toString(),
                            false,
                            binding.etPlayerName.text.toString().trim(),
                            -binding.etAmount.text.toString().toInt(),
                            -binding.etAmount.text.toString().toInt(),
                            getValue1(
                                binding.etAmount.text.toString().toInt(),
                                binding.etRate.text.toString().toInt()
                            )
                        )
                        if (jsonObj != null) {
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString().trim(),
                                -binding.etAmount.text.toString().toInt(),
                                -binding.etAmount.text.toString().toInt(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    binding.etRate.text.toString().toInt()
                                )
                            )
                            dismiss()
                        } else {
                            viewModel.saveMatchBet(bet)
                            dismiss()
                        }
                    } else {
                        // Khai Portion
                        val bet = MatchBet(
                            null,
                            id,
                            binding.etRate.text.toString().toInt(),
                            binding.etAmount.text.toString().toInt(),
                            binding.tvKhai.text.toString(),
                            binding.teamSpinner.selectedItem.toString(),
                            false,
                            binding.etPlayerName.text.toString().trim(),
                            binding.etAmount.text.toString().toInt(),
                            binding.etAmount.text.toString().toInt(),
                            getValue1(
                                binding.etAmount.text.toString().toInt(),
                                -binding.etRate.text.toString().toInt()
                            )
                        )
                        if (jsonObj != null) {
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString().trim(),
                                binding.etAmount.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    -binding.etRate.text.toString().toInt()
                                )
                            )
                            dismiss()
                        } else {
                            viewModel.saveMatchBet(bet)
                            dismiss()
                        }
                    }
                }
            }
        }
    }
}