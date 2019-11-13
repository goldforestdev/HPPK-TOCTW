package com.hppk.toctw.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_CODE_UPDATE = 1113

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }
    private val updateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationMenu()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.boothFragment,
                R.id.childrenFragment,
                R.id.infoFragment,
                R.id.settingsFragment
            ), drawerLayout
        )

        checkUpdatable()
    }

    override fun onResume() {
        super.onResume()

        updateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    // If an in-app update is already running, resume the update.
                    updateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, this, REQUEST_CODE_UPDATE)
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigationMenu() {
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    private fun checkUpdatable() {
        updateManager.appUpdateInfo
            .addOnFailureListener { t ->
                Log.e(TAG, "[TOCTW] checkUpdateable - failed: ${t.message}", t)
            }
            .addOnSuccessListener { updateInfo ->
                Log.d(TAG, "[TOCTW] checkUpdateable - updateAvailability: ${getUpdateAvailabilityText(updateInfo.updateAvailability())}")

                if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && updateInfo.isUpdateTypeAllowed(IMMEDIATE)
                ) {
                    updateManager.startUpdateFlowForResult(updateInfo, IMMEDIATE, this, REQUEST_CODE_UPDATE)
                }
            }
    }

    private fun getUpdateAvailabilityText(updateAvailability: Int): String =
        when (updateAvailability) {
            UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> "DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS"
            UpdateAvailability.UPDATE_AVAILABLE -> "UPDATE_AVAILABLE"
            UpdateAvailability.UPDATE_NOT_AVAILABLE -> "UPDATE_NOT_AVAILABLE"
            else -> "UNKNOWN"
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e(TAG, "Update flow failed! Result code: $resultCode")
            }
        }
    }
}
