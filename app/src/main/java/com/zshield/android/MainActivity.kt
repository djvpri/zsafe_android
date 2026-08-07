package com.zshield.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.zshield.android.ui.ZShieldApp
import com.zshield.android.ui.theme.ZShieldTheme

class MainActivity : ComponentActivity() {
    // URL inbound terbaru (dari VIEW / SEND). State supaya recompose saat onNewIntent.
    private val inboundUrl = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        inboundUrl.value = extractUrl(intent)
        setContent {
            ZShieldTheme {
                ZShieldApp(inboundUrl = inboundUrl)
            }
        }
    }

    // launchMode=singleTask: intent baru (share/klik link) saat app sudah berjalan
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        inboundUrl.value = extractUrl(intent)
    }

    /** Ambil URL dari Intent VIEW (data) atau SEND (EXTRA_TEXT, share menu). */
    private fun extractUrl(intent: Intent?): String? =
        intent?.data?.toString() ?: intent?.getStringExtra(Intent.EXTRA_TEXT)?.let { firstUrl(it) }

    private fun firstUrl(text: String): String? {
        val match = URL_REGEX.find(text) ?: return null
        return match.value.trimEnd('.', ',', ';', '!', '?', ')', '"')
    }

    companion object {
        private val URL_REGEX = Regex("""https?://[^\s"'<]+""")
    }
}
