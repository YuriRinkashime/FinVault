# Changelog

All notable changes to **FinVault** follow Semantic Versioning (`MAJOR.MINOR.PATCH`), separating **Major Releases** (`X.0.0`), **Minor Upgrades** (`X.Y.0`), and **Patch Updates** (`X.Y.Z`).

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
