package com.zsafe.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zsafe.android.ui.ZsafeApp
import com.zsafe.android.ui.theme.ZsafeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZsafeTheme {
                ZsafeApp(intent?.data?.toString())
            }
        }
    }
}
