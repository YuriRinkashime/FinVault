# Signing & Forking Guide

Official pre-built **FinVault Yuri** (`com.yuri.finvault`) APKs published on [GitHub Releases](https://github.com/YuriRinkashime/FinVault/releases) starting from **`v2.3.2`** are signed using **Yuri Rinkashime's** private release keystore.

For security, the `.jks` keystore file and signing passwords are **never** committed to this repository. If you clone or fork **FinVault Yuri** to compile your own build, follow the steps below to switch signing to your own keystore or the default debug key.

---

## Official Release Certificate Verification

You can verify that a downloaded APK is an authentic official release before installing it by checking its SHA-256 certificate fingerprint:

| Field | Official Release Value |
| :--- | :--- |
| **Signer DN** | `CN=Yuri Rinkashime, O=finvault-yuri-release` |
| **Signing Certificate SHA-256** | `D6:B5:C4:A5:44:F4:CB:6D:D1:AA:5F:F0:B5:8B:03:A5:EF:FE:87:70:F5:B4:32:F8:69:6F:59:6F:6B:12:7F:70` |

Verify any downloaded APK using Google's `apksigner` (Termux or PC Android SDK Build-Tools):

```bash
apksigner verify --print-certs FinVault-Yuri-v2.3.2.apk
```

---

## Building a Fork or Custom Version

### Option 1: Use Default Debug Keys (Fastest for Personal Testing)

If you just want to compile and test changes quickly on your own device without creating a custom keystore:

1. **In CodeAssist (Mobile or Desktop):**
   - Open your **`app`** module settings and switch to the **Signing** tab.
   - Under both **`debug`** and **`release`**, select **`debug (default)`**.
2. **Check `app/module.toml`:**
   - Open `app/module.toml` and confirm the `release` entry inside `buildTypes` does **not** reference a missing `signingConfig` ID (such as `signingConfig = "picked-..."`). If a `signingConfig` field is present, remove just that field so the release entry looks like:
     ```toml
     { name = "release", debuggable = false, minifyEnabled = false, shrinkResources = false, proguardFiles = ["proguard-android-optimize.txt", "proguard-rules.pro"] }
     ```
3. Run **Build / Run Debug · app** or **Build / Run Release · app**. CodeAssist will sign the output automatically with your local debug key.

---

### Option 2: Sign With Your Own Release Keystore (Recommended for Forks)

If you plan to distribute your own fork or keep consistent in-place updates for your custom builds:

#### Step 1: Generate Your Own Keystore (`.jks`)
- **Inside CodeAssist:** Open **Settings → Keystore Manager → `+ Create`**, fill in your own alias, password, and identity details, and save it outside your Git folder.
- **Or via Command Line (`keytool`):**
  ```bash
  keytool -genkeypair -v -keystore my-finvault-release.jks -storetype JKS -keyalg RSA -keysize 2048 -validity 10000 -alias myalias
  ```

#### Step 2: Assign Your Keystore to Release Builds
- **In CodeAssist:** Open the **`app`** module → **Signing** tab → under **`release`**, select your newly created or imported keystore.
- **Or Sign Externally with `apksigner` (Keeps `app/module.toml` Clean):**
  ```bash
  apksigner sign \
    --ks path/to/my-finvault-release.jks \
    --ks-key-alias myalias \
    --v1-signing-enabled true \
    --v2-signing-enabled true \
    --v3-signing-enabled true \
    --out FinVault-Fork.apk \
    app/build/outputs/apk/release/app-release-unsigned.apk
  ```
- **In Android Studio (Gradle):** Configure a standard `signingConfigs` block in your local `app/build.gradle.kts` pointing to your `.jks` file and passwords stored in `local.properties` (never committed to Git).

> [!WARNING]
> **Never commit `.jks`, `.keystore`, or password files to GitHub.** If CodeAssist adds a local `signingConfig = "picked-..."` entry to `app/module.toml`, revert that line before pushing to a public repository so other contributors can build cleanly.

---

## Optional Fork Customization Tips

- **Install Side-by-Side With Official FinVault Yuri:** Because Android blocks installing two apps with the same package name (`com.yuri.finvault`) signed by different keys, either uninstall the official APK first (after copying a JSON backup) **or** change `namespace` in `app/module.toml`, `package` in `AndroidManifest.xml`, and `package` at the top of `MainActivity.kt` to your own unique package ID.
- **Point the In-App Updater to Your Fork:** Update `GITHUB_REPO_URL`, `GITHUB_CHANGELOG_URL`, `GITHUB_RELEASES_URL`, `GITHUB_RELEASES_API_URL`, and `GITHUB_ISSUES_URL` near the top of `app/src/main/kotlin/com/yuri/finvault/MainActivity.kt` so update checks scan your forked repository instead.
