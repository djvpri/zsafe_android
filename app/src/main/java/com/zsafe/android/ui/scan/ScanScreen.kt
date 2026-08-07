package com.zsafe.android.ui.scan

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zsafe.android.data.ScanRepository
import com.zsafe.android.data.ScanResult
import com.zsafe.android.ui.common.VerdictCard

/** Layar Pindai — input URL manual lalu scan ke backend. */
@Composable
fun ScanScreen(context: Context) {
    var url by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<ScanResult?>(null) }

    fun scan() {
        if (url.isBlank()) {
            error = "Masukkan URL dulu."
            return
        }
        loading = true
        error = null
        result = runCatching { ScanRepository(context).scanUrl(url.trim()) }
            .onFailure { error = it.message }
            .getOrNull()
        loading = false
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Pindai URL", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = url,
                onValueChange = { url = it; error = null },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("https://…") },
                placeholder = { Text("contoh: https://situs.com/artikel") },
                singleLine = true,
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = ::scan, modifier = Modifier.fillMaxWidth(), enabled = !loading) {
                Text(if (loading) "Memindai…" else "Pindai")
            }

            Spacer(Modifier.height(24.dp))
            when {
                result != null -> VerdictCard(result = result!!, url = url)
                loading -> CircularProgressIndicator()
                error != null -> {
                    Text("Gagal memindai: $error", color = MaterialTheme.colorScheme.error)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = ::scan) { Text("Coba Lagi") }
                }
            }
        }
    }
}
