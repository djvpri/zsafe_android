# Zsafe Android

App Android untuk Zsafe — link protection & security device membership.

## Status
Scaffold MVP v0.1.0:
- Project **Kotlin + Jetpack Compose** (Material 3)
- Dashboard utama (desain varian 1 "Calm Trust", tema teal)
- Link checker: intercept URL (Intent filter http/https) → scan via backend → verdict
- Login Google & Play Billing: **dependency sudah di deps, belum di-wire** (butuh akun Play Developer)

## Build (di Android Studio)
```bash
# Buka Android Studio → Open → pilih folder ini → biarkan Gradle sync
# Pastikan SDK 34 terinstall (compileSdk 34, minSdk 26, target 34)
./gradlew assembleDebug
```

## Struktur
```
app/src/main/java/com/zsafe/android/
├── MainActivity.kt          # entry, terima Intent link
├── ui/
│   ├── ZsafeApp.kt          # navigasi + bottom nav
│   ├── theme/Theme.kt       # tema teal
│   ├── dashboard/DashboardScreen.kt
│   └── linkcheck/LinkCheckScreen.kt
└── data/ScanRepository.kt   # panggil POST /api/scan/url (backend zsafe_backend)
```

## Backend
Default `baseUrl = https://api.zsafe.app`. Ubah di `ScanRepository.kt` sesuai deployment backend-mu.

## Catatan
- Icon: pakai `material-icons-core` (subset). Ikon lain di `material-icons-extended` tak dipakai biar ukuran APK kecil.
- Emoji/icon standar: TIDAK dipakai (sesuai preferensi project).
- Belum: wiring Google sign-in, Play Billing, notifikasi push, layar scan/proteksi penuh.
