package com.wedoapps.CricketLiveLine.Utils

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
import com.wedoapps.CricketLiveLine.Utils.Constants.TEAM1
import com.wedoapps.CricketLiveLine.Utils.Constants.TEAM2
import java.io.File
import java.io.FileOutputStream


class JobSchedular : JobService() {

    private var jobCancelled = false
    private val storageRef = FirebaseStorage.getInstance().reference

    override fun onStartJob(params: JobParameters?): Boolean {
        val team1 = params?.extras?.getString(TEAM1)
        val team2 = params?.extras?.getString(TEAM2)

        Log.d(TAG, "Team Name in Job: $team1 & $team2")

        storageRef.child("FlagIcon/" + team1?.trim { it <= ' ' } + ".png").downloadUrl.addOnSuccessListener {
            Log.d(TAG, "onStartJob: Job Started")
            saveFile(it, params)
        }

        storageRef.child("FlagIcon/" + team2?.trim { it <= ' ' } + ".png").downloadUrl.addOnSuccessListener {
            Log.d(TAG, "onStartJob2: Job Started2")
            saveFile(it, params)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob: Stopped")
        jobCancelled = true
        return true
    }

    private fun saveFile(sourceUri: Uri, params: JobParameters?) {
        if (jobCancelled) {
            val contextWrapper = ContextWrapper(applicationContext)
            val direct = contextWrapper.getDir("/CricketLiveLine", Context.MODE_PRIVATE)

            if (!direct.exists()) {
                val wallpaperDirectory = File("/CricketLiveLine/")
                wallpaperDirectory.mkdirs()
            }

            val file = File("/CricketLiveLine/", sourceUri.toString())
            if (file.exists()) {
                file.delete()
            }
            try {
                val out = FileOutputStream(file)
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, sourceUri)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                Log.d(TAG, "saveFile: File Saved")
                jobFinished(params, false)
            } catch (e: Exception) {
                Log.d(TAG, "saveFile: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

}