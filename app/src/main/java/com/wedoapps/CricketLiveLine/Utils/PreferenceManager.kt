package com.wedoapps.CricketLiveLine.Utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.wedoapps.CricketLiveLine.Utils.Constants.FULL_ADS
import com.wedoapps.CricketLiveLine.Utils.Constants.FULL_NAME
import com.wedoapps.CricketLiveLine.Utils.Constants.SET_ADS
import com.wedoapps.CricketLiveLine.Utils.Constants.SMALL_NAME

class PreferenceManager(context: Context) {

    val KEY_BANNER_ADS_URL = "URL"
    val KEY_BANNER_ADS_PATH = "PATH"
    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setAdsVisible(ads: Boolean) {
        prefs.edit().putBoolean(SET_ADS, ads).apply()
    }

    fun getAdsVisible(): Boolean {
        return prefs.getBoolean(SET_ADS, false)
    }

    fun setFullAdsVisible(fullAds: Boolean) {
        prefs.edit().putBoolean(FULL_ADS, fullAds).apply()
    }

    fun getFullAdsVisible(): Boolean {
        return prefs.getBoolean(FULL_ADS, false)
    }

    fun getString(keyBannerAdsUrl: String, s: String): String {
        return prefs.getString(keyBannerAdsUrl, s)!!

    }

    fun setFullAdName(name: String) {
        prefs.edit().putString(FULL_NAME, name).apply()
    }

    fun getFullAdName(): String? {
        return prefs.getString(FULL_NAME, "")
    }

    fun setSmallAdName(name: String) {
        prefs.edit().putString(SMALL_NAME, name).apply()
    }

    fun getSmallAdName(): String? {
        return prefs.getString(SMALL_NAME, "")
    }
}