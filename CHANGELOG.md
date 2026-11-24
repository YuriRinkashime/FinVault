# Changelog

All notable changes to **FinVault Yuri** follow Semantic Versioning (`MAJOR.MINOR.PATCH`), separating **Major Releases** (`X.0.0`), **Minor Upgrades** (`X.Y.0`), and **Patch Updates** (`X.Y.Z`).

---

## [v2.3.1] — Patch Release (Build 6) — 2026-11-24
### Added
- **Goal Reached Celebration Animation:** Reaching 100% on any Savings Goal (via Goal Deposit, Expense-to-Goal Transfer, Purchase Simulator Save Instead, Quarantine Resist & Save, or Goal Editor) now triggers a native Jetpack Compose confetti burst, pulsing titanium halo, and a dismissible **Milestone Achieved** card that respects the global **Smooth Animations** toggle.
- **Monthly Cap Warning Animation:** Logging an expense that crosses **80%–99%** (**Approaching Monthly Cap**) or **100%+** (**Monthly Cap Exceeded**) now triggers an animated amber or crimson floating alert banner with shake feedback, paired with a subtle pulsing border, status dot, and progress bar highlight on the Vault ledger card.

### Updated
- **Smart Tracking Origin for Heatmap & Streak:** The **Monthly Discipline Heatmap** and **No-Spend Streak** now anchor to the first day you record any financial value (balance sync, monthly cap, transaction, or goal). Earlier days in the month render cleanly as **Untracked** and are excluded from zero-spend counts and streaks, with `trackingStartMillis` preserved across local storage and JSON backups.
- **Streamlined In-App Release Notes & GitHub Changelog Link:** Replaced the long multi-version history inside the **About & License** dialog with a focused **Latest Release Notes** card showing only the current update's **What's New** and **Updated** highlights, paired with a clickable **View Full Changelog on GitHub** button pointing directly to `CHANGELOG.md`.

---

## [v2.3.0] — Minor Release (Build 5, Stable Production) — 2026-11-23
### Added
- **FinVault Yuri Rebrand:** Official rebrand to **FinVault Yuri** across the app, launcher icon label, exports, diagnostics, and documentation while preserving `com.yuri.finvault` package compatibility and existing local data.
- **First-Time Setup Wizard:** 4-step onboarding flow for Currency, Matte Titanium Theme, Starting Liquid Balance, Monthly Cap, Optional Hourly Rate, Smooth Animations, Haptics, Privacy Masking, and Update Checking. Automatically runs once for `v2.2.0` pre-release upgraders while preserving 100% of existing local records.
- **Dual-Vault Architecture & Balance Sync:** Separated the spendable **Liquid Vault** from the dedicated **Goal Reserve Vault** (with combined **Total Net Worth** display) and added one-tap **Set Balance** synchronization without distorting ledger history.
- **Expense-to-Goal Deposits & Goal Withdrawals:** Select a target Savings Goal directly while recording an Expense to transfer funds into its reserve vault, toggle **Transfer from Liquid Vault** during Goal Deposits, and withdraw partial or full reserves via **Withdraw from Goal** (with an option to return funds to your Liquid Vault).
- **GitHub Release Update Scanner & Secret Developer Settings:** Scans `YuriRinkashime/FinVault` GitHub Releases (privacy-safe, zero personal data transmitted) with direct APK download support and excludes pre-releases by default. Tapping the Version header/row 7 times unlocks **Developer Settings** with an **Include Pre-Release Updates** channel toggle and **Replay Setup**.
- **Exclusive Behavioral Finance Features:**
  - **Impulse Cooling-Off Quarantine Vault:** Lock planned purchases from the Simulator for 24h, 72h, or 7 days before deciding—then **Resist & Save** into a Goal to grow your **Impulse Resisted** counter.
  - **Time-Cost ("Work Hours") Converter:** Configure an optional Hourly Work Rate to view purchase costs and monthly spending in hours of work.
  - **Monthly Discipline Heatmap:** Interactive current-month calendar matrix highlighting zero-spend days, on-pace days, and peak spending days on the Insights tab.
- **Quality of Life & Motion Control:** Added a global **Smooth Animations** toggle (with instant zero-motion snaps when disabled), Quick Amount chips (`+50`/`+100`/`+250`/`+500`) in the Transaction Editor, and a live Period Summary strip (`In / Spend / Net`) beneath the Month Navigator.

---

## [v2.2.1] — Patch Release (Stability Hardening) — 2026-11-23
### Fixed
- **Crash Prevention (`LazyColumn` Keys & Storage Resilience):** Replaced raw `System.currentTimeMillis()` IDs with a monotonic unique ID generator (`nextUniqueId`), guarded rapid double-taps on **Undo**, and made JSON parsing fault-tolerant per record with ID deduplication.
- **Strict JSON Backup Validation & Paste Tool:** Required verified `"app"` schema (`FinVault` / `FinVault Yuri`) before restoring (preventing accidental data wipes), added a one-tap **Paste** button, scrollable JSON input, live backup preview, and backwards-compatible restore of `v2.1.0`/`v2.2.0` backups.
- **Exact Precision, Financial Math & Charts:** Migrated `monthlyBudget` storage from 32-bit `Float` to exact `Double`, sanitized all decimal inputs, excluded Goal Vault transfers from Monthly Cap & No-Spend Streak penalties, fixed DST date drift, fixed `-₱0.00` formatting, updated Daily Burn & Cash Runway to use elapsed calendar days, and fixed Category Allocation donut rendering.
- **Dark Cold Start, Keyboard Safety & Delete Guards:** Updated `themes.xml` to AMOLED dark (`#07080B`) to eliminate the white launch flash, added `adjustResize` + `imePadding()` across dialogs, added confirmation dialogs before deleting Goals or Quick Presets, fixed Privacy Masking in Settings/Deposits, smoothed Undo banner dismissal, and eliminated per-frame `SharedPreferences` reads and list copies.

---

## [v2.2.0] — Minor Release (Build 4) — 2026-11-23
### Added
- **Custom FinVault Icon & Brand Emblem:** Hexagonal titanium shield with geometric FV monogram (Adaptive Icon XMLs + PNG densities `mdpi` through `xxxhdpi` + 512px GitHub asset).
- **Support, Bug Reports & Privacy-Safe Diagnostics Hub:** Contact developer via Email, pre-filled GitHub Issues, Android Share Sheet, or Clipboard, backed by a local ring-buffer logger and automatic uncaught crash trace recorder (excludes all financial values and private notes).
- **Month Scope Navigator:** Browse transactions by individual month (`< Month YYYY >`) or toggle **All Time History**.
- **Undo Delete Banner:** 5-second floating banner to restore accidentally deleted transactions in one tap.
- **Transaction Date Selector & Duplicate Action:** Choose **Today**, **Yesterday**, or **2 Days Ago** when recording entries, and duplicate existing entries for today.
- **Editable Savings Goals:** Modify existing goal names, targets, and accent colors directly without recreating them.
- **Habit Insights Cards:** Added **No-Spend Streak** counter and **Largest Expense** highlight on the Insights tab.

### Fixed
- Resolved text truncation on detail rows inside the **Credits & License** and **Purchase Impact Simulator** dialogs.
- Refined header alignment and scrolling clearances across all four tabs.

---

## [v2.1.0] — Minor Upgrade (Build 3) — 2026-11-23
### Added
- Clean blank first boot with zero pre-filled sample data.
- **Purchase Impact Simulator** with **Skip Purchase & Save to Goal** transfer.
- **Safe to Spend Today** daily allowance and **Cash Runway** indicator on the Vault card.
- One-tap **Quick Presets**, live transaction search, **Vault Health Score (0–100)**, and offline **JSON/CSV** export & restore.

### Fixed
- Replaced cramped horizontal quick-action pills with a four-column stacked icon action grid to eliminate text clipping.
- Updated the bottom navigation bar to a Material 3 active pill indicator with clean hairline separation.

---

## [v2.0.0] — Major Release (Build 2) — 2026-11-23
### Added
- Original **Matte Titanium** visual identity and curated four-theme palette system.
- Virtualized `LazyColumn` screens with `@Immutable` models for smooth 120Hz scrolling.
- **Privacy Masking**, **Seven-Day Spend Trend** curve, **Milestone Savings Goals**, and **Credits & Open Source** dialog.

---

## [v1.0.0] — Major Release (Build 1) — 2026-11-23
### Added
- Initial offline budget ledger, expense/income categorization, savings tracker, and local JSON persistence.
