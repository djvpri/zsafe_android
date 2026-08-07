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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.zsafe.android.data.SettingsStore
import com.zsafe.android.ui.dashboard.DashboardScreen
import com.zsafe.android.ui.linkcheck.LinkCheckScreen
import com.zsafe.android.ui.scan.ScanScreen
import com.zsafe.android.ui.settings.SettingsScreen

/** Root composable: bottom nav + screens + handles inbound link intent. */
@Composable
fun ZsafeApp(inboundUrl: String?) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
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
                        selected = currentRoute == item.route,
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
            composable("home") { DashboardScreen(onScanClick = { navController.navigate("scan") }) }
            composable("scan") { ScanScreen(context = context) }
            composable("link") {
                // If an inbound URL arrived via Intent, scan it here.
                LinkCheckScreen(url = interceptedUrl, context = context)
            }
            composable("settings") {
                SettingsScreen(store = SettingsStore(context))
            }
        }
    }
}

private data class NavItem(val route: String, val label: String, val icon: ImageVector)
