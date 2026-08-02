package com.zsafe.android.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zsafe.android.ui.dashboard.DashboardScreen
import com.zsafe.android.ui.linkcheck.LinkCheckScreen

/** Root composable: bottom nav + screens + handles inbound link intent. */
@Composable
fun ZsafeApp(inboundUrl: String?) {
    val navController = rememberNavController()
    var interceptedUrl by rememberSaveable { mutableStateOf(inboundUrl) }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val items = listOf(
                    NavItem("home", "Beranda", Icons.Filled.Home),
                    NavItem("scan", "Pindai", Icons.Filled.Search),
                    NavItem("link", "Link", Icons.Filled.Lock),
                    NavItem("settings", "Setelan", Icons.Filled.Settings),
                )
                items.forEach { item ->
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(item.route) },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding),
        ) {
            composable("home") { DashboardScreen() }
            composable("scan") { Text("Layar pindai — WIP") }
            composable("link") {
                // If an inbound URL arrived via Intent, scan it here.
                LinkCheckScreen(url = interceptedUrl)
            }
            composable("settings") { Text("Layar setelan — WIP") }
        }
    }
}

private data class NavItem(val route: String, val label: String, val icon: ImageVector)
