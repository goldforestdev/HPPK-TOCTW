package com.hppk.toctw.ui.settings


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.hppk.toctw.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {

    private val prefRateReview: Preference by lazy {
        findPreference<Preference>(getString(R.string.key_rating_review)) as Preference
    }
    private val prefFeedback: Preference by lazy {
        findPreference<Preference>(getString(R.string.key_feedback)) as Preference
    }
    private val prefVersion: Preference by lazy {
        findPreference<Preference>(getString(R.string.key_version)) as Preference
    }
    private val prefDevelopers: Preference by lazy {
        findPreference<Preference>(getString(R.string.key_developers)) as Preference
    }

    private val appVersion: String by lazy {
        context?.applicationContext?.packageManager?.getPackageInfo(
            context?.applicationContext?.packageName,
            0
        )?.versionName ?: "none"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        prefRateReview.onPreferenceClickListener = this
        prefFeedback.onPreferenceClickListener = this
        prefDevelopers.onPreferenceClickListener = this
        prefVersion.summary = appVersion
    }

    override fun onPreferenceClick(preference: Preference?) = when (preference?.key) {
        getString(R.string.key_rating_review) -> goPlayStore()
        getString(R.string.key_feedback) -> sendFeedback()
        getString(R.string.key_developers) -> showDevelopers()
        else -> false
    }

    private fun sendFeedback(): Boolean {
        val emailIntent = ShareCompat.IntentBuilder.from(activity)
            .setType("plain/text")
            .setEmailTo(listOf(getString(R.string.email)).toTypedArray())
            .setSubject(
                getString(
                    R.string.send_feedback_email_subject,
                    getString(R.string.app_name),
                    appVersion
                )
            )
            .setText(getString(R.string.send_feedback_email_details))
            .intent

        startActivity(emailIntent)
        return true
    }

    private fun goPlayStore(): Boolean {
        val pkgName = context?.packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkgName")))
        } catch (t: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$pkgName")
                )
            )
        }
        return true
    }

    private fun showDevelopers(): Boolean {
        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToDevelopersFragment())
        return true
    }

}
