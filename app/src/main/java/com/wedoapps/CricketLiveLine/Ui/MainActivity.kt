package com.wedoapps.CricketLiveLine.Ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.work.*
import com.wedoapps.CricketLiveLine.Db.CricketGuruDatabase
import com.wedoapps.CricketLiveLine.R
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import com.wedoapps.CricketLiveLine.Utils.Constants
import com.wedoapps.CricketLiveLine.Utils.DownloadImageWorker
import com.wedoapps.CricketLiveLine.Utils.ViewModelProviderFactory
import com.wedoapps.CricketLiveLine.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CricketGuruViewModel
    private val permission = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar.toolbarTop
        setSupportActionBar(toolbar)

        /*  permissionsLauncher =
              registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                  readPermissionGranted =
                      permissions[READ_EXTERNAL_STORAGE] ?: readPermissionGranted
                  writePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                      ?: writePermissionGranted
              }
          updateOrRequestPermissions()*/

        checkPermission(
            Constants.REQUEST_PERMISSION
        )

        val repository = CricketGuruRepository(CricketGuruDatabase(this))
        val viewModelProvider = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProvider)[CricketGuruViewModel::class.java]
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navHostFragment.navController
    }


    // Function to check and request permission.
    private fun checkPermission(requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission[0]
            ) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(
                this,
                permission[1]
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this@MainActivity,
                permission.toTypedArray(), requestCode
            )
        } else {
            startWorker()
            /*Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()*/
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*  Toast.makeText(this@MainActivity, "Storage Permission Granted", Toast.LENGTH_SHORT)
                      .show()*/
                startWorker()
            } else {
                /*Toast.makeText(this@MainActivity, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()*/
            }
        }
    }

    override fun onStart() {
        super.onStart()
        startWorker()
    }


    private fun startWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(DownloadImageWorker::class.java)
            .setConstraints(constraints.build())
            .addTag(Constants.TAG)
            .build()

//        Toast.makeText(requireContext(), "Starting worker", Toast.LENGTH_SHORT).show()

        WorkManager.getInstance()
            .enqueueUniqueWork(Constants.TAG, ExistingWorkPolicy.KEEP, oneTimeWorkRequest)
    }

}