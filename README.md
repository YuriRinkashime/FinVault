<p align="center">
  <img src="assets/finvault-icon-512.png" alt="FinVault Yuri Application Icon" width="128" height="128" />
</p>

<h1 align="center">FinVault Yuri</h1>

<p align="center">
  <strong>Offline Dual-Vault Budget Tracker, Savings Reserve & Impulse Control App for Android</strong><br />
  Built with Kotlin, Jetpack Compose, and Material 3 by Yuri Rinkashime
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Version-2.3.0%20Stable-38BDF8?style=flat-square" alt="Version 2.3.0 Stable" />
  <img src="https://img.shields.io/badge/Platform-Android%208.0%2B%20(API%2026%2B)-10B981?style=flat-square" alt="Android 8.0+" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose%20%2B%20Material%203-818CF8?style=flat-square" alt="Jetpack Compose" />
  <img src="https://img.shields.io/badge/Privacy-100%25%20Offline%20%E2%80%A2%20Zero%20Ads-14B8A6?style=flat-square" alt="100% Offline" />
  <img src="https://img.shields.io/badge/License-MIT-F59E0B?style=flat-square" alt="MIT License" />
</p>

---

## Overview

**FinVault Yuri** (`com.yuri.finvault`) is a lightweight, 100% open-source, offline-first personal finance application for Android featuring a pure AMOLED **Matte Titanium** aesthetic. All financial records remain strictly on your device with zero accounts, zero cloud lock-in, zero ads, and zero telemetry.

## Why FinVault Yuri?

| Capability | FinVault Yuri | Typical Budget Apps |
| :--- | :--- | :--- |
| **Dual-Vault Architecture** | Separate **Liquid Vault** & **Goal Reserve Vault** with combined **Total Net Worth** & one-tap **Set Balance** sync | Single flat balance that mixes spending cash and savings |
| **Impulse Cooling-Off Vault** | Lock planned buys for **24h / 72h / 7d** before deciding—or **Resist & Save** into a goal | No impulse cooling-off timer |
| **Time-Cost Converter** | Converts purchase prices & monthly spending into **Hours of Work** | Currency numbers only |
| **Monthly Discipline Heatmap** | Interactive zero-spend / on-pace / peak-spend calendar grid | Basic pie charts only |
| **Privacy & Storage** | 100% local storage, verified JSON backup/restore, CSV export, optional public GitHub release check | Mandatory accounts, cloud sync, or ads |

## Key Features

- **Dual-Vault Matte Titanium Ledger:** Separates spendable **Liquid Vault Balance** from your **Goal Reserve Vault** (with combined **Total Net Worth**), one-tap **Set Balance** sync, Monthly Spending Cap progress, **Safe to Spend Today**, and **Cash Runway**.
- **Goal Transfers & Withdrawals:** Deposit directly into any Savings Goal from the Expense dialog or Goals tab (from Liquid Vault or external reserves), and withdraw partial or full goal reserves back to Liquid Balance anytime.
- **Exclusive Behavioral Finance Tools:**
  - **Impulse Cooling-Off Quarantine Vault:** Lock planned purchases for 24h, 72h, or 7 days before deciding—or **Resist & Save** into a goal to build your **Impulse Resisted** score.
  - **Time-Cost ("Work Hours") Converter:** Translate purchase prices and monthly spend into hours of work using your optional hourly rate.
  - **Monthly Discipline Heatmap:** Interactive calendar grid tracking zero-spend, on-pace, and peak spending days.
- **First-Time Setup & Motion Controls:** 4-step guided onboarding (preserving all data when upgrading from `v2.2.0`), plus a global **Smooth Animations** toggle in Settings.
- **GitHub Release Update Scanner & Developer Settings:** Privacy-safe scanner for official stable releases with direct APK download links—plus a hidden 7-tap **Developer Settings** menu to opt into pre-release builds or replay setup.
- **Month Navigator, Live Search & Quick Presets:** Browse history by month or **All Time** with a live Period Summary (`In / Spend / Net`), search instantly, use Quick Presets, duplicate entries, and recover deletions with a 5-second **Undo** banner.
- **Offline Backup & Diagnostics:** Export/restore verified **JSON** backups with one-tap clipboard paste, generate **CSV** spreadsheet statements, and share privacy-safe crash/diagnostic logs.

## Project Structure

```text
FinVault/
├── assets/
│   └── finvault-icon-512.png          # High-Resolution Repository & Release Icon
├── app/
│   ├── module.toml                    # CodeAssist Native Android Build Config
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── kotlin/com/yuri/finvault/
│       │   └── MainActivity.kt        # Complete FinVault Yuri Compose App
│       └── res/
│           ├── drawable/
│           │   ├── ic_launcher_background.xml
│           │   └── ic_launcher_foreground.xml
│           ├── mipmap-anydpi-v26/
│           │   ├── ic_launcher.xml
│           │   └── ic_launcher_round.xml
│           ├── mipmap-*/              # Density-Specific Launcher Icons (mdpi–xxxhdpi)
│           └── values/
│               ├── colors.xml
│               ├── strings.xml
│               └── themes.xml
├── BUILDING.md                        # Step-by-Step Source Build Guide (CodeAssist & Android Studio)
├── CHANGELOG.md                       # Separated Major, Minor & Patch Release History
├── LICENSE                            # MIT Open-Source License
└── README.md
```

## Installing & Upgrading

1. Download the latest signed APK from [GitHub Releases](https://github.com/YuriRinkashime/FinVault/releases/latest) and tap it to install (or update directly over an existing signed release).
2. **Upgrading from an older pre-release or debug build (`v2.2.0` or earlier)?**
   - Try installing the new APK directly first.
   - If Android displays ***"App not installed as package conflicts with an existing package"*** (caused by a different signing key between earlier debug/pre-release APKs and the signed production release):
     1. Open your existing FinVault app → **Settings → Offline Backup & Export → Copy Backup** (save the JSON text in a Notes app).
     2. Uninstall the old build.
     3. Install the new **`FinVault Yuri`** APK and complete the Setup Wizard.
     4. Open **Settings → Offline Backup & Export → Restore JSON → Paste → Restore Data** (legacy `v2.1.0`/`v2.2.0` backups are fully supported).

## Building from Source

Want to inspect, customize, or compile **FinVault Yuri** yourself? You do not need any special AI tools or proprietary setup—just the source code and **CodeAssist** (Mobile or PC) or **Android Studio**.

- **Quick Install (Pre-Built APK):** Download the latest signed APK directly from [GitHub Releases](https://github.com/YuriRinkashime/FinVault/releases/latest).
- **CodeAssist (Android Phone & Windows Desktop):** Uses `app/module.toml` directly (`com.yuri.finvault`, `minSdk 26`, `targetSdk 36`) with all source code in `app/src/main/kotlin/com/yuri/finvault/MainActivity.kt`.
- **Android Studio (Gradle):** Drop `MainActivity.kt`, `AndroidManifest.xml`, and `res/` into a standard Jetpack Compose project.

See the complete step-by-step instructions, `.platform/` setup tips, ADB install commands, and troubleshooting in **[BUILDING.md](BUILDING.md)**.

## Author & Credits

- **Creator & Lead Developer:** Yuri Rinkashime
- **Repository:** [github.com/YuriRinkashime/FinVault](https://github.com/YuriRinkashime/FinVault)
- **Bug Reports & Feature Requests:** [Open a GitHub Issue](https://github.com/YuriRinkashime/FinVault/issues/new)

## License

**FinVault Yuri** is released under the **MIT License**. See the [LICENSE](LICENSE) file for complete terms.
