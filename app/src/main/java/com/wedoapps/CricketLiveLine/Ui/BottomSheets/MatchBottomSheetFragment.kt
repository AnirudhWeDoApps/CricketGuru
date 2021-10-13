package com.wedoapps.CricketLiveLine.Ui.BottomSheets

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.Bet.BettingActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.PID
import com.wedoapps.CricketLiveLine.databinding.FragmentBottomMatchBinding


class MatchBottomSheetFragment : DialogFragment() {

    private lateinit var binding: FragmentBottomMatchBinding
    private var isSelected = false
    private lateinit var viewModel: CricketGuruViewModel
    private lateinit var onBottom: OnMatchBetListener
    private lateinit var id: String
    private var jsonObj: MatchBet? = MatchBet()
    private var team1: String = ""
    private var team2: String = ""
    private var teamArray = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_match, container, false)
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
        binding = FragmentBottomMatchBinding.bind(view)


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
                binding.teamSpinner.adapter = teamAdapter
                val teamPosition = teamAdapter.getPosition(jsonObj?.team)
                binding.teamSpinner.setSelection(teamPosition)
            })

            binding.apply {

                etRate.setText(jsonObj?.rate.toString())
                etAmount.setText(jsonObj?.amount.toString())
                if (jsonObj?.type.equals("Khai")) {
                    isSelected = false
                    khaiSelected()
                } else {
                    isSelected = true
                    lagaiSelected()
                }
                etPlayerName.setText(jsonObj?.playerName.toString())
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
                binding.teamSpinner.adapter = teamAdapter
            })
        }

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        binding.apply {
            tvLagai.setOnClickListener {
                isSelected = true
                lagaiSelected()
            }

            binding.apply {
                tvKhai.setOnClickListener {
                    isSelected = false
                    khaiSelected()
                }
            }
        }
        binding.btnAdd.setOnClickListener {

            if (validated()) {
                if (jsonObj != null) {
                    if (binding.teamSpinner.selectedItem == team1) {
                        if (isSelected) {
                            Toast.makeText(requireContext(), "Lagai", Toast.LENGTH_SHORT).show()
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    binding.etRate.text.toString().toInt()
                                ),
                                -binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Khai", Toast.LENGTH_SHORT).show()
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    -binding.etRate.text.toString().toInt()
                                ),
                                binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        }
                    } else if (binding.teamSpinner.selectedItem == team2) {
                        if (isSelected) {
                            Toast.makeText(requireContext(), "Lagai", Toast.LENGTH_SHORT).show()
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                -binding.etAmount.text.toString().toInt(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    binding.etRate.text.toString().toInt()
                                )
                            )
                            dismiss()
                        } else {
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                binding.etAmount.text.toString().toInt(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    -binding.etRate.text.toString().toInt()
                                )
                            )
                            dismiss()
                        }
                    } else {
                        if (isSelected) {
                            Toast.makeText(requireContext(), "Lagai", Toast.LENGTH_SHORT).show()
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                binding.etAmount.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        } else {
                            viewModel.updateMatchBet(
                                jsonObj?.id!!,
                                jsonObj?.matchID!!,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                binding.etAmount.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        }
                    }
                } else {
                    if (binding.teamSpinner.selectedItem == team1) {
                        if (isSelected) {
                            Toast.makeText(requireContext(), "Lagai", Toast.LENGTH_SHORT).show()
                            viewModel.saveMatchBet(
                                id,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    binding.etRate.text.toString().toInt()
                                ),
                                -binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Khai", Toast.LENGTH_SHORT).show()
                            viewModel.saveMatchBet(
                                id,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    -binding.etRate.text.toString().toInt()
                                ),
                                binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        }
                    } else if (binding.teamSpinner.selectedItem == team2) {
                        if (isSelected) {
                            Toast.makeText(requireContext(), "Lagai", Toast.LENGTH_SHORT).show()
                            viewModel.saveMatchBet(
                                id,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                -binding.etAmount.text.toString().toInt(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    binding.etRate.text.toString().toInt()
                                )
                            )
                            dismiss()
                        } else {
                            viewModel.saveMatchBet(
                                id,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                binding.etAmount.text.toString().toInt(),
                                getValue1(
                                    binding.etAmount.text.toString().toInt(),
                                    -binding.etRate.text.toString().toInt()
                                )
                            )
                            dismiss()
                        }
                    } else {
                        if (isSelected) {
                            Toast.makeText(requireContext(), "Lagai", Toast.LENGTH_SHORT).show()
                            viewModel.saveMatchBet(
                                id,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvLagai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                binding.etAmount.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        } else {
                            viewModel.saveMatchBet(
                                id,
                                binding.etRate.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt(),
                                binding.tvKhai.text.toString(),
                                binding.teamSpinner.selectedItem.toString(),
                                false,
                                binding.etPlayerName.text.toString(),
                                binding.etAmount.text.toString().toInt(),
                                binding.etAmount.text.toString().toInt()
                            )
                            dismiss()
                        }
                    }
                }
            }
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

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog?.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

}