package com.zshield.android.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Dashboard utama — status aman + skor + ringkasan proteksi (desain varian 1). */
@Composable
fun DashboardScreen(onScanClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(
                "ZShield", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp),
            )
        }
        Spacer(Modifier.height(18.dp))

        // Hero score
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("STATUS PERANGKAT", fontSize = 12.sp, letterSpacing = 1.sp, color = Color.White.copy(alpha = 0.9f))
                Text(
                    "96", fontSize = 64.sp, fontWeight = FontWeight.ExtraBold,
                    color = Color.White, lineHeight = 64.sp,
                )
                Text("Aman — Terlindungi", color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(
                    "Terakhir diperiksa 5 menit lalu", color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp),
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        // Stat grid 2x2
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(Icons.Filled.CheckCircle, "3.214", "File dipindai", Color(0xFFE2F4F1), Modifier.weight(1f))
            StatCard(Icons.Filled.Warning, "2", "Ancaman diblokir", Color(0xFFFDE7E5), Modifier.weight(1f))
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(Icons.Filled.Lock, "1", "WiFi tak aman", Color(0xFFFDF1E0), Modifier.weight(1f))
            StatCard(Icons.Filled.List, "0", "Link mencurigakan", Color(0xFFE2F4F1), Modifier.weight(1f))
        }
        Spacer(Modifier.height(16.dp))

        // Scan CTA
        Card(
            onClick = onScanClick,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(
                    Icons.Filled.Lock, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp),
                )
                Column(Modifier.padding(start = 12.dp)) {
                    Text("Pindai Sekarang", fontWeight = FontWeight.SemiBold)
                    Text("Full malware &amp; permission check", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
            }
        }
    }
}

@Composable
private fun RowScope.StatCard(icon: ImageVector, number: String, label: String, bg: Color, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Column(Modifier.padding(16.dp)) {
            Box(Modifier.background(bg, CircleShape).padding(9.dp)) { Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) }
            Spacer(Modifier.height(10.dp))
            Text(number, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }
    }
}
