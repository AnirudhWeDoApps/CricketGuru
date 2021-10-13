package com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.PagerFragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Ui.CricketGuruViewModel
import com.wedoapps.CricketLiveLine.Ui.Fragments.SingleMatch.ViewPagerActivity
import com.wedoapps.CricketLiveLine.Utils.Constants.ID
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.Utils.PreferenceManager
import com.wedoapps.CricketLiveLine.databinding.FragmentLiveLineBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

class LiveLineFragment : Fragment(R.layout.fragment_live_line) {

    private lateinit var binding: FragmentLiveLineBinding
    private lateinit var viewModel: CricketGuruViewModel
    private var textToSpeech: TextToSpeech? = null
    private var isVolumeOn: Boolean = true
    private var isFirstInning: Boolean = true
    private var isMatchLive: Boolean = true
    private var team1Name: String? = ""
    private var scoreTeam1: String? = ""
    private var overTeam1: String? = ""
    private var team2Name: String? = ""
    private var scoreTeam2: String? = ""
    private var overTeam2: String? = ""
    private var ballByBallSpeech: String? = ""
    private lateinit var id: String
    private lateinit var f: File
    private lateinit var b: Bitmap

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLiveLineBinding.bind(view)

        id = arguments?.getString(ID).toString()

        try {
            val imgPath = Environment.getExternalStorageDirectory()
                .toString() + "/Android/data/" + requireActivity().applicationContext.packageName + "/Files/FullAd.jpeg"
            f = File(imgPath)
            b = BitmapFactory.decodeStream(FileInputStream(f))
            Log.d(TAG, "setImage: Done")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.d(TAG, "setImage: $e")
        }

        viewModel = (activity as ViewPagerActivity).viewModel

        val card1 = binding.card1
        val card2 = binding.card2
        val card3 = binding.card3
        val card4 = binding.card4
        val card6 = binding.card6
        val card7 = binding.card7

        setTextToSpeechListener()
        viewModel.apply {

            getSpecificIdDetail(id).observe(requireActivity(), {
                val status = it.MatchStatus
                binding.tvStatus.text = status

                team1Name = it.Team1
                team2Name = it.Team2
                binding.tvTeamName.text = "${it.Team1} "
                binding.tvTeamNameOpp.text = "${it.Team2} "

                if (status.equals("LIVE")) {
                    isMatchLive = true
                } else if (status.equals("UPCOMING")) {
                    isMatchLive = false
                }

            })

            getAllTeam1(id).observe(requireActivity(), {
                Log.d(TAG, "Team1: $it ")
                scoreTeam1 = it?.Score
                overTeam1 = it?.Over
                binding.tvPlayScore.text = it?.Score + " ( ${it?.Over} )"
            })

            getAllTeam2(id).observe(requireActivity(), {
                Log.d(TAG, "Team2: $it ")
                scoreTeam2 = it?.Score
                overTeam2 = it?.Over
                binding.tvOppScore.text = it?.Score + " ( ${it?.Over} )"
            })

            getRunRate(id).observe(requireActivity(), {
                Log.d(TAG, " RunRate: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card1.apply {
                    tvCrr.text = map["CRR"]
                    tvMessage.text = map["OtherInfo"]
                    tvRrr.text = map["RRR"]
                }
            })

            getBatsman1(id).observe(requireActivity(), {
                Log.d(TAG, "Batsman1: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card3.apply {
                    tvFirstBatsman.text = map["BatsmanName"]
                    tvFirstRun.text = map["Run"]
                    tvFirstBalls.text = map["Ball"]
                    tvFirst4s.text = map["4s"]
                    tvFirst6s.text = map["6s"]
                    tvFirstStrikeRate.text = map["SR"]
                }

            })

            getBatsman2(id).observe(requireActivity(), {
                Log.d(TAG, "Batsman2: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card3.apply {
                    tvSecondBatsman.text = map["BatsmanName"]
                    tvSecondRun.text = map["Run"]
                    tvSecondBalls.text = map["Ball"]
                    tvSecond4s.text = map["4s"]
                    tvSecond6s.text = map["6s"]
                    tvSecondStrikeRate.text = map["SR"]
                }
            })

            getBowlerInfo(id).observe(requireActivity(), {
                Log.d(TAG, " Bowler Info: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                val text =
                    map["BowlerName"] + " " + map["Wicket"]/* + "-" + map["Run"] + " " + "( ${map["Over"]} Over)"*/
                card3.tvBowlerInfo.text = text
            })

            getPartnership(id).observe(requireActivity(), {
                Log.d(TAG, " Partnership: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                card3.tvPartnership.text = map["Partnership"]
            })

            getBallXRun(id).observe(requireActivity(), {
                Log.d(TAG, " BallXRun: $it")


                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }


                card2.apply {
                    tvSecondBall.text = map["Ball"]
                    tvSecondRun.text = map["Run"]

                    if (tvSecondBall.text.isNullOrEmpty()) {
                        card2.root.visibility = View.GONE
                    } else {
                        card2.root.visibility = View.VISIBLE
                    }
                }
            })

            getSessionLambi(id).observe(requireActivity(), {
                Log.d(TAG, " SessionLambi: $it")


                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card6.apply {
                    tvSessionOver.text = map["LambiName"] + " Over"
                    tvFirstNo.text = map["Lambi1"]
                    tvFirstYes.text = map["Lambi2"]
                    tvOpn.text = map["OpenLambi"]
                    tvMin.text = map["MinLambi"]
                    tvMax.text = map["MaxLambi"]
                }
            })

            getLambiBallXRun(id).observe(requireActivity(), {
                Log.d(TAG, " LambiBallXRun: $it")


                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }


                card6.apply {
                    tvSecondBall.text = map["Ball"]
                    tvSecondRun.text = map["Run"]


                    if (tvSecondBall.text.isNullOrEmpty()) {
                        card6.root.visibility = View.GONE
                    } else {
                        card6.root.visibility = View.VISIBLE
                    }

                }
            })

            getLiveMatch(id).observe(requireActivity(), {
                Log.d(TAG, " LiveMatch: $it")
            })

            getSession(id).observe(requireActivity(), {
                Log.d(TAG, " SessionFragment: $it")


                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card2.apply {
                    tvSessionOver.text = map["SessionName"] + " Over"
                    tvFirstNo.text = map["Rate1"]
                    tvFirstYes.text = map["Rate2"]
                    tvOpn.text = map["OpenSession"]
                    tvMin.text = map["MinSession"]
                    tvMax.text = map["MaxSession"]
                }

            })

            getLastBall(id).observe(requireActivity(), {
                Log.d(TAG, " LastBall: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                card4.apply {
                    tvBall1.text = map["Ball1"]
                    tvBall2.text = map["Ball2"]
                    tvBall3.text = map["Ball3"]
                    tvBall4.text = map["Ball4"]
                    tvBall5.text = map["Ball5"]
                    tvBall6.text = map["Ball6"]
                }

            })

            getBallByBall(id).observe(requireActivity(), {
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                binding.tvDay.text = map["BallByBall"]
                ballByBallSpeech = binding.tvDay.text.toString()

                when {
                    ballByBallSpeech!!.contentEquals("BALL") -> {
                        ballByBallSpeech = "Ball Start Ball"
                    }
                    ballByBallSpeech!!.contentEquals("0 Run") -> {
                        binding.tvDay.text = "0"
                        ballByBallSpeech = "dot ball"
                    }
                    ballByBallSpeech!!.contentEquals("1 Run") -> {
                        binding.tvDay.text = "1"
                        ballByBallSpeech = "One Run One"
                    }
                    ballByBallSpeech!!.contentEquals("2 Run") -> {
                        binding.tvDay.text = "2"
                        ballByBallSpeech = "Two Run Two"
                    }
                    ballByBallSpeech!!.contentEquals("3 Run") -> {
                        binding.tvDay.text = "3"
                        ballByBallSpeech = "Three Run"
                    }
                    ballByBallSpeech!!.contentEquals("It's 6") -> {
                        binding.tvDay.text = "6-6-6"
//                        binding.tvDay.text = "6"
                        ballByBallSpeech = " Six Six Six"
                    }
                    ballByBallSpeech!!.contentEquals("It's 4") -> {
                        binding.tvDay.text = "4-4-4"
//                        binding.tvDay.text = "4"
                        ballByBallSpeech = " Four Four Four"
                    }
                    ballByBallSpeech!!.contentEquals("WD + 1") -> {
                        binding.tvDay.text = "WD1"
                        ballByBallSpeech = "Wide Plus one"
                    }
                    ballByBallSpeech!!.contentEquals("WD + 2") -> {
                        binding.tvDay.text = "WD2"
                        ballByBallSpeech = "Wide Plus Two"
                    }
                    ballByBallSpeech!!.contentEquals("WD + 4") -> {
                        ballByBallSpeech = "Wide Plus Four"
                    }
                    ballByBallSpeech!!.contentEquals("No + 1") -> {
                        ballByBallSpeech = "No Ball Plus One"
                    }
                    ballByBallSpeech!!.contentEquals("No + 2") -> {
                        ballByBallSpeech = "No Ball Plus Two"
                    }
                    ballByBallSpeech!!.contentEquals("No + 4") -> {
                        ballByBallSpeech = "No Ball Plus Four"
                    }
                    ballByBallSpeech!!.contentEquals("No + 6") -> {
                        ballByBallSpeech = "No Ball Plus Six"
                    }
                    ballByBallSpeech!!.contentEquals("It's Wicket!!!") -> {
                        ballByBallSpeech = "It's Wicket"
                    }
                    ballByBallSpeech!!.contentEquals("1 + Wicket") -> {
                        ballByBallSpeech = "One Plus Wicket"
                    }

                    ballByBallSpeech!!.trim().contentEquals("Over Complete") -> {
                        getRunAndScoreVoice()
                    }

                    ballByBallSpeech!!.trim().contentEquals("Over") -> {
                        getRunAndScoreVoice()
                    }

                }

                if (isVolumeOn) {
                    val speechStatus = textToSpeech!!.speak(
                        ballByBallSpeech!!,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.d(
                            "TTS",
                            "Error in converting Text to Speech!"
                        )
                    }

                }

                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                    binding.tvDay,
                    20,
                    35,
                    20,
                    1
                )
            })

            getOtherMessage(id).observe(requireActivity(), {
                Log.d(TAG, "Other Message: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                card3.tvLastWicket.text = map["Message"]
            })

            getMatchRate(id).observe(requireActivity(), {
                Log.d(TAG, "match rate: $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }
                card7.apply {
                    tvFavTeam.text = map["FavTeam"]
                    tvRate1.text = map["Rate1"]
                    tvRate2.text = map["Rate2"]
                }
            })

            getFirstInnings(id).observe(requireActivity(), {
                Log.d(TAG, "First Innings $it")
                val value = it.substring(1, it.length - 1)
                val keyValuePair = value.split(",")
                val map = hashMapOf<String, String>()

                keyValuePair.forEach { text ->
                    val entry = text.split("=")
                    map[entry[0].trim()] = entry[1].trim()
                }

                isFirstInning = map["IsFirstInning"].toBoolean()

                if (isFirstInning) {
                    binding.tvTeamName.text = team1Name
                    binding.tvPlayScore.text = "$scoreTeam1 ( $overTeam1 )"

                    binding.tvTeamNameOpp.text = team2Name
                    binding.tvOppScore.text = "$scoreTeam2 ( $overTeam2 )"
                } else {
                    binding.tvTeamName.text = team2Name
                    binding.tvPlayScore.text = "$scoreTeam2 ( $overTeam2 )"

                    binding.tvTeamNameOpp.text = team1Name
                    binding.tvOppScore.text = "$scoreTeam1 ( $overTeam1 )"

                }
            })
        }

        binding.ivVolume.setOnClickListener {
            if (isVolumeOn) {
                binding.ivVolume.visibility = View.GONE
                binding.ivMute.visibility = View.VISIBLE
                isVolumeOn = false
            } else {
                binding.ivVolume.visibility = View.VISIBLE
                binding.ivMute.visibility = View.GONE
                isVolumeOn = true
            }
        }

        binding.ivMute.setOnClickListener {
            if (isVolumeOn) {
                binding.ivVolume.visibility = View.GONE
                binding.ivMute.visibility = View.VISIBLE
                isVolumeOn = false
            } else {
                binding.ivVolume.visibility = View.VISIBLE
                binding.ivMute.visibility = View.GONE
                isVolumeOn = true
            }
        }
    }

    private fun setTextToSpeechListener() {
        if (isAdded && textToSpeech == null) {
            textToSpeech = TextToSpeech(requireContext()) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val ttsLang = textToSpeech!!.setLanguage(Locale.US)
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                        || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED
                    ) {
                        Log.d("TTS", "The Language is not supported!")
                    } else {
                        Log.d("TTS", "Language Supported.")
                    }
                    Log.d("TTS", "Initialization success.")
                } else {
                    Log.d("TTS", "Initialization Failed!.")
                }
            }
        }
    }

    private fun getRunAndScoreVoice() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            var ballBy = ""
            if (isMatchLive) {
                if (isFirstInning) {
                    if (!TextUtils.isEmpty(scoreTeam1) && !TextUtils.isEmpty(overTeam1)) {
                        val score = scoreTeam1!!.split("-".toRegex()).toTypedArray()
                        val over = overTeam1!!.split("\\.".toRegex()).toTypedArray()
                        ballBy =
                            score[0] + " runs for " + score[1] + " Wickets in " + over[0] + " Overs"
                    }
                } else {
                    if (!TextUtils.isEmpty(scoreTeam2) && !TextUtils.isEmpty(overTeam2)) {
                        val score = scoreTeam2!!.split("-".toRegex()).toTypedArray()
                        val over = overTeam2!!.split("\\.".toRegex()).toTypedArray()
                        ballBy =
                            score[0] + " runs for " + score[1] + " Wickets in " + over[0] + " Overs"
                    }
                }
            }
            if (isVolumeOn) {
                setTextToSpeechListener()
                val speechStatus = textToSpeech!!.speak(
                    ballBy,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
                if (speechStatus == TextToSpeech.ERROR) {
                    Log.d("TTS", "Error in converting Text to Speech!")
                }

            }
        }, 500)
    }

    private fun createAdDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_dialog_ad)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val adIv = dialog.findViewById<ImageView>(R.id.iv_full_ad)
        val close = dialog.findViewById<ImageView>(R.id.iv_close_ad)
        adIv.setImageBitmap(b)
        close.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onPause() {
        super.onPause()
        if (isVolumeOn) {
            if (textToSpeech != null) {
                textToSpeech!!.stop()
                textToSpeech!!.shutdown()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
    }

    override fun onResume() {
        super.onResume()
        setTextToSpeechListener()

        val preferenceManager = PreferenceManager(requireContext())
        if (preferenceManager.getFullAdsVisible()) {
            if (f.exists()) {
                createAdDialog()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setTextToSpeechListener()
    }

    fun newInstance(myString: String?): LiveLineFragment {
        val myFragment = LiveLineFragment()
        val args = Bundle()
        args.putString(ID, myString)
        myFragment.arguments = args
        return myFragment
    }

}
