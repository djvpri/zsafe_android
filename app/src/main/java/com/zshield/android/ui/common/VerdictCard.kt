package com.zshield.android.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zshield.android.data.ScanResult

/** Kartu hasil verdict scan — dipakai layar Pindai & Link Check. */
@Composable
fun VerdictCard(result: ScanResult, url: String) {
    val (title, desc) = when (result.verdict) {
        "safe" -> "Link Aman" to "Tidak ditemukan ancaman."
        "warn" -> "Link Mencurigakan" to "Berhati-hati — pola tidak biasa terdeteksi."
        "block" -> "Link Berbahaya" to "Phishing terdeteksi. Amankan perangkat Anda."
        else -> "Hasil Tidak Dikenal" to result.reason
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(desc, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        Spacer(Modifier.height(12.dp))
        Text("URL: $url", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
    }
}
