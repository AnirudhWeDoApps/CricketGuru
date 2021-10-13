package com.wedoapps.CricketLiveLine.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL


class DownloadImageWorker(mContext: Context, workerParams: WorkerParameters) :
    Worker(mContext, workerParams) {

    private val storageRef = FirebaseStorage.getInstance().reference
    private var firestore = FirebaseFirestore.getInstance()
    private val preference = PreferenceManager(mContext)

    override fun doWork(): Result {
        Log.d(TAG, Thread.currentThread().toString())


        val thread1 = Thread {
            try {
                getAds()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        thread1.start()

        val thread2 = Thread {
            try {
                getFullAdUrl()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        thread2.start()

        val thread = Thread {
            try {
                getSmallBannerUrl()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        thread.start()

        return Result.success()
    }

    private fun getAds() {
        firestore.collection("LiveMatch").document("NewAppAdsAndroid")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen Failed", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    Log.d(TAG, "doWork Data: ${value.data}")
                    if (value.data!!.containsKey("AdVisible")) {
                        Constants.isAdsVisible = value.getBoolean("AdVisible")!!
                        preference.setAdsVisible(Constants.isAdsVisible)
                        Constants.isAdsVisible = preference.getAdsVisible()
                    }

                    if (value.data!!.containsKey("IsFullAdsShow")) {
                        Constants.isFullAddsShow = value.getBoolean("IsFullAdsShow")!!
                        preference.setFullAdsVisible(Constants.isFullAddsShow)
                        Constants.isFullAddsShow = preference.getFullAdsVisible()
                    }

                    if (value.data!!.containsKey("FullAdsName")) {
                        Constants.FULL_BANNER_ADS_NAME =
                            value.getString("FullAdsName")!!
                        preference.setFullAdName(value.getString("FullAdsName")!!)
                    }

                    if (value.data!!.containsKey("Big_banner_Ads")) {
                        Constants.BIG_BANNER_ADS_NAME =
                            value.getString("Big_banner_Ads")!!
                    }

                    if (value.data!!.containsKey("BannerAds")) {
                        Constants.SMALL_BANNER_ADS_NAME =
                            value.getString("BannerAds")!!
                        preference.setSmallAdName(value.getString("BannerAds")!!)
                        Log.d(TAG, "doWork: VALUE GOT")
                    } else {
                        Log.d(TAG, "doWork: NO value")
                    }
                }
            }
    }

    private fun getFullAdUrl() {
        if (!TextUtils.isEmpty(preference.getFullAdName())) {
            storageRef.child(Constants.ADS_STORAGE_PATH + preference.getFullAdName()).downloadUrl
                .addOnSuccessListener { uri ->
//                    val bitmap: Bitmap = CommonUtils.getImageBitmap(uri.toString())!!

                    val fullBannerUrl = uri.toString()
                    Log.d(TAG, "getFullBannerUrl: $fullBannerUrl")
                    val preferenceSmallBannerUrl =
                        preference.getString(preference.KEY_BANNER_ADS_URL, "")
                    if (TextUtils.isEmpty(preferenceSmallBannerUrl)) {
                        val thread = Thread {
                            try {
                                storeImage(
                                    URL(fullBannerUrl),
                                    "FullAd"
                                )
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }
                        thread.start()

                    } else {
                        if (preferenceSmallBannerUrl.contentEquals(fullBannerUrl)) {
                            Constants.bannerAdsFilePath =
                                preference.getString(preference.KEY_BANNER_ADS_PATH, "")
                            //homeFragment!!.setSmallBannerAdvertiseView()
                        } else {
                            val thread = Thread {
                                try {
                                    storeImage(
                                        URL(fullBannerUrl),
                                        "FullAd"
                                    )
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                }
                            }
                            thread.start()
                        }
                    }
                }.addOnFailureListener { exception -> // Handle any errors
                    Log.d(TAG, "BigBanner image Failure: ${exception.message}")
                }
        } else {
            Log.d(TAG, "FullAdUrl: TextUtil Empty")
        }
    }


    private fun getSmallBannerUrl() {
        if (!TextUtils.isEmpty(preference.getSmallAdName())) {
            storageRef.child(Constants.ADS_STORAGE_PATH + preference.getSmallAdName()).downloadUrl
                .addOnSuccessListener { uri ->
//                    val bitmap: Bitmap = CommonUtils.getImageBitmap(uri.toString())!!

                    val smallBannerUrl = uri.toString()
                    Log.d(TAG, "getSmallBannerUrl: $smallBannerUrl")
                    val preferenceSmallBannerUrl =
                        preference.getString(preference.KEY_BANNER_ADS_URL, "")
                    if (TextUtils.isEmpty(preferenceSmallBannerUrl)) {
                        val thread = Thread {
                            try {
                                storeImage(
                                    URL(smallBannerUrl),
                                    "Banner"
                                )
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }
                        thread.start()

                    } else {
                        if (preferenceSmallBannerUrl.contentEquals(smallBannerUrl)) {
                            Constants.bannerAdsFilePath =
                                preference.getString(preference.KEY_BANNER_ADS_PATH, "")
                            //homeFragment!!.setSmallBannerAdvertiseView()
                        } else {
                            val thread = Thread {
                                try {
                                    storeImage(
                                        URL(smallBannerUrl),
                                        "Banner"
                                    )
                                } catch (e: java.lang.Exception) {
                                    e.printStackTrace()
                                }
                            }
                            thread.start()
                        }
                    }
                }.addOnFailureListener { exception -> // Handle any errors
                    Log.d(TAG, "Small image Failure: ${exception.message}")
                }

        }

    }

    private fun storeImage(url: URL, name: String) {
        val pictureFile = getOutputMediaFile(name)
        if (pictureFile == null) {
            Log.d(
                TAG,
                "Error creating media file, check storage permissions: "
            )
            return
        }
        try {
            val fos = FileOutputStream(pictureFile, true)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.d(TAG, "File not found: " + e.localizedMessage)
        } catch (e: IOException) {
            Log.d(TAG, "Error accessing file: " + e.message)
        }
    }

    private fun getOutputMediaFile(name: String): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        val mediaStorageDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/Android/data/"
                    + applicationContext.packageName
                    + "/Files"
        )

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        // Create a media file name
        return File(mediaStorageDir.path + File.separator + "$name.jpeg")
    }
}