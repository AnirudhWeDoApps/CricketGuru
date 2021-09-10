package com.wedoapps.CricketLiveLine.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import com.wedoapps.CricketLiveLine.Model.*
import com.wedoapps.CricketLiveLine.Model.Info.Info
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CricketGuruRepository {
    private var firestore = FirebaseFirestore.getInstance()
    private var mutableLangName = MutableLiveData<MutableList<HomeMatch>>()
    private var mutableData = MutableLiveData<HomeMatch>()
    private var _team1: MutableLiveData<Score> = MutableLiveData<Score>()
    private var _team2: MutableLiveData<Score> = MutableLiveData<Score>()
    private var _teamExtras: MutableLiveData<String> = MutableLiveData<String>()
    private var _teamExtras2: MutableLiveData<String> = MutableLiveData<String>()
    private var _matchInfo: MutableLiveData<Info> = MutableLiveData<Info>()
    private var _teamScore: MutableLiveData<TeamScore> = MutableLiveData<TeamScore>()
    private var _teamScore2: MutableLiveData<TeamScore> = MutableLiveData<TeamScore>()
    private var _bowlerlist1: MutableLiveData<BowlerList> = MutableLiveData<BowlerList>()
    private var _bowlerlist2: MutableLiveData<BowlerList> = MutableLiveData<BowlerList>()
    private var _wicketList1: MutableLiveData<AllWicketList> = MutableLiveData<AllWicketList>()
    private var _wicketList2: MutableLiveData<AllWicketList> = MutableLiveData<AllWicketList>()
    private val firestoreRef = firestore.collection("MatchList")
    private var scoreDataModelArrayList1 = ArrayList<PlayerScore>()
    private val scoreDataModelArrayList2 = ArrayList<PlayerScore>()
    private val bowlerDataModelArrayList1 = ArrayList<Bowlers>()
    private val bowlerDataModelArrayList2 = ArrayList<Bowlers>()
    private val wicketDataModelArrayList1 = ArrayList<WicketFall>()
    private val wicketDataModelArrayList2 = ArrayList<WicketFall>()
    private val teamScore = TeamScore()
    private val bowlerList = BowlerList()
    private var homeMatch = HomeMatch()
    private var wicketList = AllWicketList()

    fun team1(id: String): MutableLiveData<Score> {
        firestoreRef.document(id).collection("LiveMatch").document("ScoreTeam1")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    //                    val allTeam1 = ArrayList<Score>()
                    val allTeam1 = value.toObject(Score::class.java)!!
                    //                    allTeam1?.add(allTeam1)
                    _team1.value = allTeam1
                    Log.d(TAG, "team1: $allTeam1")
                } else {
                    Log.d(TAG, "No Data")
                }
            }
        return _team1
    }

    fun team2(id: String): MutableLiveData<Score> {
        firestoreRef.document(id).collection("LiveMatch").document("ScoreTeam2")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val allTeam2 = value.toObject(Score::class.java)!!
                    _team2.value = allTeam2
                    Log.d(TAG, "team2: $allTeam2")
                } else {
                    Log.d(TAG, "No Data")
                }
            }
        return _team2
    }


    fun getMatch(): MutableLiveData<MutableList<HomeMatch>> {

        firestore.collection("MatchList")
            .whereLessThan("MatchIndex", 100)
            .orderBy("MatchIndex", Query.Direction.ASCENDING)
            .limit(10)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val allMatch = ArrayList<HomeMatch>()
                    val match = value.documents
                    match.forEach {
                        homeMatch = it.toObject(HomeMatch::class.java)!!
                        homeMatch.id = it.id
//                        getScore(it.id)
                        allMatch.add(homeMatch)
                    }
                    mutableLangName.value = allMatch
                } else {
                    Log.d(TAG, "No Data")
                }
            }
        return mutableLangName
    }

    fun extrasTeam1(id: String): MutableLiveData<String> {
        firestoreRef.document(id).collection("ScoreCard").document("Extra")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val data = value.data
                    if (data != null) {
                        _teamExtras.value = data["Extra1"].toString()
                    }
                } else {
                    Log.d(TAG, "No Data")
                }
            }
        return _teamExtras
    }

    fun extrasTeam2(id: String): MutableLiveData<String> {
        firestoreRef.document(id).collection("ScoreCard").document("Extra")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val data = value.data
                    if (data != null) {
                        _teamExtras2.value = data["Extra2"].toString()
                    }
                } else {
                    Log.d(TAG, "No Data")
                }
            }
        return _teamExtras2
    }

    fun getScore1(id: String): MutableLiveData<TeamScore> {
        scoreDataModelArrayList1.clear()
        firestoreRef.document(id).collection("ScoreCard").document("ScoreTeam1")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    if (value.exists()) {
                        try {
                            if (Objects.requireNonNull(
                                    value.data
                                ).containsKey("ScoreTeam1")
                            ) {
                                val dataModelArrayList1: ArrayList<HashMap<String, String>>? =
                                    value["ScoreTeam1"] as ArrayList<HashMap<String, String>>?
                                scoreDataModelArrayList1.clear()
                                if (dataModelArrayList1 != null && dataModelArrayList1.size > 0) {
                                    for (i in dataModelArrayList1.indices) {
                                        val team1HashMap = dataModelArrayList1[i]
                                        val scoreDataModel = PlayerScore()
                                        scoreDataModel.Name = team1HashMap["Name"]
                                        scoreDataModel.OtherInfo = team1HashMap["OtherInfo"]
                                        scoreDataModel.Ball = team1HashMap["Ball"]
                                        scoreDataModel.Run = team1HashMap["Run"]
                                        scoreDataModel.fours = team1HashMap["4s"]
                                        scoreDataModel.sixes = team1HashMap["6s"]
                                        scoreDataModel.SR = team1HashMap["SR"]
                                        scoreDataModelArrayList1.add(scoreDataModel)
                                    }
                                }
                                teamScore.playerScore = scoreDataModelArrayList1
                                Log.d(TAG, "getDetails1: $teamScore")
                                _teamScore.value = teamScore

                            }
                        } catch (index: IndexOutOfBoundsException) {
                            index.printStackTrace()
                        }
                    } else {
                        Log.d(TAG, "NO DATA")
                    }
                }
            }
        return _teamScore
    }

    fun getScore2(id: String): MutableLiveData<TeamScore> {
        scoreDataModelArrayList2.clear()
        firestoreRef.document(id).collection("ScoreCard").document("ScoreTeam2")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    if (value.exists()) {
                        try {
                            if (Objects.requireNonNull(
                                    value.data
                                ).containsKey("ScoreTeam2")
                            ) {
                                val dataModelArrayList1: ArrayList<HashMap<String, String>>? =
                                    value["ScoreTeam2"] as ArrayList<HashMap<String, String>>?
                                scoreDataModelArrayList2.clear()
                                if (dataModelArrayList1 != null && dataModelArrayList1.size > 0) {
                                    for (i in dataModelArrayList1.indices) {
                                        val team1HashMap = dataModelArrayList1[i]
                                        val scoreDataModel2 = PlayerScore()
                                        scoreDataModel2.Name = team1HashMap["Name"]
                                        scoreDataModel2.OtherInfo = team1HashMap["OtherInfo"]
                                        scoreDataModel2.Ball = team1HashMap["Ball"]
                                        scoreDataModel2.Run = team1HashMap["Run"]
                                        scoreDataModel2.fours = team1HashMap["4s"]
                                        scoreDataModel2.sixes = team1HashMap["6s"]
                                        scoreDataModel2.SR = team1HashMap["SR"]
                                        scoreDataModelArrayList2.add(scoreDataModel2)
                                    }
                                    teamScore.playerScore2 = scoreDataModelArrayList2
                                    Log.d(TAG, "getDetails2: $teamScore")
                                    _teamScore2.value = teamScore
                                }
                            }
                        } catch (index: IndexOutOfBoundsException) {
                            index.printStackTrace()
                        }
                    } else {
                        Log.d(TAG, "NO DATA")
                    }
                }
            }
        return _teamScore2
    }

    fun getSpecificMatchData(id: String): MutableLiveData<HomeMatch> {

        firestore.collection("MatchList").document(id)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val allMatch: HomeMatch = value.toObject(HomeMatch::class.java)!!
                    mutableData.value = allMatch
                } else {
                    Log.d(TAG, "No Data")
                }
            }
        return mutableData
    }

    fun getBowlerList1(id: String): MutableLiveData<BowlerList> {
        bowlerDataModelArrayList1.clear()
        firestoreRef.document(id).collection("ScoreCard").document("BowlerList1")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    if (value.exists()) {
                        try {
                            if (Objects.requireNonNull(
                                    value.data
                                ).containsKey("BowlerList1")
                            ) {
                                val dataModelArrayList1: ArrayList<HashMap<String, String>>? =
                                    value["BowlerList1"] as ArrayList<HashMap<String, String>>?
                                bowlerDataModelArrayList1.clear()
                                if (dataModelArrayList1 != null && dataModelArrayList1.size > 0) {
                                    for (i in dataModelArrayList1.indices) {
                                        val bowler1HashMap = dataModelArrayList1[i]
                                        val bowler = Bowlers()
                                        bowler.Name = bowler1HashMap["Name"]
                                        bowler.Over = bowler1HashMap["Over"]
                                        bowler.Wicket = bowler1HashMap["Wicket"]
                                        bowler.Maiden = bowler1HashMap["Maiden"]
                                        bowler.Run = bowler1HashMap["Run"]
                                        bowler.ER = bowler1HashMap["ER"]
                                        bowlerDataModelArrayList1.add(bowler)
                                    }
                                }
                                bowlerList.team1List = bowlerDataModelArrayList1
                                Log.d(TAG, "getBowler List1: $bowlerList")
                                _bowlerlist1.value = bowlerList

                            }
                        } catch (index: IndexOutOfBoundsException) {
                            index.printStackTrace()
                        }
                    } else {
                        Log.d(TAG, "NO DATA")
                    }
                }
            }
        return _bowlerlist1
    }

    fun getBowlerList2(id: String): MutableLiveData<BowlerList> {
        bowlerDataModelArrayList2.clear()
        firestoreRef.document(id).collection("ScoreCard").document("BowlerList2")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    if (value.exists()) {
                        Log.d(TAG, "getBowlerList2 Outer: ${value.data}")
                        try {
                            if (Objects.requireNonNull(
                                    value.data
                                ).containsKey("BowlerList2")
                            ) {
                                val dataModelArrayList1: ArrayList<HashMap<String, String>>? =
                                    value["BowlerList2"] as ArrayList<HashMap<String, String>>?
                                bowlerDataModelArrayList2.clear()
                                if (dataModelArrayList1 != null && dataModelArrayList1.size > 0) {
                                    for (i in dataModelArrayList1.indices) {
                                        val bowler1HashMap = dataModelArrayList1[i]
                                        val bowler = Bowlers()
                                        bowler.Name = bowler1HashMap["Name"]
                                        bowler.Over = bowler1HashMap["Over"]
                                        bowler.Wicket = bowler1HashMap["Wicket"]
                                        bowler.Maiden = bowler1HashMap["Maiden"]
                                        bowler.Run = bowler1HashMap["Run"]
                                        bowler.ER = bowler1HashMap["ER"]
                                        bowlerDataModelArrayList2.add(bowler)
                                    }
                                }
                                bowlerList.team2List = bowlerDataModelArrayList2
                                Log.d(TAG, "getBowler List2: $bowlerList")
                                _bowlerlist2.value = bowlerList

                            }
                        } catch (index: IndexOutOfBoundsException) {
                            index.printStackTrace()
                        }
                    } else {
                        Log.d(TAG, "NO DATA")
                    }
                }
            }
        return _bowlerlist2
    }

    fun getWicketList1(id: String): MutableLiveData<AllWicketList> {
        wicketDataModelArrayList1.clear()
        firestoreRef.document(id).collection("ScoreCard").document("WicketList1")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    if (value.exists()) {
                        Log.d(TAG, "WicketList1 Outer: ${value.data}")
                        try {
                            if (Objects.requireNonNull(
                                    value.data
                                ).containsKey("WicketList1")
                            ) {
                                val dataModelArrayList1: ArrayList<HashMap<String, String>>? =
                                    value["WicketList1"] as ArrayList<HashMap<String, String>>?
                                wicketDataModelArrayList1.clear()
                                if (dataModelArrayList1 != null && dataModelArrayList1.size > 0) {
                                    for (i in dataModelArrayList1.indices) {
                                        val wicket1HashMap = dataModelArrayList1[i]
                                        val wicketFall = WicketFall()
                                        wicketFall.Name = wicket1HashMap["Name"]
                                        wicketFall.Over = wicket1HashMap["Over"]
                                        wicketFall.Score = wicket1HashMap["Score"]
                                        wicketDataModelArrayList1.add(wicketFall)
                                    }
                                }
                                wicketList.wicketList1 = wicketDataModelArrayList1
                                Log.d(TAG, "WicketList1 List1: $wicketList")
                                _wicketList1.value = wicketList

                            }
                        } catch (index: IndexOutOfBoundsException) {
                            index.printStackTrace()
                            index.printStackTrace()
                        }
                    } else {
                        Log.d(TAG, "NO DATA")
                    }
                }
            }
        return _wicketList1
    }


    fun getWicketList2(id: String): MutableLiveData<AllWicketList> {
        wicketDataModelArrayList2.clear()
        firestoreRef.document(id).collection("ScoreCard").document("WicketList2")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    if (value.exists()) {
                        Log.d(TAG, "WicketList2 Outer: ${value.data}")
                        try {
                            if (Objects.requireNonNull(
                                    value.data
                                ).containsKey("WicketList2")
                            ) {
                                val dataModelArrayList1: ArrayList<HashMap<String, String>>? =
                                    value["WicketList2"] as ArrayList<HashMap<String, String>>?
                                wicketDataModelArrayList2.clear()
                                if (dataModelArrayList1 != null && dataModelArrayList1.size > 0) {
                                    for (i in dataModelArrayList1.indices) {
                                        val wicket1HashMap = dataModelArrayList1[i]
                                        val wicketFall = WicketFall()
                                        wicketFall.Name = wicket1HashMap["Name"]
                                        wicketFall.Over = wicket1HashMap["Over"]
                                        wicketFall.Score = wicket1HashMap["Score"]
                                        wicketDataModelArrayList2.add(wicketFall)
                                    }
                                }
                                wicketList.wicketList2 = wicketDataModelArrayList2
                                Log.d(TAG, "WicketList1 List1: $wicketList")
                                _wicketList2.value = wicketList

                            }
                        } catch (index: IndexOutOfBoundsException) {
                            index.printStackTrace()
                        }
                    } else {
                        Log.d(TAG, "NO DATA")
                    }
                }
            }
        return _wicketList2
    }

    fun getInfo(id: String): MutableLiveData<Info> {
        firestoreRef.document(id).addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TAG, "Listen Failed", error)
                return@addSnapshotListener
            }

            if (value != null) {
                val info: Info = value.toObject(Info::class.java)!!
                Log.d(TAG, "getInfo: $info")
                _matchInfo.value = info
            } else {
                Log.d(TAG, "NO DATA")
            }
        }
        return _matchInfo
    }
}