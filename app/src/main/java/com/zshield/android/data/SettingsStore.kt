package com.zshield.android.data

import android.content.Context

/** Preferensi lokal sederhana (backend URL). Tanpa DI — init manual via Context. */
class SettingsStore(context: Context) {
    private val prefs = context.getSharedPreferences("zshield_settings", Context.MODE_PRIVATE)

    companion object {
        // ponytail: default prod. Custom base URL utk dev/testing di layar Setelan.
        const val DEFAULT_BASE_URL = "https://zsafebackend-production.up.railway.app"
        private const val KEY_BASE_URL = "backend_url"
    }

    val baseUrl: String
        get() = prefs.getString(KEY_BASE_URL, DEFAULT_BASE_URL) ?: DEFAULT_BASE_URL

    fun setBaseUrl(url: String) {
        prefs.edit().putString(KEY_BASE_URL, url.trim().trimEnd('/')).apply()
    }
}
