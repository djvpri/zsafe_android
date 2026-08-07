package com.zshield.android.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zshield.android.data.SettingsStore

/** Layar Setelan — atur URL backend ZShield. Tanpa auto-connect (manual save). */
@Composable
fun SettingsScreen(store: SettingsStore) {
    val storeBase = remember(store) { store.baseUrl }
    var url by rememberSaveable { mutableStateOf(storeBase) }
    var savedMsg by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text("Setelan", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(20.dp))

            Text(
                "URL Backend ZShield",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = url,
                onValueChange = { url = it; savedMsg = false },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("https://…") },
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    store.setBaseUrl(url)
                    savedMsg = true
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Simpan")
            }

            if (savedMsg) {
                Spacer(Modifier.height(12.dp))
                Text("Tersimpan ✓", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.height(24.dp))
            Text(
                "URL ini dipakai untuk memindai link (POST /api/scan/url). Default: ${SettingsStore.DEFAULT_BASE_URL}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
        }
    }
}
