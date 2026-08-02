package com.zsafe.android.data

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

// Hasil scan URL dari backend: POST /api/scan/url → { verdict, reason, host, ... }
data class ScanResult(
    val verdict: String,
    val reason: String,
    val host: String?,
)

class ScanRepository(
    private val baseUrl: String = "https://api.zsafe.app",
) {
    fun scanUrl(url: String): ScanResult {
        val conn = URL("$baseUrl/api/scan/url").openConnection() as HttpURLConnection
        try {
            conn.requestMethod = "POST"
            conn.connectTimeout = 10_000
            conn.readTimeout = 15_000
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json")
            val payload = JSONObject().put("url", url).toString()
            conn.outputStream.use { it.write(payload.toByteArray()) }

            val code = conn.responseCode
            if (code != 200) throw RuntimeException("Server error $code")
            val body = conn.inputStream.bufferedReader().readText()
            val json = JSONObject(body)
            return ScanResult(
                verdict = json.optString("verdict", "error"),
                reason = json.optString("reason", ""),
                host = if (json.has("host") && !json.isNull("host")) json.optString("host") else null,
            )
        } finally {
            conn.disconnect()
        }
    }
}
