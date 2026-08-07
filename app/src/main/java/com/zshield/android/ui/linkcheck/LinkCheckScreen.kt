package com.zshield.android.ui.linkcheck

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zshield.android.data.ScanRepository
import com.zshield.android.data.ScanResult
import com.zshield.android.ui.common.VerdictCard

/** Layar pemeriksaan link. URL inbound (dari Intent) langsung discan. */
@Composable
fun LinkCheckScreen(url: String?, context: Context) {
    var result by remember { mutableStateOf<ScanResult?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var attempt by remember { mutableStateOf(0) }

    fun scan(target: String) {
        loading = true
        error = null
        result = runCatching { ScanRepository(context).scanUrl(target) }
            .onFailure { error = it.message }
            .getOrNull()
        loading = false
    }

    val target = url
    LaunchedEffect(target, attempt) {
        if (target != null) scan(target)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Pemeriksaan Link", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(20.dp))

            if (url != null) {
                Text(url, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Spacer(Modifier.height(24.dp))
            }

            when {
                loading -> CircularProgressIndicator()
                error != null -> {
                    Text("Gagal memindai: $error", color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { attempt++ }) {
                        Text("Coba Lagi")
                    }
                }
                result != null -> VerdictCard(result = result!!, url = url ?: "")
                url == null -> Text("Tidak ada link untuk diperiksa.", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            }
        }
    }
}
