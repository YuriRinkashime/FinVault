package com.yuri.finvault

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color as AndroidColor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.truncate

// ============================================================================
// FinVault Release & Developer Configuration
// Update these constants when bumping versions or customizing contact links.
// ============================================================================
private const val APP_VERSION_MAJOR = 2
private const val APP_VERSION_MINOR = 3
private const val APP_VERSION_PATCH = 2
private const val APP_VERSION_CODE = 7
private const val APP_VERSION_NAME = "$APP_VERSION_MAJOR.$APP_VERSION_MINOR.$APP_VERSION_PATCH"
private const val APP_BUILD_TRACK = "Patch Release"
private const val MAX_FINANCE_AMOUNT = 999_999_999_999.99

private const val APP_BRAND_NAME = "FinVault Yuri"
private const val DEVELOPER_NAME = "Yuri Rinkashime"
private const val GITHUB_REPO_URL = "https://github.com/YuriRinkashime/FinVault"
private const val GITHUB_CHANGELOG_URL = "https://github.com/YuriRinkashime/FinVault/blob/main/CHANGELOG.md"
private const val GITHUB_RELEASES_URL = "https://github.com/YuriRinkashime/FinVault/releases"
private const val GITHUB_RELEASES_API_URL = "https://api.github.com/repos/YuriRinkashime/FinVault/releases?per_page=15"
private const val GITHUB_ISSUES_URL = "https://github.com/YuriRinkashime/FinVault/issues/new"
private const val SUPPORT_EMAIL = "yuri.rinkashime@gmail.com"

class MainActivity : ComponentActivity() {
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VaultDiagnostics.installCrashHandler(applicationContext)
        VaultDiagnostics.log(applicationContext, "INFO", "Session started on v$APP_VERSION_NAME ($APP_VERSION_CODE)")

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = AndroidColor.TRANSPARENT
        window.navigationBarColor = AndroidColor.TRANSPARENT
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false

        setContent {
            FinVaultApp()
        }
    }
}

private enum class TransactionType {
    EXPENSE,
    INCOME
}

private enum class Category(
    val title: String,
    val color: Color,
    val isIncomeCategory: Boolean = false
) {
    FOOD("Food & Dining", Color(0xFFF59E0B)),
    TRANSPORT("Transport", Color(0xFF38BDF8)),
    BILLS("Bills & Data", Color(0xFFF43F5E)),
    SHOPPING("Shopping", Color(0xFFC084FC)),
    TECH("Tech & Gaming", Color(0xFF22D3EE)),
    EDUCATION("School & Work", Color(0xFF34D399)),
    GOAL_TRANSFER("Goal Vault", Color(0xFF38BDF8)),
    OTHER("General", Color(0xFF94A3B8)),
    ALLOWANCE("Allowance", Color(0xFF10B981), isIncomeCategory = true),
    SALARY("Earnings", Color(0xFF14B8A6), isIncomeCategory = true),
    GIFT("Bonus & Gift", Color(0xFF4ADE80), isIncomeCategory = true),
    GOAL_WITHDRAW("Goal Refund", Color(0xFF2DD4BF), isIncomeCategory = true)
}

private enum class AccentTheme(
    val title: String,
    val primary: Color,
    val secondary: Color,
    val cardTop: Color,
    val cardMid: Color,
    val cardBottom: Color
) {
    TITANIUM_ICE(
        title = "Titanium Ice",
        primary = Color(0xFF38BDF8),
        secondary = Color(0xFF818CF8),
        cardTop = Color(0xFF162032),
        cardMid = Color(0xFF101521),
        cardBottom = Color(0xFF0C1018)
    ),
    JADE_SLATE(
        title = "Jade Slate",
        primary = Color(0xFF10B981),
        secondary = Color(0xFF06B6D4),
        cardTop = Color(0xFF102822),
        cardMid = Color(0xFF0D1918),
        cardBottom = Color(0xFF0A1113)
    ),
    COBALT_PULSE(
        title = "Cobalt Pulse",
        primary = Color(0xFF6366F1),
        secondary = Color(0xFFA855F7),
        cardTop = Color(0xFF1C1936),
        cardMid = Color(0xFF131224),
        cardBottom = Color(0xFF0D0D18)
    ),
    ROSE_QUARTZ(
        title = "Rose Quartz",
        primary = Color(0xFFFB7185),
        secondary = Color(0xFFF59E0B),
        cardTop = Color(0xFF2E161E),
        cardMid = Color(0xFF1C1116),
        cardBottom = Color(0xFF120C10)
    )
}

private enum class CurrencyOption(val code: String, val symbol: String, val label: String) {
    PHP("PHP", "₱", "Philippine Peso (₱)"),
    USD("USD", "$", "US Dollar ($)"),
    EUR("EUR", "€", "Euro (€)"),
    GBP("GBP", "£", "British Pound (£)"),
    JPY("JPY", "¥", "Japanese Yen (¥)")
}

private enum class SupportReportType(val title: String) {
    BUG("Bug Report"),
    FEATURE("Feature Request"),
    FEEDBACK("General Feedback")
}

@Immutable
private data class TransactionItem(
    val id: Long,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: Category,
    val timestamp: Long,
    val note: String = ""
)

@Immutable
private data class SavingsGoal(
    val id: Long,
    val title: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val colorHex: Long
)

@Immutable
private data class QuickPreset(
    val id: Long,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: Category
)

@Immutable
private data class QuarantineItem(
    val id: Long,
    val title: String,
    val amount: Double,
    val category: Category,
    val targetGoalId: Long?,
    val createdAt: Long,
    val unlockAt: Long
)

@Immutable
private data class AppUpdateInfo(
    val tagName: String,
    val releaseName: String,
    val notes: String,
    val htmlUrl: String,
    val apkDownloadUrl: String?,
    val isPreRelease: Boolean,
    val publishedAt: String
)

private sealed class UpdateCheckResult {
    data class UpdateAvailable(val info: AppUpdateInfo) : UpdateCheckResult()
    data class UpToDate(val latestCheckedTag: String?) : UpdateCheckResult()
    data class Error(val message: String) : UpdateCheckResult()
}

@Immutable
private data class ChangelogSection(
    val label: String,
    val accentHex: Long,
    val items: List<String>
)

@Immutable
private data class GoalCelebrationEvent(
    val id: Long,
    val goalTitle: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val accentColorHex: Long
)

@Immutable
private data class CapWarningEvent(
    val id: Long,
    val isExceeded: Boolean,
    val ratio: Double,
    val monthExpense: Double,
    val monthlyBudget: Double
)

private val LatestReleaseHighlights = listOf(
    ChangelogSection(
        label = "What's New",
        accentHex = 0xFF10B981L,
        items = listOf(
            "Rich GitHub Markdown Updater: In-app Update Available release notes now render headings, bold/italic text, inline code, bullet lists, callouts, dividers, and links natively without raw Markdown symbols."
        )
    ),
    ChangelogSection(
        label = "Fixed",
        accentHex = 0xFF38BDF8L,
        items = listOf(
            "Full Scrollable Release Notes: Removed the 1,200-character truncation cutoff in the updater dialog and pinned the Download APK and action buttons so they are never clipped on long changelogs.",
            "Unified Release Signing Baseline: Standardized permanent release keystore signing across mobile and PC builds for seamless future in-place upgrades."
        )
    )
)

// ============================================================================
// Privacy-Safe Diagnostic & Crash Logger
// Never stores financial amounts, transaction titles, or personal notes.
// ============================================================================
private object VaultDiagnostics {
    private const val PREFS_DIAG = "finvault_diagnostics_v1"
    private const val KEY_LOGS = "event_logs"
    private const val KEY_LAST_CRASH = "last_crash_trace"
    private const val MAX_LOG_ENTRIES = 60
    private var crashHandlerInstalled = false

    fun installCrashHandler(context: Context) {
        if (crashHandlerInstalled) return
        crashHandlerInstalled = true
        val appContext = context.applicationContext
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val sw = StringWriter()
                throwable.printStackTrace(PrintWriter(sw))
                val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
                val crashText = "[$timestamp] Thread=${thread.name}\n${sw.toString().take(3500)}"
                appContext.getSharedPreferences(PREFS_DIAG, Context.MODE_PRIVATE)
                    .edit()
                    .putString(KEY_LAST_CRASH, crashText)
                    .commit()
                log(appContext, "CRASH", "${throwable.javaClass.simpleName}: ${throwable.message ?: "Unhandled Error"}")
            } catch (_: Exception) {
                // Avoid secondary crash inside handler
            } finally {
                previous?.uncaughtException(thread, throwable)
            }
        }
    }

    @Synchronized
    fun log(context: Context, level: String, message: String) {
        try {
            val prefs = context.applicationContext.getSharedPreferences(PREFS_DIAG, Context.MODE_PRIVATE)
            val existing = JSONArray(prefs.getString(KEY_LOGS, "[]") ?: "[]")
            val time = SimpleDateFormat("MM-dd HH:mm:ss", Locale.US).format(Date())
            val line = "[$time][$level] $message"
            val updated = JSONArray()
            val start = max(0, existing.length() - (MAX_LOG_ENTRIES - 1))
            for (i in start until existing.length()) {
                updated.put(existing.optString(i))
            }
            updated.put(line)
            prefs.edit().putString(KEY_LOGS, updated.toString()).apply()
        } catch (_: Exception) {
            // Safe ignore
        }
    }

    @Synchronized
    fun readLogs(context: Context): List<String> {
        val prefs = context.applicationContext.getSharedPreferences(PREFS_DIAG, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_LOGS, "[]") ?: "[]"
        val list = mutableListOf<String>()
        try {
            val arr = JSONArray(raw)
            for (i in 0 until arr.length()) {
                list.add(arr.optString(i))
            }
        } catch (_: Exception) {
            return emptyList()
        }
        return list.reversed()
    }

    @Synchronized
    fun readLastCrash(context: Context): String? {
        val prefs = context.applicationContext.getSharedPreferences(PREFS_DIAG, Context.MODE_PRIVATE)
        val trace = prefs.getString(KEY_LAST_CRASH, null)
        return if (trace.isNullOrBlank()) null else trace
    }

    @Synchronized
    fun clearAll(context: Context) {
        context.applicationContext.getSharedPreferences(PREFS_DIAG, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_LOGS)
            .remove(KEY_LAST_CRASH)
            .apply()
    }

    fun buildDiagnosticBundle(
        context: Context,
        theme: AccentTheme,
        currency: CurrencyOption,
        txCount: Int,
        goalCount: Int,
        presetCount: Int,
        hasMonthlyCap: Boolean
    ): String {
        val sb = StringBuilder()
        sb.appendLine("=== $APP_BRAND_NAME Diagnostic Summary (Privacy-Safe) ===")
        sb.appendLine("App Version: v$APP_VERSION_NAME (Build $APP_VERSION_CODE)")
        sb.appendLine("Android Version: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
        sb.appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
        sb.appendLine("Active Theme: ${theme.title}")
        sb.appendLine("Currency: ${currency.code}")
        sb.appendLine("Records: $txCount Transactions, $goalCount Goals, $presetCount Presets")
        sb.appendLine("Monthly Cap Configured: ${if (hasMonthlyCap) "Yes" else "No"}")
        val crash = readLastCrash(context)
        if (crash != null) {
            sb.appendLine()
            sb.appendLine("--- Last Captured Crash Trace ---")
            sb.appendLine(crash)
        }
        val logs = readLogs(context).take(25)
        if (logs.isNotEmpty()) {
            sb.appendLine()
            sb.appendLine("--- Recent Diagnostic Events (Newest First) ---")
            logs.forEach { sb.appendLine(it) }
        }
        return sb.toString()
    }
}

// ============================================================================
// GitHub Release Update Scanner (Public API — Zero Personal Data Transmitted)
// Ignores pre-releases unless enabled in Developer Settings.
// ============================================================================
private object VaultUpdater {
    fun compareSemVer(remoteTag: String, localVersion: String): Int {
        fun parseParts(raw: String): Pair<List<Int>, String> {
            val cleaned = raw.trim().removePrefix("v").removePrefix("V")
            val dashIndex = cleaned.indexOf('-')
            val core = if (dashIndex >= 0) cleaned.substring(0, dashIndex) else cleaned
            val suffix = if (dashIndex >= 0) cleaned.substring(dashIndex + 1).lowercase(Locale.US) else ""
            val nums = core.split('.').map { it.filter(Char::isDigit).toIntOrNull() ?: 0 }
            val padded = listOf(
                nums.getOrElse(0) { 0 },
                nums.getOrElse(1) { 0 },
                nums.getOrElse(2) { 0 }
            )
            return Pair(padded, suffix)
        }

        val (rNums, rSuffix) = parseParts(remoteTag)
        val (lNums, lSuffix) = parseParts(localVersion)
        for (i in 0..2) {
            if (rNums[i] != lNums[i]) return rNums[i].compareTo(lNums[i])
        }
        return when {
            rSuffix.isEmpty() && lSuffix.isNotEmpty() -> 1
            rSuffix.isNotEmpty() && lSuffix.isEmpty() -> -1
            else -> rSuffix.compareTo(lSuffix)
        }
    }

    suspend fun checkForUpdates(includePreReleases: Boolean): UpdateCheckResult = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(GITHUB_RELEASES_API_URL)
            connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 8000
                readTimeout = 8000
                setRequestProperty("Accept", "application/vnd.github+json")
                setRequestProperty("User-Agent", "FinVault-Android/$APP_VERSION_NAME")
            }
            val statusCode = connection.responseCode
            if (statusCode == 403) {
                return@withContext UpdateCheckResult.Error("GitHub API rate limit reached. Try again later.")
            }
            if (statusCode !in 200..299) {
                return@withContext UpdateCheckResult.Error("Unable to reach GitHub Releases (HTTP $statusCode).")
            }

            val body = BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                reader.readText()
            }
            val array = JSONArray(body)
            val candidates = mutableListOf<AppUpdateInfo>()

            for (i in 0 until array.length()) {
                val obj = array.optJSONObject(i) ?: continue
                if (obj.optBoolean("draft", false)) continue
                val tag = obj.optString("tag_name", "").trim()
                if (tag.isEmpty()) continue
                val isPre = obj.optBoolean("prerelease", false) || tag.contains('-')
                if (isPre && !includePreReleases) continue

                var apkUrl: String? = null
                val assets = obj.optJSONArray("assets")
                if (assets != null) {
                    for (a in 0 until assets.length()) {
                        val asset = assets.optJSONObject(a) ?: continue
                        val name = asset.optString("name", "")
                        val dl = asset.optString("browser_download_url", "")
                        if (name.endsWith(".apk", ignoreCase = true) && dl.isNotBlank()) {
                            apkUrl = dl
                            break
                        }
                    }
                }

                val name = obj.optString("name", "").trim().ifEmpty { tag }
                val notes = obj.optString("body", "").trim()
                val htmlUrl = obj.optString("html_url", GITHUB_RELEASES_URL).ifBlank { GITHUB_RELEASES_URL }
                val published = obj.optString("published_at", "").take(10)

                candidates.add(
                    AppUpdateInfo(
                        tagName = tag,
                        releaseName = name,
                        notes = notes,
                        htmlUrl = htmlUrl,
                        apkDownloadUrl = apkUrl,
                        isPreRelease = isPre,
                        publishedAt = published
                    )
                )
            }

            val latest = candidates.maxWithOrNull { a, b -> compareSemVer(a.tagName, b.tagName) }
            if (latest != null && compareSemVer(latest.tagName, APP_VERSION_NAME) > 0) {
                UpdateCheckResult.UpdateAvailable(latest)
            } else {
                UpdateCheckResult.UpToDate(latest?.tagName)
            }
        } catch (_: Exception) {
            UpdateCheckResult.Error("Offline or unable to reach GitHub. Connect to the internet to check for updates.")
        } finally {
            connection?.disconnect()
        }
    }
}

private object VaultStorage {
    private const val PREFS_NAME = "finvault_storage_v2"
    private const val KEY_TRANSACTIONS = "transactions"
    private const val KEY_GOALS = "goals"
    private const val KEY_PRESETS = "quick_presets"
    private const val KEY_QUARANTINE = "quarantine_items"
    private const val KEY_MONTHLY_BUDGET = "monthly_budget"
    private const val KEY_MONTHLY_BUDGET_EXACT = "monthly_budget_exact"
    private const val KEY_OPENING_BALANCE = "opening_balance_exact"
    private const val KEY_HOURLY_RATE = "hourly_rate_exact"
    private const val KEY_IMPULSE_RESISTED_TOTAL = "impulse_resisted_total"
    private const val KEY_IMPULSE_RESISTED_COUNT = "impulse_resisted_count"
    private const val KEY_CURRENCY = "currency"
    private const val KEY_THEME = "accent_theme"
    private const val KEY_HAPTICS = "haptics_enabled"
    private const val KEY_HIDE_BALANCE = "hide_balance"
    private const val KEY_ANIMATIONS = "animations_enabled"
    private const val KEY_SETUP_DONE_V23 = "setup_completed_v23"
    private const val KEY_DEV_SETTINGS = "dev_settings_unlocked"
    private const val KEY_INCLUDE_PRERELEASES = "include_prereleases"
    private const val KEY_AUTO_CHECK_UPDATES = "auto_check_updates"
    private const val KEY_TRACKING_START_MS = "tracking_start_ms"

    fun loadMonthlyBudget(context: Context): Double {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val exact = prefs.getString(KEY_MONTHLY_BUDGET_EXACT, null)?.toDoubleOrNull()
        if (exact != null && exact.isFinite() && exact >= 0.0) {
            return exact.coerceAtMost(MAX_FINANCE_AMOUNT)
        }
        val legacy = prefs.getFloat(KEY_MONTHLY_BUDGET, 0f).toDouble()
        return if (legacy.isFinite() && legacy >= 0.0) legacy.coerceAtMost(MAX_FINANCE_AMOUNT) else 0.0
    }

    fun saveMonthlyBudget(context: Context, budget: Double) {
        val safe = if (budget.isFinite() && budget >= 0.0) budget.coerceAtMost(MAX_FINANCE_AMOUNT) else 0.0
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_MONTHLY_BUDGET_EXACT, safe.toString())
            .putFloat(KEY_MONTHLY_BUDGET, safe.toFloat())
            .apply()
    }

    fun loadOpeningBalance(context: Context): Double {
        val raw = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_OPENING_BALANCE, null)?.toDoubleOrNull()
        return if (raw != null && raw.isFinite()) {
            raw.coerceIn(-MAX_FINANCE_AMOUNT, MAX_FINANCE_AMOUNT)
        } else {
            0.0
        }
    }

    fun saveOpeningBalance(context: Context, balance: Double) {
        val safe = if (balance.isFinite()) balance.coerceIn(-MAX_FINANCE_AMOUNT, MAX_FINANCE_AMOUNT) else 0.0
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_OPENING_BALANCE, safe.toString())
            .apply()
    }

    fun loadHourlyRate(context: Context): Double {
        val raw = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_HOURLY_RATE, null)?.toDoubleOrNull()
        return if (raw != null && raw.isFinite() && raw >= 0.0) {
            raw.coerceAtMost(MAX_FINANCE_AMOUNT)
        } else {
            0.0
        }
    }

    fun saveHourlyRate(context: Context, rate: Double) {
        val safe = if (rate.isFinite() && rate >= 0.0) rate.coerceAtMost(MAX_FINANCE_AMOUNT) else 0.0
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_HOURLY_RATE, safe.toString())
            .apply()
    }

    fun loadImpulseResistedTotal(context: Context): Double {
        val raw = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_IMPULSE_RESISTED_TOTAL, null)?.toDoubleOrNull()
        return if (raw != null && raw.isFinite() && raw >= 0.0) raw.coerceAtMost(MAX_FINANCE_AMOUNT) else 0.0
    }

    fun saveImpulseResistedTotal(context: Context, total: Double) {
        val safe = if (total.isFinite() && total >= 0.0) total.coerceAtMost(MAX_FINANCE_AMOUNT) else 0.0
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_IMPULSE_RESISTED_TOTAL, safe.toString())
            .apply()
    }

    fun loadImpulseResistedCount(context: Context): Int {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_IMPULSE_RESISTED_COUNT, 0).coerceAtLeast(0)
    }

    fun saveImpulseResistedCount(context: Context, count: Int) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_IMPULSE_RESISTED_COUNT, count.coerceAtLeast(0))
            .apply()
    }

    fun loadAnimationsEnabled(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_ANIMATIONS, true)
    }

    fun saveAnimationsEnabled(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_ANIMATIONS, enabled)
            .apply()
    }

    fun loadSetupCompleted(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_SETUP_DONE_V23, false)
    }

    fun saveSetupCompleted(context: Context, completed: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_SETUP_DONE_V23, completed)
            .apply()
    }

    fun loadDevSettingsUnlocked(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_DEV_SETTINGS, false)
    }

    fun saveDevSettingsUnlocked(context: Context, unlocked: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_DEV_SETTINGS, unlocked)
            .apply()
    }

    fun loadIncludePreReleases(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_INCLUDE_PRERELEASES, false)
    }

    fun saveIncludePreReleases(context: Context, include: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_INCLUDE_PRERELEASES, include)
            .apply()
    }

    fun loadAutoCheckUpdates(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_AUTO_CHECK_UPDATES, true)
    }

    fun saveAutoCheckUpdates(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_AUTO_CHECK_UPDATES, enabled)
            .apply()
    }

    fun loadTrackingStartMillis(context: Context): Long {
        val raw = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getLong(KEY_TRACKING_START_MS, 0L)
        return if (raw > 0L) raw else 0L
    }

    fun saveTrackingStartMillis(context: Context, timestamp: Long) {
        val safe = if (timestamp > 0L) timestamp else 0L
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putLong(KEY_TRACKING_START_MS, safe)
            .apply()
    }

    fun loadQuarantine(context: Context): List<QuarantineItem> {
        val raw = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_QUARANTINE, "[]") ?: "[]"
        return parseQuarantineJson(raw)
    }

    fun saveQuarantine(context: Context, items: List<QuarantineItem>) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_QUARANTINE, serializeQuarantineJson(items).toString())
            .apply()
    }

    fun loadCurrency(context: Context): CurrencyOption {
        val code = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_CURRENCY, CurrencyOption.PHP.code)
        return CurrencyOption.entries.firstOrNull { it.code == code } ?: CurrencyOption.PHP
    }

    fun saveCurrency(context: Context, currency: CurrencyOption) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CURRENCY, currency.code)
            .apply()
    }

    fun loadTheme(context: Context): AccentTheme {
        val name = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_THEME, AccentTheme.TITANIUM_ICE.name)
        return AccentTheme.entries.firstOrNull { it.name == name } ?: AccentTheme.TITANIUM_ICE
    }

    fun saveTheme(context: Context, theme: AccentTheme) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_THEME, theme.name)
            .apply()
    }

    fun loadHaptics(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_HAPTICS, true)
    }

    fun saveHaptics(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_HAPTICS, enabled)
            .apply()
    }

    fun loadHideBalance(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_HIDE_BALANCE, false)
    }

    fun saveHideBalance(context: Context, hidden: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_HIDE_BALANCE, hidden)
            .apply()
    }

    fun loadTransactions(context: Context): List<TransactionItem> {
        val raw = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_TRANSACTIONS, "[]") ?: "[]"
        return parseTransactionsJson(raw)
    }

    fun saveTransactions(context: Context, items: List<TransactionItem>) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_TRANSACTIONS, serializeTransactionsJson(items).toString())
            .apply()
    }

    fun loadGoals(context: Context): List<SavingsGoal> {
        val raw = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_GOALS, "[]") ?: "[]"
        return parseGoalsJson(raw)
    }

    fun saveGoals(context: Context, goals: List<SavingsGoal>) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_GOALS, serializeGoalsJson(goals).toString())
            .apply()
    }

    fun loadPresets(context: Context): List<QuickPreset> {
        val raw = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_PRESETS, "[]") ?: "[]"
        return parsePresetsJson(raw)
    }

    fun savePresets(context: Context, presets: List<QuickPreset>) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_PRESETS, serializePresetsJson(presets).toString())
            .apply()
    }

    fun exportFullBackupJson(
        monthlyBudget: Double,
        openingBalance: Double,
        hourlyRate: Double,
        impulseResistedTotal: Double,
        impulseResistedCount: Int,
        trackingStartMillis: Long,
        currency: CurrencyOption,
        transactions: List<TransactionItem>,
        goals: List<SavingsGoal>,
        presets: List<QuickPreset>,
        quarantine: List<QuarantineItem>
    ): String {
        val root = JSONObject()
        root.put("app", APP_BRAND_NAME)
        root.put("version", APP_VERSION_NAME)
        root.put("exportedAt", System.currentTimeMillis())
        root.put("monthlyBudget", monthlyBudget)
        root.put("openingBalance", openingBalance)
        root.put("hourlyRate", hourlyRate)
        root.put("impulseResistedTotal", impulseResistedTotal)
        root.put("impulseResistedCount", impulseResistedCount)
        root.put("trackingStartMillis", trackingStartMillis)
        root.put("currency", currency.code)
        root.put("transactions", serializeTransactionsJson(transactions))
        root.put("goals", serializeGoalsJson(goals))
        root.put("presets", serializePresetsJson(presets))
        root.put("quarantine", serializeQuarantineJson(quarantine))
        return root.toString(2)
    }

    fun parseFullBackupJson(rawJson: String): BackupPayload? {
        return try {
            val trimmed = rawJson.trim()
            if (trimmed.isEmpty()) return null
            val root = JSONObject(trimmed)
            val appField = root.optString("app")
            if (appField != "FinVault" && appField != APP_BRAND_NAME) return null
            val txArray = root.optJSONArray("transactions") ?: return null
            if (root.has("goals") && root.optJSONArray("goals") == null) return null
            if (root.has("presets") && root.optJSONArray("presets") == null) return null
            if (root.has("quarantine") && root.optJSONArray("quarantine") == null) return null

            val rawBudget = root.optDouble("monthlyBudget", 0.0)
            val budget = if (rawBudget.isFinite() && rawBudget >= 0.0) {
                rawBudget.coerceAtMost(MAX_FINANCE_AMOUNT)
            } else {
                0.0
            }
            val rawOpening = root.optDouble("openingBalance", 0.0)
            val openingBalance = if (rawOpening.isFinite()) {
                rawOpening.coerceIn(-MAX_FINANCE_AMOUNT, MAX_FINANCE_AMOUNT)
            } else {
                0.0
            }
            val rawHourly = root.optDouble("hourlyRate", 0.0)
            val hourlyRate = if (rawHourly.isFinite() && rawHourly >= 0.0) {
                rawHourly.coerceAtMost(MAX_FINANCE_AMOUNT)
            } else {
                0.0
            }
            val rawResistedTotal = root.optDouble("impulseResistedTotal", 0.0)
            val resistedTotal = if (rawResistedTotal.isFinite() && rawResistedTotal >= 0.0) {
                rawResistedTotal.coerceAtMost(MAX_FINANCE_AMOUNT)
            } else {
                0.0
            }
            val resistedCount = root.optInt("impulseResistedCount", 0).coerceAtLeast(0)
            val trackingStartMillis = root.optLong("trackingStartMillis", 0L).coerceAtLeast(0L)
            val currencyCode = root.optString("currency", CurrencyOption.PHP.code)
            val currency = CurrencyOption.entries.firstOrNull { it.code == currencyCode }
                ?: CurrencyOption.PHP
            val goalArray = root.optJSONArray("goals") ?: JSONArray()
            val presetArray = root.optJSONArray("presets") ?: JSONArray()
            val quarantineArray = root.optJSONArray("quarantine") ?: JSONArray()
            BackupPayload(
                monthlyBudget = budget,
                openingBalance = openingBalance,
                hourlyRate = hourlyRate,
                impulseResistedTotal = resistedTotal,
                impulseResistedCount = resistedCount,
                trackingStartMillis = trackingStartMillis,
                currency = currency,
                transactions = parseTransactionsJson(txArray.toString()),
                goals = parseGoalsJson(goalArray.toString()),
                presets = parsePresetsJson(presetArray.toString()),
                quarantine = parseQuarantineJson(quarantineArray.toString())
            )
        } catch (_: Exception) {
            null
        }
    }

    fun exportCsvStatement(transactions: List<TransactionItem>, currency: CurrencyOption): String {
        val sb = StringBuilder()
        sb.appendLine("Date,Title,Type,Category,Amount (${currency.code}),Note")
        val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        transactions.forEach { item ->
            val safeTitle = item.title.replace("\"", "\"\"")
            val safeNote = item.note.replace("\"", "\"\"")
            val signed = if (item.type == TransactionType.EXPENSE) -item.amount else item.amount
            sb.appendLine(
                "\"${fmt.format(Date(item.timestamp))}\",\"$safeTitle\",\"${item.type.name}\",\"${item.category.title}\",${String.format(Locale.US, "%.2f", signed)},\"$safeNote\""
            )
        }
        return sb.toString()
    }

    private fun serializeTransactionsJson(items: List<TransactionItem>): JSONArray {
        val array = JSONArray()
        items.forEach { item ->
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("title", item.title)
            obj.put("amount", item.amount)
            obj.put("type", item.type.name)
            obj.put("category", item.category.name)
            obj.put("timestamp", item.timestamp)
            obj.put("note", item.note)
            array.put(obj)
        }
        return array
    }

    private fun parseTransactionsJson(raw: String): List<TransactionItem> {
        val result = mutableListOf<TransactionItem>()
        val seenIds = HashSet<Long>()
        var nextFallbackId = System.currentTimeMillis()
        try {
            val array = JSONArray(raw)
            for (i in 0 until array.length()) {
                val obj = array.optJSONObject(i) ?: continue
                val type = TransactionType.entries.firstOrNull {
                    it.name == obj.optString("type")
                } ?: TransactionType.EXPENSE
                val rawCategory = Category.entries.firstOrNull {
                    it.name == obj.optString("category")
                }
                val category = if (rawCategory != null && rawCategory.isIncomeCategory == (type == TransactionType.INCOME)) {
                    rawCategory
                } else if (type == TransactionType.INCOME) {
                    Category.ALLOWANCE
                } else {
                    Category.OTHER
                }
                val rawAmount = obj.optDouble("amount", 0.0)
                if (!rawAmount.isFinite() || rawAmount <= 0.0) continue
                val safeAmount = rawAmount.coerceAtMost(MAX_FINANCE_AMOUNT)
                var id = obj.optLong("id", 0L)
                if (id <= 0L || !seenIds.add(id)) {
                    while (!seenIds.add(nextFallbackId)) {
                        nextFallbackId += 1L
                    }
                    id = nextFallbackId
                    nextFallbackId += 1L
                }
                val timestamp = obj.optLong("timestamp", System.currentTimeMillis()).let {
                    if (it > 0L) it else System.currentTimeMillis()
                }
                val title = obj.optString("title", "").trim().ifEmpty { "Untitled Entry" }
                result.add(
                    TransactionItem(
                        id = id,
                        title = title,
                        amount = safeAmount,
                        type = type,
                        category = category,
                        timestamp = timestamp,
                        note = obj.optString("note", "").trim()
                    )
                )
            }
        } catch (_: Exception) {
            return emptyList()
        }
        return result.sortedByDescending { it.timestamp }
    }

    private fun serializeGoalsJson(goals: List<SavingsGoal>): JSONArray {
        val array = JSONArray()
        goals.forEach { goal ->
            val obj = JSONObject()
            obj.put("id", goal.id)
            obj.put("title", goal.title)
            obj.put("targetAmount", goal.targetAmount)
            obj.put("savedAmount", goal.savedAmount)
            obj.put("colorHex", goal.colorHex)
            array.put(obj)
        }
        return array
    }

    private fun parseGoalsJson(raw: String): List<SavingsGoal> {
        val result = mutableListOf<SavingsGoal>()
        val seenIds = HashSet<Long>()
        var nextFallbackId = System.currentTimeMillis()
        try {
            val array = JSONArray(raw)
            for (i in 0 until array.length()) {
                val obj = array.optJSONObject(i) ?: continue
                val rawTarget = obj.optDouble("targetAmount", 0.0)
                if (!rawTarget.isFinite() || rawTarget <= 0.0) continue
                val rawSaved = obj.optDouble("savedAmount", 0.0)
                val safeSaved = if (rawSaved.isFinite() && rawSaved >= 0.0) {
                    rawSaved.coerceAtMost(MAX_FINANCE_AMOUNT)
                } else {
                    0.0
                }
                var id = obj.optLong("id", 0L)
                if (id <= 0L || !seenIds.add(id)) {
                    while (!seenIds.add(nextFallbackId)) {
                        nextFallbackId += 1L
                    }
                    id = nextFallbackId
                    nextFallbackId += 1L
                }
                val title = obj.optString("title", "").trim().ifEmpty { "Savings Target" }
                val colorHex = obj.optLong("colorHex", 0xFF38BDF8L)
                val safeColorHex = if ((colorHex and 0xFF000000L) == 0L) 0xFF38BDF8L else colorHex
                result.add(
                    SavingsGoal(
                        id = id,
                        title = title,
                        targetAmount = rawTarget.coerceAtMost(MAX_FINANCE_AMOUNT),
                        savedAmount = safeSaved,
                        colorHex = safeColorHex
                    )
                )
            }
        } catch (_: Exception) {
            return emptyList()
        }
        return result
    }

    private fun serializePresetsJson(presets: List<QuickPreset>): JSONArray {
        val array = JSONArray()
        presets.forEach { preset ->
            val obj = JSONObject()
            obj.put("id", preset.id)
            obj.put("title", preset.title)
            obj.put("amount", preset.amount)
            obj.put("type", preset.type.name)
            obj.put("category", preset.category.name)
            array.put(obj)
        }
        return array
    }

    private fun parsePresetsJson(raw: String): List<QuickPreset> {
        val result = mutableListOf<QuickPreset>()
        val seenIds = HashSet<Long>()
        var nextFallbackId = System.currentTimeMillis()
        try {
            val array = JSONArray(raw)
            for (i in 0 until array.length()) {
                val obj = array.optJSONObject(i) ?: continue
                val type = TransactionType.entries.firstOrNull {
                    it.name == obj.optString("type")
                } ?: TransactionType.EXPENSE
                val rawCategory = Category.entries.firstOrNull {
                    it.name == obj.optString("category")
                }
                val category = if (rawCategory != null && rawCategory.isIncomeCategory == (type == TransactionType.INCOME)) {
                    rawCategory
                } else if (type == TransactionType.INCOME) {
                    Category.ALLOWANCE
                } else {
                    Category.OTHER
                }
                val rawAmount = obj.optDouble("amount", 0.0)
                if (!rawAmount.isFinite() || rawAmount <= 0.0) continue
                var id = obj.optLong("id", 0L)
                if (id <= 0L || !seenIds.add(id)) {
                    while (!seenIds.add(nextFallbackId)) {
                        nextFallbackId += 1L
                    }
                    id = nextFallbackId
                    nextFallbackId += 1L
                }
                val title = obj.optString("title", "").trim().ifEmpty { "Quick Preset" }
                result.add(
                    QuickPreset(
                        id = id,
                        title = title,
                        amount = rawAmount.coerceAtMost(MAX_FINANCE_AMOUNT),
                        type = type,
                        category = category
                    )
                )
            }
        } catch (_: Exception) {
            return emptyList()
        }
        return result
    }

    private fun serializeQuarantineJson(items: List<QuarantineItem>): JSONArray {
        val array = JSONArray()
        items.forEach { item ->
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("title", item.title)
            obj.put("amount", item.amount)
            obj.put("category", item.category.name)
            if (item.targetGoalId != null) obj.put("targetGoalId", item.targetGoalId)
            obj.put("createdAt", item.createdAt)
            obj.put("unlockAt", item.unlockAt)
            array.put(obj)
        }
        return array
    }

    private fun parseQuarantineJson(raw: String): List<QuarantineItem> {
        val result = mutableListOf<QuarantineItem>()
        val seenIds = HashSet<Long>()
        var nextFallbackId = System.currentTimeMillis()
        try {
            val array = JSONArray(raw)
            for (i in 0 until array.length()) {
                val obj = array.optJSONObject(i) ?: continue
                val rawAmount = obj.optDouble("amount", 0.0)
                if (!rawAmount.isFinite() || rawAmount <= 0.0) continue
                val category = Category.entries.firstOrNull {
                    !it.isIncomeCategory && it.name == obj.optString("category")
                } ?: Category.SHOPPING
                var id = obj.optLong("id", 0L)
                if (id <= 0L || !seenIds.add(id)) {
                    while (!seenIds.add(nextFallbackId)) {
                        nextFallbackId += 1L
                    }
                    id = nextFallbackId
                    nextFallbackId += 1L
                }
                val title = obj.optString("title", "").trim().ifEmpty { "Planned Purchase" }
                val targetGoalId = if (obj.has("targetGoalId") && !obj.isNull("targetGoalId")) {
                    obj.optLong("targetGoalId", -1L).takeIf { it > 0L }
                } else {
                    null
                }
                val createdAt = obj.optLong("createdAt", System.currentTimeMillis()).let {
                    if (it > 0L) it else System.currentTimeMillis()
                }
                val unlockAt = obj.optLong("unlockAt", createdAt + 72L * 60L * 60L * 1000L)
                result.add(
                    QuarantineItem(
                        id = id,
                        title = title,
                        amount = rawAmount.coerceAtMost(MAX_FINANCE_AMOUNT),
                        category = category,
                        targetGoalId = targetGoalId,
                        createdAt = createdAt,
                        unlockAt = unlockAt
                    )
                )
            }
        } catch (_: Exception) {
            return emptyList()
        }
        return result.sortedBy { it.unlockAt }
    }
}

private data class BackupPayload(
    val monthlyBudget: Double,
    val openingBalance: Double,
    val hourlyRate: Double,
    val impulseResistedTotal: Double,
    val impulseResistedCount: Int,
    val trackingStartMillis: Long,
    val currency: CurrencyOption,
    val transactions: List<TransactionItem>,
    val goals: List<SavingsGoal>,
    val presets: List<QuickPreset>,
    val quarantine: List<QuarantineItem>
)

private fun formatWorkHours(amount: Double, hourlyRate: Double): String? {
    if (!amount.isFinite() || !hourlyRate.isFinite() || amount <= 0.0 || hourlyRate <= 0.0) return null
    val hours = amount / hourlyRate
    return when {
        hours < 1.0 -> "${max(1, (hours * 60.0).roundToInt())} Mins"
        hours < 100.0 -> "${String.format(Locale.US, "%.1f", hours)} Hrs"
        else -> "${hours.roundToInt()} Hrs"
    }
}

private fun formatRemainingCountdown(unlockAt: Long, nowMillis: Long): String {
    val diff = unlockAt - nowMillis
    if (diff <= 0L) return "Ready to Decide"
    val totalMinutes = diff / (60L * 1000L)
    val days = totalMinutes / (24L * 60L)
    val hours = (totalMinutes % (24L * 60L)) / 60L
    val mins = totalMinutes % 60L
    return when {
        days > 0L -> "${days}d ${hours}h Left"
        hours > 0L -> "${hours}h ${mins}m Left"
        mins > 0L -> "${mins}m Left"
        else -> "< 1m Left"
    }
}

private fun nextUniqueId(existingIds: Collection<Long>): Long {
    val now = System.currentTimeMillis()
    val maxExisting = existingIds.maxOrNull() ?: 0L
    return max(now, maxExisting + 1L)
}

private fun sanitizeDecimalInput(input: String): String {
    val sb = StringBuilder()
    var hasDot = false
    var integerDigits = 0
    var fractionalDigits = 0
    for (ch in input) {
        if (ch.isDigit()) {
            if (!hasDot) {
                if (integerDigits < 12) {
                    sb.append(ch)
                    integerDigits++
                }
            } else if (fractionalDigits < 2) {
                sb.append(ch)
                fractionalDigits++
            }
        } else if (ch == '.' && !hasDot) {
            if (sb.isEmpty()) {
                sb.append('0')
                integerDigits = 1
            }
            sb.append('.')
            hasDot = true
        }
    }
    return sb.toString()
}

private fun formatEditableAmount(amount: Double): String {
    if (!amount.isFinite() || amount <= 0.0) return ""
    val formatted = String.format(Locale.US, "%.2f", amount)
    return when {
        formatted.endsWith(".00") -> formatted.dropLast(3)
        formatted.endsWith("0") -> formatted.dropLast(1)
        else -> formatted
    }
}

private fun hasFractionalCents(amount: Double): Boolean {
    if (!amount.isFinite()) return false
    return abs(amount - truncate(amount)) >= 0.005
}

private fun formatCompactPercent(ratio: Double): String {
    if (!ratio.isFinite() || ratio <= 0.0) return "0%"
    val pct = ratio * 100.0
    return if (pct > 9999.0) "9999%+" else "${pct.roundToInt()}%"
}

private fun formatMoney(
    amount: Double,
    symbol: String,
    showDecimals: Boolean = true,
    masked: Boolean = false
): String {
    if (masked) return "$symbol••••••"
    val safe = if (amount.isFinite()) amount else 0.0
    val zeroThreshold = if (showDecimals) 0.005 else 0.5
    val normalized = if (abs(safe) < zeroThreshold) 0.0 else safe
    val formatted = if (showDecimals) {
        String.format(Locale.US, "%,.2f", abs(normalized))
    } else {
        String.format(Locale.US, "%,.0f", abs(normalized))
    }
    return if (normalized < 0.0) "-$symbol$formatted" else "$symbol$formatted"
}

private fun calendarDaysAgo(daysAgo: Int): Calendar {
    return Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -daysAgo)
    }
}

private fun startOfDayMillis(timestamp: Long): Long {
    val cal = Calendar.getInstance().apply {
        timeInMillis = timestamp
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return cal.timeInMillis
}

private fun calendarDaysSpanInclusive(earliestTimestamp: Long): Int {
    val startDay = startOfDayMillis(earliestTimestamp)
    val todayStart = startOfDayMillis(System.currentTimeMillis())
    if (startDay >= todayStart) return 1
    val diffDays = ((todayStart - startDay) + 12L * 60L * 60L * 1000L) / (24L * 60L * 60L * 1000L)
    return max(1, diffDays.toInt() + 1)
}

private fun computeAverageDailySpend(expenses: List<TransactionItem>): Double {
    if (expenses.isEmpty()) return 0.0
    val total = expenses.sumOf { it.amount }
    val earliest = expenses.minOf { it.timestamp }
    val spanDays = calendarDaysSpanInclusive(earliest)
    return total / spanDays
}

private fun computeRecentDailyBurn(expenses: List<TransactionItem>): Double {
    if (expenses.isEmpty()) return 0.0
    val cutoff = startOfDayMillis(calendarDaysAgo(29).timeInMillis)
    val recent = expenses.filter { it.timestamp >= cutoff }
    if (recent.isEmpty()) return computeAverageDailySpend(expenses)
    val earliest = recent.minOf { it.timestamp }
    val spanDays = min(30, calendarDaysSpanInclusive(earliest))
    return recent.sumOf { it.amount } / max(1, spanDays)
}

private fun formatDateLabel(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    return formatter.format(Date(timestamp))
}

private fun formatMonthYearLabel(monthOffset: Int): String {
    val cal = Calendar.getInstance()
    cal.set(Calendar.DAY_OF_MONTH, 1)
    cal.add(Calendar.MONTH, monthOffset)
    return SimpleDateFormat("MMMM yyyy", Locale.US).format(cal.time)
}

private fun isTimestampInMonthOffset(timestamp: Long, monthOffset: Int): Boolean {
    val ref = Calendar.getInstance()
    ref.set(Calendar.DAY_OF_MONTH, 1)
    ref.add(Calendar.MONTH, monthOffset)
    val target = Calendar.getInstance().apply { timeInMillis = timestamp }
    return ref.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
        ref.get(Calendar.MONTH) == target.get(Calendar.MONTH)
}

private fun daysRemainingInCurrentMonth(): Int {
    val cal = Calendar.getInstance()
    val maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    val today = cal.get(Calendar.DAY_OF_MONTH)
    return max(1, (maxDay - today) + 1)
}

private fun isTimestampInCurrentMonth(timestamp: Long): Boolean {
    return isTimestampInMonthOffset(timestamp, 0)
}

private fun resolveEffectiveTrackingStartMillis(
    storedStartMillis: Long,
    transactions: List<TransactionItem>,
    goals: List<SavingsGoal>,
    monthlyBudget: Double,
    openingBalance: Double
): Long? {
    val earliestTx = transactions.minOfOrNull { it.timestamp }
    val candidates = buildList {
        if (storedStartMillis > 0L) add(storedStartMillis)
        if (earliestTx != null && earliestTx > 0L) add(earliestTx)
    }
    if (candidates.isNotEmpty()) {
        return startOfDayMillis(candidates.min())
    }
    if (goals.isNotEmpty() || monthlyBudget > 0.0 || openingBalance != 0.0) {
        return startOfDayMillis(System.currentTimeMillis())
    }
    return null
}

private fun computeNoSpendStreakDays(
    transactions: List<TransactionItem>,
    trackingStartDayMillis: Long?
): Int {
    if (trackingStartDayMillis == null) return 0
    val expenses = transactions.filter {
        it.type == TransactionType.EXPENSE && it.category != Category.GOAL_TRANSFER
    }
    val keyFmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val expenseDays = expenses.map { keyFmt.format(Date(it.timestamp)) }.toSet()
    val cal = Calendar.getInstance()
    var streak = 0
    for (offset in 0..365) {
        val dayStart = startOfDayMillis(cal.timeInMillis)
        if (dayStart < trackingStartDayMillis) break
        val key = keyFmt.format(cal.time)
        if (key in expenseDays) break
        streak++
        cal.add(Calendar.DAY_OF_YEAR, -1)
    }
    return streak
}

private fun openExternalUrl(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (_: Exception) {
        Toast.makeText(context, "Unable to Open Browser Link", Toast.LENGTH_SHORT).show()
    }
}

private fun launchEmailReport(context: Context, subject: String, body: String) {
    try {
        val encodedSubject = Uri.encode(subject)
        val encodedBody = Uri.encode(body)
        val mailtoUri = Uri.parse("mailto:$SUPPORT_EMAIL?subject=$encodedSubject&body=$encodedBody")
        val intent = Intent(Intent.ACTION_SENDTO, mailtoUri)
        context.startActivity(Intent.createChooser(intent, "Send Report via Email"))
    } catch (_: Exception) {
        Toast.makeText(context, "No Email Application Found on Device", Toast.LENGTH_SHORT).show()
    }
}

private fun sharePlainTextReport(context: Context, subject: String, body: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        context.startActivity(Intent.createChooser(intent, "Share Report & Diagnostic Logs"))
    } catch (_: Exception) {
        Toast.makeText(context, "Unable to Open Share Sheet", Toast.LENGTH_SHORT).show()
    }
}

@Composable
private fun FinVaultApp() {
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()

    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var activeTheme by remember { mutableStateOf(VaultStorage.loadTheme(context)) }
    var currency by remember { mutableStateOf(VaultStorage.loadCurrency(context)) }
    var monthlyBudget by remember { mutableDoubleStateOf(VaultStorage.loadMonthlyBudget(context)) }
    var openingBalance by remember { mutableDoubleStateOf(VaultStorage.loadOpeningBalance(context)) }
    var hourlyRate by remember { mutableDoubleStateOf(VaultStorage.loadHourlyRate(context)) }
    var impulseResistedTotal by remember { mutableDoubleStateOf(VaultStorage.loadImpulseResistedTotal(context)) }
    var impulseResistedCount by remember { mutableIntStateOf(VaultStorage.loadImpulseResistedCount(context)) }
    var hapticsEnabled by remember { mutableStateOf(VaultStorage.loadHaptics(context)) }
    var hideBalance by remember { mutableStateOf(VaultStorage.loadHideBalance(context)) }
    var animationsEnabled by remember { mutableStateOf(VaultStorage.loadAnimationsEnabled(context)) }
    var setupCompleted by remember { mutableStateOf(VaultStorage.loadSetupCompleted(context)) }
    var devSettingsUnlocked by remember { mutableStateOf(VaultStorage.loadDevSettingsUnlocked(context)) }
    var includePreReleases by remember { mutableStateOf(VaultStorage.loadIncludePreReleases(context)) }
    var autoCheckUpdates by remember { mutableStateOf(VaultStorage.loadAutoCheckUpdates(context)) }
    var trackingStartMillis by remember { mutableLongStateOf(VaultStorage.loadTrackingStartMillis(context)) }

    var transactions by remember { mutableStateOf(VaultStorage.loadTransactions(context)) }
    var goals by remember { mutableStateOf(VaultStorage.loadGoals(context)) }
    var presets by remember { mutableStateOf(VaultStorage.loadPresets(context)) }
    var quarantineItems by remember { mutableStateOf(VaultStorage.loadQuarantine(context)) }
    var diagnosticsVersion by remember { mutableIntStateOf(0) }

    var isCheckingUpdates by remember { mutableStateOf(false) }
    var availableUpdate by remember { mutableStateOf<AppUpdateInfo?>(null) }
    var dismissedUpdateBanner by remember { mutableStateOf(false) }
    var updateStatusText by remember { mutableStateOf("Tap to Scan GitHub Releases") }
    var devTapCount by remember { mutableIntStateOf(0) }

    var undoBannerItem by remember { mutableStateOf<TransactionItem?>(null) }
    var showUndoBanner by remember { mutableStateOf(false) }
    var undoToken by remember { mutableIntStateOf(0) }

    var goalCelebration by remember { mutableStateOf<GoalCelebrationEvent?>(null) }
    var capWarningEvent by remember { mutableStateOf<CapWarningEvent?>(null) }
    var showCapWarningBanner by remember { mutableStateOf(false) }
    var capWarningToken by remember { mutableIntStateOf(0) }

    var showAddTransactionDialog by remember { mutableStateOf(false) }
    var editingTransaction by remember { mutableStateOf<TransactionItem?>(null) }
    var initialTransactionType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var showGoalEditorDialog by remember { mutableStateOf(false) }
    var editingGoal by remember { mutableStateOf<SavingsGoal?>(null) }
    var activeDepositGoal by remember { mutableStateOf<SavingsGoal?>(null) }
    var activeWithdrawGoal by remember { mutableStateOf<SavingsGoal?>(null) }
    var pendingDeleteGoal by remember { mutableStateOf<SavingsGoal?>(null) }
    var pendingDeletePreset by remember { mutableStateOf<QuickPreset?>(null) }
    var showEditBudgetDialog by remember { mutableStateOf(false) }
    var showSetBalanceDialog by remember { mutableStateOf(false) }
    var showHourlyRateDialog by remember { mutableStateOf(false) }
    var showSimulatorDialog by remember { mutableStateOf(false) }
    var showAddPresetDialog by remember { mutableStateOf(false) }
    var showImportBackupDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }
    var showCreditsDialog by remember { mutableStateOf(false) }
    var showSupportDialog by remember { mutableStateOf(false) }
    var showLogsViewerDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    fun logDiag(level: String, message: String) {
        VaultDiagnostics.log(context, level, message)
        diagnosticsVersion += 1
    }

    fun currentLiquidBalance(): Double {
        val inc = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val exp = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        return openingBalance + inc - exp
    }

    fun ensureTrackingStarted(candidateTimestamp: Long = System.currentTimeMillis()) {
        val safeDay = startOfDayMillis(candidateTimestamp)
        if (trackingStartMillis <= 0L || safeDay < startOfDayMillis(trackingStartMillis)) {
            trackingStartMillis = safeDay
            VaultStorage.saveTrackingStartMillis(context, safeDay)
        }
    }

    fun currentMonthRealExpense(list: List<TransactionItem>): Double {
        return list
            .filter {
                it.type == TransactionType.EXPENSE &&
                    it.category != Category.GOAL_TRANSFER &&
                    isTimestampInCurrentMonth(it.timestamp)
            }
            .sumOf { it.amount }
    }

    fun checkAndTriggerCapWarning(
        previousList: List<TransactionItem>,
        nextList: List<TransactionItem>,
        activeBudget: Double = monthlyBudget
    ) {
        if (activeBudget <= 0.0) return
        val prevSpend = currentMonthRealExpense(previousList)
        val nextSpend = currentMonthRealExpense(nextList)
        if (nextSpend <= prevSpend) return
        val prevRatio = prevSpend / activeBudget
        val nextRatio = nextSpend / activeBudget
        val crossedExceeded = prevRatio < 1.0 && nextRatio >= 1.0
        val crossedApproaching = prevRatio < 0.80 && nextRatio >= 0.80 && nextRatio < 1.0
        val deeperOverCap = prevRatio >= 1.0 && nextRatio > prevRatio
        if (crossedExceeded || crossedApproaching || deeperOverCap) {
            capWarningEvent = CapWarningEvent(
                id = System.currentTimeMillis(),
                isExceeded = nextRatio >= 1.0,
                ratio = nextRatio,
                monthExpense = nextSpend,
                monthlyBudget = activeBudget
            )
            showCapWarningBanner = true
            capWarningToken += 1
            logDiag(
                "BUDGET",
                if (nextRatio >= 1.0) "Monthly spending cap exceeded" else "Approaching monthly spending cap"
            )
        }
    }

    fun checkAndCelebrateGoalCompletion(previousGoal: SavingsGoal?, updatedGoal: SavingsGoal) {
        val wasCompleted = previousGoal != null &&
            previousGoal.targetAmount > 0.0 &&
            previousGoal.savedAmount >= previousGoal.targetAmount
        val isNowCompleted = updatedGoal.targetAmount > 0.0 &&
            updatedGoal.savedAmount >= updatedGoal.targetAmount
        if (!wasCompleted && isNowCompleted) {
            goalCelebration = GoalCelebrationEvent(
                id = System.currentTimeMillis(),
                goalTitle = updatedGoal.title,
                targetAmount = updatedGoal.targetAmount,
                savedAmount = updatedGoal.savedAmount,
                accentColorHex = updatedGoal.colorHex
            )
            logDiag("GOAL", "Savings goal milestone reached")
        }
    }

    fun setDesiredLiquidBalance(desiredBalance: Double) {
        val inc = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val exp = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        val ledgerDelta = inc - exp
        val newOpening = (desiredBalance - ledgerDelta).coerceIn(-MAX_FINANCE_AMOUNT, MAX_FINANCE_AMOUNT)
        openingBalance = newOpening
        VaultStorage.saveOpeningBalance(context, newOpening)
        if (desiredBalance != 0.0) {
            ensureTrackingStarted()
        }
        logDiag("VAULT", "Synced liquid vault balance")
    }

    fun runUpdateCheck(manual: Boolean) {
        if (isCheckingUpdates) return
        val allowPre = devSettingsUnlocked && includePreReleases
        scope.launch {
            isCheckingUpdates = true
            updateStatusText = if (allowPre) "Scanning Releases (Pre-Releases Enabled)..." else "Scanning Stable Releases..."
            val result = VaultUpdater.checkForUpdates(includePreReleases = allowPre)
            isCheckingUpdates = false
            when (result) {
                is UpdateCheckResult.UpdateAvailable -> {
                    availableUpdate = result.info
                    dismissedUpdateBanner = false
                    val tagLabel = if (result.info.isPreRelease) "${result.info.tagName} (Pre-Release)" else result.info.tagName
                    updateStatusText = "Update Available: $tagLabel"
                    logDiag("UPDATE", "Found update ${result.info.tagName} (pre=${result.info.isPreRelease})")
                    if (manual) showUpdateDialog = true
                }
                is UpdateCheckResult.UpToDate -> {
                    availableUpdate = null
                    updateStatusText = "Up to Date (v$APP_VERSION_NAME)"
                    logDiag("UPDATE", "Up to date on v$APP_VERSION_NAME")
                    if (manual) {
                        Toast.makeText(context, "$APP_BRAND_NAME Is Up to Date (v$APP_VERSION_NAME)", Toast.LENGTH_SHORT).show()
                    }
                }
                is UpdateCheckResult.Error -> {
                    updateStatusText = result.message
                    logDiag("UPDATE", "Check skipped or failed")
                    if (manual) {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val resolvedStart = resolveEffectiveTrackingStartMillis(
            storedStartMillis = trackingStartMillis,
            transactions = transactions,
            goals = goals,
            monthlyBudget = monthlyBudget,
            openingBalance = openingBalance
        )
        if (resolvedStart != null && (trackingStartMillis <= 0L || resolvedStart < startOfDayMillis(trackingStartMillis))) {
            trackingStartMillis = resolvedStart
            VaultStorage.saveTrackingStartMillis(context, resolvedStart)
        }
        logDiag(
            "STORAGE",
            "Loaded ${transactions.size} entries, ${goals.size} goals, ${presets.size} presets, ${quarantineItems.size} quarantined"
        )
    }

    LaunchedEffect(capWarningToken) {
        if (showCapWarningBanner && capWarningToken > 0) {
            delay(6500L)
            showCapWarningBanner = false
        }
    }

    LaunchedEffect(setupCompleted, autoCheckUpdates, devSettingsUnlocked, includePreReleases) {
        if (setupCompleted && autoCheckUpdates) {
            runUpdateCheck(manual = false)
        }
    }

    LaunchedEffect(undoToken) {
        if (showUndoBanner && undoToken > 0) {
            delay(5000L)
            showUndoBanner = false
        }
    }

    fun updateTransactions(updated: List<TransactionItem>) {
        val sorted = updated.sortedByDescending { it.timestamp }
        transactions = sorted
        VaultStorage.saveTransactions(context, sorted)
        val earliest = sorted.minOfOrNull { it.timestamp }
        if (earliest != null) {
            ensureTrackingStarted(earliest)
        }
    }

    fun updateGoals(updated: List<SavingsGoal>) {
        goals = updated
        VaultStorage.saveGoals(context, updated)
        if (updated.isNotEmpty()) {
            ensureTrackingStarted()
        }
    }

    fun updatePresets(updated: List<QuickPreset>) {
        presets = updated
        VaultStorage.savePresets(context, updated)
    }

    fun updateQuarantine(updated: List<QuarantineItem>) {
        val sorted = updated.sortedBy { it.unlockAt }
        quarantineItems = sorted
        VaultStorage.saveQuarantine(context, sorted)
    }

    fun recordImpulseResisted(amount: Double) {
        impulseResistedTotal = (impulseResistedTotal + amount).coerceAtMost(MAX_FINANCE_AMOUNT)
        impulseResistedCount += 1
        VaultStorage.saveImpulseResistedTotal(context, impulseResistedTotal)
        VaultStorage.saveImpulseResistedCount(context, impulseResistedCount)
    }

    fun triggerHaptic(strong: Boolean = false) {
        if (hapticsEnabled) {
            haptics.performHapticFeedback(
                if (strong) HapticFeedbackType.LongPress else HapticFeedbackType.TextHandleMove
            )
        }
    }

    fun handleSecretVersionTap() {
        if (devSettingsUnlocked) {
            Toast.makeText(context, "Developer Settings Already Unlocked", Toast.LENGTH_SHORT).show()
            return
        }
        devTapCount += 1
        val remaining = 7 - devTapCount
        if (remaining <= 0) {
            devTapCount = 0
            devSettingsUnlocked = true
            VaultStorage.saveDevSettingsUnlocked(context, true)
            triggerHaptic(strong = true)
            logDiag("DEV", "Developer Settings unlocked")
            Toast.makeText(context, "Developer Settings Unlocked in Settings Tab", Toast.LENGTH_SHORT).show()
        } else if (remaining <= 4) {
            triggerHaptic()
            Toast.makeText(
                context,
                "$remaining More ${if (remaining == 1) "Tap" else "Taps"} to Unlock Developer Settings",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun copyToClipboard(label: String, text: String, toastMessage: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    val hasCrashReport = remember(diagnosticsVersion) {
        VaultDiagnostics.readLastCrash(context) != null
    }

    val animatedPrimary by animateColorAsState(
        targetValue = activeTheme.primary,
        animationSpec = if (animationsEnabled) tween(300, easing = FastOutSlowInEasing) else snap(),
        label = "primaryColor"
    )
    val animatedSecondary by animateColorAsState(
        targetValue = activeTheme.secondary,
        animationSpec = if (animationsEnabled) tween(300, easing = FastOutSlowInEasing) else snap(),
        label = "secondaryColor"
    )

    val appColors = darkColorScheme(
        primary = animatedPrimary,
        onPrimary = Color(0xFF07080B),
        secondary = animatedSecondary,
        background = Color(0xFF07080B),
        surface = Color(0xFF101218),
        onBackground = Color(0xFFF4F6FB),
        onSurface = Color(0xFFF4F6FB),
        onSurfaceVariant = Color(0xFF8B93A7)
    )

    val appTypography = Typography(
        headlineLarge = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 27.sp,
            letterSpacing = (-0.5).sp
        ),
        headlineMedium = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp,
            letterSpacing = (-0.3).sp
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            letterSpacing = (-0.1).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 13.5.sp,
            lineHeight = 19.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            letterSpacing = 0.5.sp
        )
    )

    MaterialTheme(
        colorScheme = appColors,
        typography = appTypography
    ) {
        if (!setupCompleted) {
            val existingRecords = transactions.size + goals.size + presets.size
            val isUpgrading = existingRecords > 0 || monthlyBudget > 0.0 || openingBalance != 0.0
            FirstTimeSetupScreen(
                isUpgrading = isUpgrading,
                existingRecordCount = existingRecords,
                initialCurrency = currency,
                initialTheme = activeTheme,
                initialLiquidBalance = currentLiquidBalance(),
                initialMonthlyBudget = monthlyBudget,
                initialHourlyRate = hourlyRate,
                initialAnimationsEnabled = animationsEnabled,
                initialHapticsEnabled = hapticsEnabled,
                initialHideBalance = hideBalance,
                initialAutoCheckUpdates = autoCheckUpdates,
                primary = animatedPrimary,
                secondary = animatedSecondary,
                onHaptic = { triggerHaptic() },
                onPreviewTheme = { preview ->
                    activeTheme = preview
                    VaultStorage.saveTheme(context, preview)
                },
                onComplete = { chosenCurrency, chosenTheme, desiredBalance, chosenCap, chosenHourly, chosenAnim, chosenHaptics, chosenPrivacy, chosenAutoUpdates ->
                    triggerHaptic(strong = true)
                    currency = chosenCurrency
                    activeTheme = chosenTheme
                    monthlyBudget = chosenCap
                    hourlyRate = chosenHourly
                    animationsEnabled = chosenAnim
                    hapticsEnabled = chosenHaptics
                    hideBalance = chosenPrivacy
                    autoCheckUpdates = chosenAutoUpdates

                    VaultStorage.saveCurrency(context, chosenCurrency)
                    VaultStorage.saveTheme(context, chosenTheme)
                    VaultStorage.saveMonthlyBudget(context, chosenCap)
                    VaultStorage.saveHourlyRate(context, chosenHourly)
                    VaultStorage.saveAnimationsEnabled(context, chosenAnim)
                    VaultStorage.saveHaptics(context, chosenHaptics)
                    VaultStorage.saveHideBalance(context, chosenPrivacy)
                    VaultStorage.saveAutoCheckUpdates(context, chosenAutoUpdates)

                    if (desiredBalance != null) {
                        setDesiredLiquidBalance(desiredBalance)
                    }
                    if (chosenCap > 0.0) {
                        ensureTrackingStarted()
                    }

                    setupCompleted = true
                    VaultStorage.saveSetupCompleted(context, true)
                    logDiag("SETUP", "Completed v$APP_VERSION_NAME setup (upgrading=$isUpgrading)")
                }
            )
        } else {
        val liveLiquidBalance = currentLiquidBalance()

        Scaffold(
            containerColor = Color(0xFF07080B),
            bottomBar = {
                DockedBottomBar(
                    selectedTab = selectedTab,
                    accent = animatedPrimary,
                    onSelectTab = { index ->
                        triggerHaptic()
                        selectedTab = index
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF07080B))
                    .statusBarsPadding()
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .imePadding()
            ) {
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        if (animationsEnabled) {
                            fadeIn(tween(180)) togetherWith fadeOut(tween(120))
                        } else {
                            fadeIn(snap()) togetherWith fadeOut(snap())
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    label = "tabContent"
                ) { tab ->
                    when (tab) {
                        0 -> VaultOverviewScreen(
                            transactions = transactions,
                            goals = goals,
                            presets = presets,
                            quarantineItems = quarantineItems,
                            monthlyBudget = monthlyBudget,
                            openingBalance = openingBalance,
                            hourlyRate = hourlyRate,
                            currency = currency,
                            theme = activeTheme,
                            primary = animatedPrimary,
                            secondary = animatedSecondary,
                            hideBalance = hideBalance,
                            animationsEnabled = animationsEnabled,
                            availableUpdate = if (dismissedUpdateBanner) null else availableUpdate,
                            onOpenUpdateDialog = {
                                triggerHaptic()
                                showUpdateDialog = true
                            },
                            onDismissUpdateBanner = {
                                triggerHaptic()
                                dismissedUpdateBanner = true
                            },
                            onSetBalance = {
                                triggerHaptic()
                                showSetBalanceDialog = true
                            },
                            onTogglePrivacy = {
                                triggerHaptic()
                                hideBalance = !hideBalance
                                VaultStorage.saveHideBalance(context, hideBalance)
                            },
                            onAddExpense = {
                                triggerHaptic()
                                editingTransaction = null
                                initialTransactionType = TransactionType.EXPENSE
                                showAddTransactionDialog = true
                            },
                            onAddIncome = {
                                triggerHaptic()
                                editingTransaction = null
                                initialTransactionType = TransactionType.INCOME
                                showAddTransactionDialog = true
                            },
                            onNewGoal = {
                                triggerHaptic()
                                editingGoal = null
                                showGoalEditorDialog = true
                            },
                            onOpenSimulator = {
                                triggerHaptic()
                                showSimulatorDialog = true
                            },
                            onEditBudget = {
                                triggerHaptic()
                                showEditBudgetDialog = true
                            },
                            onAddPreset = {
                                triggerHaptic()
                                showAddPresetDialog = true
                            },
                            onTriggerPreset = { preset ->
                                triggerHaptic(strong = true)
                                val entry = TransactionItem(
                                    id = nextUniqueId(transactions.map { it.id }),
                                    title = preset.title,
                                    amount = preset.amount,
                                    type = preset.type,
                                    category = preset.category,
                                    timestamp = System.currentTimeMillis(),
                                    note = "Logged via Quick Preset"
                                )
                                val prevList = transactions
                                val nextList = listOf(entry) + transactions
                                updateTransactions(nextList)
                                checkAndTriggerCapWarning(prevList, nextList)
                                logDiag("ACTION", "Logged entry via Quick Preset")
                                Toast.makeText(
                                    context,
                                    "Recorded: ${preset.title}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onDeletePreset = { preset ->
                                triggerHaptic()
                                pendingDeletePreset = preset
                            },
                            onEditTransaction = { item ->
                                triggerHaptic()
                                editingTransaction = item
                                initialTransactionType = item.type
                                showAddTransactionDialog = true
                            },
                            onDeleteTransaction = { item ->
                                triggerHaptic(strong = true)
                                undoBannerItem = item
                                showUndoBanner = true
                                undoToken += 1
                                updateTransactions(transactions.filterNot { it.id == item.id })
                                logDiag("ACTION", "Removed transaction entry")
                            },
                            onResistQuarantine = { item ->
                                triggerHaptic(strong = true)
                                updateQuarantine(quarantineItems.filterNot { it.id == item.id })
                                recordImpulseResisted(item.amount)
                                val targetGoal = item.targetGoalId?.let { gid -> goals.firstOrNull { it.id == gid } }
                                    ?: goals.firstOrNull()
                                if (targetGoal != null) {
                                    val updatedGoal = targetGoal.copy(
                                        savedAmount = (targetGoal.savedAmount + item.amount).coerceAtMost(MAX_FINANCE_AMOUNT)
                                    )
                                    updateGoals(
                                        goals.map { g ->
                                            if (g.id == targetGoal.id) updatedGoal else g
                                        }
                                    )
                                    checkAndCelebrateGoalCompletion(targetGoal, updatedGoal)
                                    val transferEntry = TransactionItem(
                                        id = nextUniqueId(transactions.map { it.id }),
                                        title = "Saved to ${targetGoal.title}",
                                        amount = item.amount,
                                        type = TransactionType.EXPENSE,
                                        category = Category.GOAL_TRANSFER,
                                        timestamp = System.currentTimeMillis(),
                                        note = "Resisted impulse: ${item.title}"
                                    )
                                    updateTransactions(listOf(transferEntry) + transactions)
                                    Toast.makeText(
                                        context,
                                        "Resisted! Moved ${formatMoney(item.amount, currency.symbol, hasFractionalCents(item.amount))} to ${targetGoal.title}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Impulse Resisted: ${formatMoney(item.amount, currency.symbol, hasFractionalCents(item.amount))} Saved!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                logDiag("ACTION", "Resisted quarantined impulse purchase")
                            },
                            onApproveQuarantine = { item ->
                                triggerHaptic(strong = true)
                                updateQuarantine(quarantineItems.filterNot { it.id == item.id })
                                val entry = TransactionItem(
                                    id = nextUniqueId(transactions.map { it.id }),
                                    title = item.title,
                                    amount = item.amount,
                                    type = TransactionType.EXPENSE,
                                    category = item.category,
                                    timestamp = System.currentTimeMillis(),
                                    note = "Approved after Cooling-Off Period"
                                )
                                val prevList = transactions
                                val nextList = listOf(entry) + transactions
                                updateTransactions(nextList)
                                checkAndTriggerCapWarning(prevList, nextList)
                                logDiag("ACTION", "Approved quarantined purchase")
                                Toast.makeText(context, "Purchase Recorded", Toast.LENGTH_SHORT).show()
                            },
                            onRemoveQuarantine = { item ->
                                triggerHaptic()
                                updateQuarantine(quarantineItems.filterNot { it.id == item.id })
                            }
                        )

                        1 -> InsightsScreen(
                            transactions = transactions,
                            goals = goals,
                            currency = currency,
                            primary = animatedPrimary,
                            secondary = animatedSecondary,
                            monthlyBudget = monthlyBudget,
                            openingBalance = openingBalance,
                            trackingStartMillis = trackingStartMillis,
                            hourlyRate = hourlyRate,
                            impulseResistedTotal = impulseResistedTotal,
                            impulseResistedCount = impulseResistedCount,
                            hideBalance = hideBalance,
                            animationsEnabled = animationsEnabled,
                            onHaptic = { triggerHaptic() }
                        )

                        2 -> GoalsScreen(
                            goals = goals,
                            liquidBalance = liveLiquidBalance,
                            impulseResistedTotal = impulseResistedTotal,
                            impulseResistedCount = impulseResistedCount,
                            currency = currency,
                            theme = activeTheme,
                            primary = animatedPrimary,
                            secondary = animatedSecondary,
                            hideBalance = hideBalance,
                            animationsEnabled = animationsEnabled,
                            onAddGoal = {
                                triggerHaptic()
                                editingGoal = null
                                showGoalEditorDialog = true
                            },
                            onEditGoal = { goal ->
                                triggerHaptic()
                                editingGoal = goal
                                showGoalEditorDialog = true
                            },
                            onDepositGoal = { goal ->
                                triggerHaptic()
                                activeDepositGoal = goal
                            },
                            onWithdrawGoal = { goal ->
                                triggerHaptic()
                                activeWithdrawGoal = goal
                            },
                            onDeleteGoal = { goal ->
                                triggerHaptic()
                                pendingDeleteGoal = goal
                            }
                        )

                        else -> SettingsScreen(
                            monthlyBudget = monthlyBudget,
                            currentLiquidBalance = liveLiquidBalance,
                            hourlyRate = hourlyRate,
                            currency = currency,
                            activeTheme = activeTheme,
                            hapticsEnabled = hapticsEnabled,
                            hideBalance = hideBalance,
                            animationsEnabled = animationsEnabled,
                            autoCheckUpdates = autoCheckUpdates,
                            isCheckingUpdates = isCheckingUpdates,
                            updateStatusText = updateStatusText,
                            availableUpdate = availableUpdate,
                            devSettingsUnlocked = devSettingsUnlocked,
                            includePreReleases = includePreReleases,
                            primary = animatedPrimary,
                            hasCrashReport = hasCrashReport,
                            onSetBalance = {
                                triggerHaptic()
                                showSetBalanceDialog = true
                            },
                            onEditBudget = {
                                triggerHaptic()
                                showEditBudgetDialog = true
                            },
                            onEditHourlyRate = {
                                triggerHaptic()
                                showHourlyRateDialog = true
                            },
                            onSelectCurrency = { selected ->
                                triggerHaptic()
                                currency = selected
                                VaultStorage.saveCurrency(context, selected)
                                logDiag("PREF", "Currency changed to ${selected.code}")
                            },
                            onSelectTheme = { selected ->
                                triggerHaptic()
                                activeTheme = selected
                                VaultStorage.saveTheme(context, selected)
                                logDiag("PREF", "Theme changed to ${selected.title}")
                            },
                            onToggleAnimations = { enabled ->
                                animationsEnabled = enabled
                                VaultStorage.saveAnimationsEnabled(context, enabled)
                                triggerHaptic()
                            },
                            onToggleHaptics = { enabled ->
                                hapticsEnabled = enabled
                                VaultStorage.saveHaptics(context, enabled)
                                if (enabled) triggerHaptic()
                            },
                            onTogglePrivacy = { hidden ->
                                triggerHaptic()
                                hideBalance = hidden
                                VaultStorage.saveHideBalance(context, hidden)
                            },
                            onToggleAutoCheckUpdates = { enabled ->
                                triggerHaptic()
                                autoCheckUpdates = enabled
                                VaultStorage.saveAutoCheckUpdates(context, enabled)
                                logDiag("PREF", "Auto update check=$enabled")
                            },
                            onCheckForUpdates = {
                                triggerHaptic()
                                runUpdateCheck(manual = true)
                            },
                            onOpenUpdateDialog = {
                                triggerHaptic()
                                showUpdateDialog = true
                            },
                            onToggleIncludePreReleases = { include ->
                                triggerHaptic()
                                includePreReleases = include
                                VaultStorage.saveIncludePreReleases(context, include)
                                logDiag("DEV", "Pre-release channel=$include")
                                runUpdateCheck(manual = false)
                            },
                            onReplaySetup = {
                                triggerHaptic()
                                setupCompleted = false
                            },
                            onHideDevSettings = {
                                triggerHaptic(strong = true)
                                devSettingsUnlocked = false
                                includePreReleases = false
                                devTapCount = 0
                                VaultStorage.saveDevSettingsUnlocked(context, false)
                                VaultStorage.saveIncludePreReleases(context, false)
                                Toast.makeText(context, "Developer Settings Hidden", Toast.LENGTH_SHORT).show()
                            },
                            onTapVersionSecret = { handleSecretVersionTap() },
                            onExportBackupJson = {
                                triggerHaptic()
                                val json = VaultStorage.exportFullBackupJson(
                                    monthlyBudget = monthlyBudget,
                                    openingBalance = openingBalance,
                                    hourlyRate = hourlyRate,
                                    impulseResistedTotal = impulseResistedTotal,
                                    impulseResistedCount = impulseResistedCount,
                                    trackingStartMillis = trackingStartMillis,
                                    currency = currency,
                                    transactions = transactions,
                                    goals = goals,
                                    presets = presets,
                                    quarantine = quarantineItems
                                )
                                logDiag("BACKUP", "Exported JSON backup")
                                copyToClipboard(
                                    label = "$APP_BRAND_NAME JSON Backup",
                                    text = json,
                                    toastMessage = "JSON Backup Copied to Clipboard"
                                )
                            },
                            onOpenImportBackup = {
                                triggerHaptic()
                                showImportBackupDialog = true
                            },
                            onExportCsv = {
                                triggerHaptic()
                                if (transactions.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "No Transactions Available to Export",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    val csv = VaultStorage.exportCsvStatement(
                                        transactions = transactions,
                                        currency = currency
                                    )
                                    logDiag("EXPORT", "Exported CSV statement")
                                    copyToClipboard(
                                        label = "$APP_BRAND_NAME CSV Statement",
                                        text = csv,
                                        toastMessage = "CSV Statement Copied to Clipboard"
                                    )
                                }
                            },
                            onOpenSupportHub = {
                                triggerHaptic()
                                showSupportDialog = true
                            },
                            onOpenDiagnosticLogs = {
                                triggerHaptic()
                                showLogsViewerDialog = true
                            },
                            onOpenCredits = {
                                triggerHaptic()
                                showCreditsDialog = true
                            },
                            onRequestClearAll = {
                                triggerHaptic()
                                showResetConfirmDialog = true
                            }
                        )
                    }
                }

                // Monthly Cap Warning Animated Floating Banner
                AnimatedVisibility(
                    visible = showCapWarningBanner && capWarningEvent != null,
                    enter = if (animationsEnabled) {
                        fadeIn(tween(220)) + slideInVertically { -it }
                    } else {
                        fadeIn(snap())
                    },
                    exit = if (animationsEnabled) {
                        fadeOut(tween(180)) + slideOutVertically { -it }
                    } else {
                        fadeOut(snap())
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    val event = capWarningEvent
                    if (event != null) {
                        CapWarningFloatingBanner(
                            event = event,
                            currency = currency,
                            hideBalance = hideBalance,
                            animationsEnabled = animationsEnabled,
                            onDismiss = { showCapWarningBanner = false }
                        )
                    }
                }

                // Undo Delete Floating Banner
                AnimatedVisibility(
                    visible = showUndoBanner && undoBannerItem != null,
                    enter = if (animationsEnabled) {
                        fadeIn(tween(180)) + slideInVertically { it / 2 }
                    } else {
                        fadeIn(snap())
                    },
                    exit = if (animationsEnabled) {
                        fadeOut(tween(180)) + slideOutVertically { it / 2 }
                    } else {
                        fadeOut(snap())
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    val deleted = undoBannerItem
                    if (deleted != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFF161B26))
                                .border(0.85.dp, animatedPrimary.copy(alpha = 0.50f), RoundedCornerShape(14.dp))
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Removed \"${deleted.title}\"",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                color = Color.White,
                                modifier = Modifier.weight(1f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Undo",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = animatedPrimary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(animatedPrimary.copy(alpha = 0.16f))
                                    .clickable {
                                        if (showUndoBanner) {
                                            triggerHaptic()
                                            showUndoBanner = false
                                            if (transactions.none { it.id == deleted.id }) {
                                                updateTransactions(listOf(deleted) + transactions)
                                                logDiag("ACTION", "Restored deleted transaction")
                                            }
                                        }
                                    }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }

        if (showAddTransactionDialog) {
            TransactionEditorDialog(
                existingItem = editingTransaction,
                initialType = initialTransactionType,
                goals = goals,
                currency = currency,
                primary = animatedPrimary,
                onDismiss = {
                    showAddTransactionDialog = false
                    editingTransaction = null
                },
                onDelete = editingTransaction?.let { existing ->
                    {
                        triggerHaptic(strong = true)
                        undoBannerItem = existing
                        showUndoBanner = true
                        undoToken += 1
                        updateTransactions(transactions.filterNot { it.id == existing.id })
                        logDiag("ACTION", "Removed transaction entry")
                        showAddTransactionDialog = false
                        editingTransaction = null
                    }
                },
                onDuplicate = editingTransaction?.let { existing ->
                    {
                        triggerHaptic(strong = true)
                        val copy = existing.copy(
                            id = nextUniqueId(transactions.map { it.id }),
                            timestamp = System.currentTimeMillis()
                        )
                        val prevList = transactions
                        val nextList = listOf(copy) + transactions
                        updateTransactions(nextList)
                        checkAndTriggerCapWarning(prevList, nextList)
                        logDiag("ACTION", "Duplicated transaction entry")
                        showAddTransactionDialog = false
                        editingTransaction = null
                        Toast.makeText(context, "Duplicated Entry for Today", Toast.LENGTH_SHORT).show()
                    }
                },
                onSave = { title, amount, type, category, note, timestamp, depositGoalId ->
                    triggerHaptic(strong = true)
                    val existing = editingTransaction
                    if (existing != null) {
                        val prevList = transactions
                        val updated = transactions.map { item ->
                            if (item.id == existing.id) {
                                item.copy(
                                    title = title,
                                    amount = amount,
                                    type = type,
                                    category = category,
                                    timestamp = timestamp,
                                    note = note
                                )
                            } else {
                                item
                            }
                        }
                        updateTransactions(updated)
                        checkAndTriggerCapWarning(prevList, updated)
                        logDiag("ACTION", "Updated transaction entry")
                    } else {
                        val targetGoal = if (type == TransactionType.EXPENSE && depositGoalId != null) {
                            goals.firstOrNull { it.id == depositGoalId }
                        } else {
                            null
                        }
                        val finalCategory = if (targetGoal != null) Category.GOAL_TRANSFER else category
                        val finalNote = if (targetGoal != null) {
                            if (note.isBlank()) "Deposited to Goal: ${targetGoal.title}" else "$note • Goal: ${targetGoal.title}"
                        } else {
                            note
                        }
                        val entry = TransactionItem(
                            id = nextUniqueId(transactions.map { it.id }),
                            title = title,
                            amount = amount,
                            type = type,
                            category = finalCategory,
                            timestamp = timestamp,
                            note = finalNote
                        )
                        val prevList = transactions
                        val nextList = listOf(entry) + transactions
                        updateTransactions(nextList)
                        if (targetGoal != null) {
                            val updatedGoal = targetGoal.copy(
                                savedAmount = (targetGoal.savedAmount + amount).coerceAtMost(MAX_FINANCE_AMOUNT)
                            )
                            updateGoals(
                                goals.map { g ->
                                    if (g.id == targetGoal.id) updatedGoal else g
                                }
                            )
                            checkAndCelebrateGoalCompletion(targetGoal, updatedGoal)
                            logDiag("ACTION", "Transferred expense amount to savings goal")
                            Toast.makeText(
                                context,
                                "Deposited ${formatMoney(amount, currency.symbol, hasFractionalCents(amount))} to ${targetGoal.title}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            checkAndTriggerCapWarning(prevList, nextList)
                            logDiag("ACTION", "Created ${type.name} transaction")
                        }
                    }
                    showAddTransactionDialog = false
                    editingTransaction = null
                }
            )
        }

        if (showSimulatorDialog) {
            PurchaseImpactSimulatorDialog(
                transactions = transactions,
                goals = goals,
                openingBalance = openingBalance,
                monthlyBudget = monthlyBudget,
                hourlyRate = hourlyRate,
                currency = currency,
                primary = animatedPrimary,
                onDismiss = { showSimulatorDialog = false },
                onRecordPurchase = { itemTitle, amount, category ->
                    triggerHaptic(strong = true)
                    val entry = TransactionItem(
                        id = nextUniqueId(transactions.map { it.id }),
                        title = itemTitle,
                        amount = amount,
                        type = TransactionType.EXPENSE,
                        category = category,
                        timestamp = System.currentTimeMillis(),
                        note = "Approved via Purchase Simulator"
                    )
                    val prevList = transactions
                    val nextList = listOf(entry) + transactions
                    updateTransactions(nextList)
                    checkAndTriggerCapWarning(prevList, nextList)
                    logDiag("ACTION", "Recorded purchase via simulator")
                    showSimulatorDialog = false
                    Toast.makeText(context, "Purchase Recorded", Toast.LENGTH_SHORT).show()
                },
                onSaveInstead = { goalId, amount, itemTitle ->
                    triggerHaptic(strong = true)
                    recordImpulseResisted(amount)
                    val targetGoal = goals.firstOrNull { it.id == goalId }
                    if (targetGoal != null) {
                        val updatedGoal = targetGoal.copy(
                            savedAmount = (targetGoal.savedAmount + amount).coerceAtMost(MAX_FINANCE_AMOUNT)
                        )
                        val updated = goals.map { goal ->
                            if (goal.id == goalId) updatedGoal else goal
                        }
                        updateGoals(updated)
                        checkAndCelebrateGoalCompletion(targetGoal, updatedGoal)
                        val transferEntry = TransactionItem(
                            id = nextUniqueId(transactions.map { it.id }),
                            title = "Saved to ${targetGoal.title}",
                            amount = amount,
                            type = TransactionType.EXPENSE,
                            category = Category.GOAL_TRANSFER,
                            timestamp = System.currentTimeMillis(),
                            note = "Skipped purchase: $itemTitle"
                        )
                        updateTransactions(listOf(transferEntry) + transactions)
                        logDiag("ACTION", "Transferred impulse savings to goal")
                        Toast.makeText(
                            context,
                            "Saved ${formatMoney(amount, currency.symbol, hasFractionalCents(amount))} Instead of Buying $itemTitle!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    showSimulatorDialog = false
                },
                onQuarantinePurchase = { itemTitle, amount, category, targetGoalId, hours ->
                    triggerHaptic(strong = true)
                    val now = System.currentTimeMillis()
                    val item = QuarantineItem(
                        id = nextUniqueId(quarantineItems.map { it.id }),
                        title = itemTitle,
                        amount = amount,
                        category = category,
                        targetGoalId = targetGoalId,
                        createdAt = now,
                        unlockAt = now + hours * 60L * 60L * 1000L
                    )
                    updateQuarantine(quarantineItems + item)
                    logDiag("ACTION", "Quarantined planned purchase for ${hours}h")
                    showSimulatorDialog = false
                    Toast.makeText(
                        context,
                        "Locked in Cooling-Off Vault for ${hours}h",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        if (showAddPresetDialog) {
            AddQuickPresetDialog(
                currency = currency,
                primary = animatedPrimary,
                onDismiss = { showAddPresetDialog = false },
                onSave = { title, amount, type, category ->
                    triggerHaptic(strong = true)
                    val preset = QuickPreset(
                        id = nextUniqueId(presets.map { it.id }),
                        title = title,
                        amount = amount,
                        type = type,
                        category = category
                    )
                    updatePresets(presets + preset)
                    logDiag("ACTION", "Created quick preset")
                    showAddPresetDialog = false
                }
            )
        }

        if (showGoalEditorDialog) {
            GoalEditorDialog(
                existingGoal = editingGoal,
                currency = currency,
                primary = animatedPrimary,
                onDismiss = {
                    showGoalEditorDialog = false
                    editingGoal = null
                },
                onSave = { title, target, savedAmount, colorHex ->
                    triggerHaptic(strong = true)
                    val existing = editingGoal
                    if (existing != null) {
                        val updatedGoal = existing.copy(
                            title = title,
                            targetAmount = target,
                            savedAmount = savedAmount,
                            colorHex = colorHex
                        )
                        val updated = goals.map { goal ->
                            if (goal.id == existing.id) updatedGoal else goal
                        }
                        updateGoals(updated)
                        checkAndCelebrateGoalCompletion(existing, updatedGoal)
                        logDiag("ACTION", "Updated savings goal")
                    } else {
                        val goal = SavingsGoal(
                            id = nextUniqueId(goals.map { it.id }),
                            title = title,
                            targetAmount = target,
                            savedAmount = savedAmount,
                            colorHex = colorHex
                        )
                        updateGoals(goals + goal)
                        checkAndCelebrateGoalCompletion(null, goal)
                        logDiag("ACTION", "Created savings goal")
                    }
                    showGoalEditorDialog = false
                    editingGoal = null
                }
            )
        }

        activeDepositGoal?.let { goal ->
            DepositGoalDialog(
                goal = goal,
                liquidBalance = liveLiquidBalance,
                currency = currency,
                primary = animatedPrimary,
                hideBalance = hideBalance,
                onDismiss = { activeDepositGoal = null },
                onConfirm = { depositAmount, transferFromLiquid ->
                    triggerHaptic(strong = true)
                    val updatedGoal = goal.copy(
                        savedAmount = (goal.savedAmount + depositAmount).coerceAtMost(MAX_FINANCE_AMOUNT)
                    )
                    val updated = goals.map { item ->
                        if (item.id == goal.id) updatedGoal else item
                    }
                    updateGoals(updated)
                    checkAndCelebrateGoalCompletion(goal, updatedGoal)
                    if (transferFromLiquid) {
                        val transferEntry = TransactionItem(
                            id = nextUniqueId(transactions.map { it.id }),
                            title = "Deposit to ${goal.title}",
                            amount = depositAmount,
                            type = TransactionType.EXPENSE,
                            category = Category.GOAL_TRANSFER,
                            timestamp = System.currentTimeMillis(),
                            note = "Moved from Liquid Vault to Savings Reserve"
                        )
                        updateTransactions(listOf(transferEntry) + transactions)
                    }
                    logDiag("ACTION", "Deposited to savings goal (fromLiquid=$transferFromLiquid)")
                    activeDepositGoal = null
                }
            )
        }

        activeWithdrawGoal?.let { goal ->
            WithdrawGoalDialog(
                goal = goal,
                currency = currency,
                primary = animatedPrimary,
                hideBalance = hideBalance,
                onDismiss = { activeWithdrawGoal = null },
                onConfirm = { withdrawAmount, returnToLiquid ->
                    triggerHaptic(strong = true)
                    val updated = goals.map { item ->
                        if (item.id == goal.id) {
                            item.copy(
                                savedAmount = max(0.0, item.savedAmount - withdrawAmount)
                            )
                        } else {
                            item
                        }
                    }
                    updateGoals(updated)
                    if (returnToLiquid) {
                        val refundEntry = TransactionItem(
                            id = nextUniqueId(transactions.map { it.id }),
                            title = "Withdraw from ${goal.title}",
                            amount = withdrawAmount,
                            type = TransactionType.INCOME,
                            category = Category.GOAL_WITHDRAW,
                            timestamp = System.currentTimeMillis(),
                            note = "Returned from Savings Reserve to Liquid Vault"
                        )
                        updateTransactions(listOf(refundEntry) + transactions)
                    }
                    logDiag("ACTION", "Withdrew from savings goal (toLiquid=$returnToLiquid)")
                    Toast.makeText(
                        context,
                        if (returnToLiquid) {
                            "Returned ${formatMoney(withdrawAmount, currency.symbol, hasFractionalCents(withdrawAmount))} to Liquid Vault"
                        } else {
                            "Withdrew ${formatMoney(withdrawAmount, currency.symbol, hasFractionalCents(withdrawAmount))} from ${goal.title}"
                        },
                        Toast.LENGTH_SHORT
                    ).show()
                    activeWithdrawGoal = null
                }
            )
        }

        pendingDeleteGoal?.let { goal ->
            ConfirmActionDialog(
                title = "Delete Savings Goal?",
                message = "Remove \"${goal.title}\" and its saved milestone progress from this device?",
                confirmLabel = "Delete Goal",
                onDismiss = { pendingDeleteGoal = null },
                onConfirm = {
                    triggerHaptic(strong = true)
                    updateGoals(goals.filterNot { it.id == goal.id })
                    logDiag("ACTION", "Deleted savings goal")
                    pendingDeleteGoal = null
                }
            )
        }

        pendingDeletePreset?.let { preset ->
            ConfirmActionDialog(
                title = "Delete Quick Preset?",
                message = "Remove the \"${preset.title}\" one-tap preset? Previously logged entries will remain in your ledger.",
                confirmLabel = "Delete Preset",
                onDismiss = { pendingDeletePreset = null },
                onConfirm = {
                    triggerHaptic(strong = true)
                    updatePresets(presets.filterNot { it.id == preset.id })
                    logDiag("ACTION", "Deleted quick preset")
                    pendingDeletePreset = null
                }
            )
        }

        if (showEditBudgetDialog) {
            EditBudgetDialog(
                currentBudget = monthlyBudget,
                currency = currency,
                primary = animatedPrimary,
                onDismiss = { showEditBudgetDialog = false },
                onSave = { newBudget ->
                    triggerHaptic(strong = true)
                    monthlyBudget = newBudget
                    VaultStorage.saveMonthlyBudget(context, newBudget)
                    if (newBudget > 0.0) {
                        ensureTrackingStarted()
                    }
                    logDiag("PREF", "Monthly cap updated")
                    showEditBudgetDialog = false
                }
            )
        }

        if (showSetBalanceDialog) {
            SetBalanceDialog(
                currentBalance = liveLiquidBalance,
                currency = currency,
                primary = animatedPrimary,
                onDismiss = { showSetBalanceDialog = false },
                onSave = { desired ->
                    triggerHaptic(strong = true)
                    setDesiredLiquidBalance(desired)
                    showSetBalanceDialog = false
                    Toast.makeText(
                        context,
                        "Liquid Balance Synced to ${formatMoney(desired, currency.symbol, hasFractionalCents(desired))}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        if (showHourlyRateDialog) {
            EditHourlyRateDialog(
                currentRate = hourlyRate,
                currency = currency,
                primary = animatedPrimary,
                onDismiss = { showHourlyRateDialog = false },
                onSave = { newRate ->
                    triggerHaptic(strong = true)
                    hourlyRate = newRate
                    VaultStorage.saveHourlyRate(context, newRate)
                    logDiag("PREF", "Hourly rate updated")
                    showHourlyRateDialog = false
                }
            )
        }

        if (showUpdateDialog) {
            availableUpdate?.let { update ->
                UpdateAvailableDialog(
                    update = update,
                    primary = animatedPrimary,
                    onDownloadApk = { apkUrl ->
                        triggerHaptic()
                        openExternalUrl(context, apkUrl)
                    },
                    onOpenReleasePage = { url ->
                        triggerHaptic()
                        openExternalUrl(context, url)
                    },
                    onDismiss = { showUpdateDialog = false }
                )
            }
        }

        if (showImportBackupDialog) {
            ImportBackupDialog(
                primary = animatedPrimary,
                onDismiss = { showImportBackupDialog = false },
                onImport = { payload ->
                    triggerHaptic(strong = true)
                    monthlyBudget = payload.monthlyBudget
                    openingBalance = payload.openingBalance
                    hourlyRate = payload.hourlyRate
                    impulseResistedTotal = payload.impulseResistedTotal
                    impulseResistedCount = payload.impulseResistedCount
                    currency = payload.currency
                    VaultStorage.saveMonthlyBudget(context, monthlyBudget)
                    VaultStorage.saveOpeningBalance(context, openingBalance)
                    VaultStorage.saveHourlyRate(context, hourlyRate)
                    VaultStorage.saveImpulseResistedTotal(context, impulseResistedTotal)
                    VaultStorage.saveImpulseResistedCount(context, impulseResistedCount)
                    VaultStorage.saveCurrency(context, currency)
                    val restoredStart = resolveEffectiveTrackingStartMillis(
                        storedStartMillis = payload.trackingStartMillis,
                        transactions = payload.transactions,
                        goals = payload.goals,
                        monthlyBudget = payload.monthlyBudget,
                        openingBalance = payload.openingBalance
                    ) ?: 0L
                    trackingStartMillis = restoredStart
                    VaultStorage.saveTrackingStartMillis(context, restoredStart)
                    updateTransactions(payload.transactions)
                    updateGoals(payload.goals)
                    updatePresets(payload.presets)
                    updateQuarantine(payload.quarantine)
                    showUndoBanner = false
                    undoBannerItem = null
                    logDiag("BACKUP", "Restored JSON backup successfully")
                    showImportBackupDialog = false
                    Toast.makeText(context, "Backup Restored Successfully", Toast.LENGTH_SHORT).show()
                }
            )
        }

        if (showSupportDialog) {
            val supportBundle = remember(
                diagnosticsVersion,
                activeTheme,
                currency,
                transactions.size,
                goals.size,
                presets.size,
                monthlyBudget > 0.0
            ) {
                VaultDiagnostics.buildDiagnosticBundle(
                    context = context,
                    theme = activeTheme,
                    currency = currency,
                    txCount = transactions.size,
                    goalCount = goals.size,
                    presetCount = presets.size,
                    hasMonthlyCap = monthlyBudget > 0.0
                )
            }
            ContactAndReportDialog(
                primary = animatedPrimary,
                diagnosticBundle = supportBundle,
                onSendEmail = { subject, body ->
                    triggerHaptic()
                    launchEmailReport(context, subject, body)
                },
                onOpenGitHubIssue = { title, body ->
                    triggerHaptic()
                    val encTitle = URLEncoder.encode(title, "UTF-8")
                    val encBody = URLEncoder.encode(body.take(1800), "UTF-8")
                    openExternalUrl(context, "$GITHUB_ISSUES_URL?title=$encTitle&body=$encBody")
                },
                onShareReport = { subject, body ->
                    triggerHaptic()
                    sharePlainTextReport(context, subject, body)
                },
                onCopyReport = { fullReport ->
                    triggerHaptic()
                    copyToClipboard(
                        label = "$APP_BRAND_NAME Support Report",
                        text = fullReport,
                        toastMessage = "Report & Diagnostics Copied to Clipboard"
                    )
                },
                onDismiss = { showSupportDialog = false }
            )
        }

        if (showLogsViewerDialog) {
            val recentLogs = remember(diagnosticsVersion) { VaultDiagnostics.readLogs(context) }
            val capturedCrash = remember(diagnosticsVersion) { VaultDiagnostics.readLastCrash(context) }
            DiagnosticLogsDialog(
                primary = animatedPrimary,
                logs = recentLogs,
                lastCrash = capturedCrash,
                onCopyBundle = {
                    triggerHaptic()
                    val bundle = VaultDiagnostics.buildDiagnosticBundle(
                        context = context,
                        theme = activeTheme,
                        currency = currency,
                        txCount = transactions.size,
                        goalCount = goals.size,
                        presetCount = presets.size,
                        hasMonthlyCap = monthlyBudget > 0.0
                    )
                    copyToClipboard(
                        label = "$APP_BRAND_NAME Diagnostic Logs",
                        text = bundle,
                        toastMessage = "Diagnostic Logs Copied to Clipboard"
                    )
                },
                onClearLogs = {
                    triggerHaptic(strong = true)
                    VaultDiagnostics.clearAll(context)
                    diagnosticsVersion += 1
                    showLogsViewerDialog = false
                    Toast.makeText(context, "Diagnostic Logs Cleared", Toast.LENGTH_SHORT).show()
                },
                onDismiss = { showLogsViewerDialog = false }
            )
        }

        if (showResetConfirmDialog) {
            ConfirmActionDialog(
                title = "Clear All Local Data?",
                message = "This action permanently removes all transactions, savings goals, quick presets, quarantined items, synced balance, and your monthly cap from this device.",
                confirmLabel = "Clear Data",
                onDismiss = { showResetConfirmDialog = false },
                onConfirm = {
                    triggerHaptic(strong = true)
                    monthlyBudget = 0.0
                    openingBalance = 0.0
                    hourlyRate = 0.0
                    impulseResistedTotal = 0.0
                    impulseResistedCount = 0
                    VaultStorage.saveMonthlyBudget(context, 0.0)
                    VaultStorage.saveOpeningBalance(context, 0.0)
                    VaultStorage.saveHourlyRate(context, 0.0)
                    VaultStorage.saveImpulseResistedTotal(context, 0.0)
                    VaultStorage.saveImpulseResistedCount(context, 0)
                    trackingStartMillis = 0L
                    VaultStorage.saveTrackingStartMillis(context, 0L)
                    updateTransactions(emptyList())
                    updateGoals(emptyList())
                    updatePresets(emptyList())
                    updateQuarantine(emptyList())
                    showUndoBanner = false
                    undoBannerItem = null
                    showCapWarningBanner = false
                    capWarningEvent = null
                    goalCelebration = null
                    logDiag("STORAGE", "Cleared all local financial records")
                    showResetConfirmDialog = false
                    Toast.makeText(context, "All Data Cleared", Toast.LENGTH_SHORT).show()
                }
            )
        }

        if (showCreditsDialog) {
            CreditsAndLicenseDialog(
                primary = animatedPrimary,
                secondary = animatedSecondary,
                onTapVersionSecret = { handleSecretVersionTap() },
                onOpenChangelog = {
                    triggerHaptic()
                    openExternalUrl(context, GITHUB_CHANGELOG_URL)
                },
                onOpenGitHub = {
                    triggerHaptic()
                    openExternalUrl(context, GITHUB_REPO_URL)
                },
                onCopyAttribution = {
                    triggerHaptic()
                    copyToClipboard(
                        label = "$APP_BRAND_NAME Attribution",
                        text = "$APP_BRAND_NAME v$APP_VERSION_NAME — Open-Source Personal Budget & Savings Visualizer by $DEVELOPER_NAME ($GITHUB_REPO_URL, MIT License)",
                        toastMessage = "Attribution Copied to Clipboard"
                    )
                },
                onDismiss = { showCreditsDialog = false }
            )
        }

        goalCelebration?.let { celebration ->
            GoalMilestoneCelebrationOverlay(
                event = celebration,
                currency = currency,
                primary = animatedPrimary,
                secondary = animatedSecondary,
                hideBalance = hideBalance,
                animationsEnabled = animationsEnabled,
                onDismiss = { goalCelebration = null }
            )
        }
        }
    }
}

@Composable
private fun FirstTimeSetupScreen(
    isUpgrading: Boolean,
    existingRecordCount: Int,
    initialCurrency: CurrencyOption,
    initialTheme: AccentTheme,
    initialLiquidBalance: Double,
    initialMonthlyBudget: Double,
    initialHourlyRate: Double,
    initialAnimationsEnabled: Boolean,
    initialHapticsEnabled: Boolean,
    initialHideBalance: Boolean,
    initialAutoCheckUpdates: Boolean,
    primary: Color,
    secondary: Color,
    onHaptic: () -> Unit,
    onPreviewTheme: (AccentTheme) -> Unit,
    onComplete: (CurrencyOption, AccentTheme, Double?, Double, Double, Boolean, Boolean, Boolean, Boolean) -> Unit
) {
    var step by rememberSaveable { mutableIntStateOf(0) }
    var chosenCurrency by remember { mutableStateOf(initialCurrency) }
    var chosenTheme by remember { mutableStateOf(initialTheme) }
    var balanceText by remember {
        mutableStateOf(if (initialLiquidBalance > 0.0) formatEditableAmount(initialLiquidBalance) else "")
    }
    var balanceEdited by remember { mutableStateOf(false) }
    var budgetText by remember { mutableStateOf(formatEditableAmount(initialMonthlyBudget)) }
    var hourlyText by remember { mutableStateOf(formatEditableAmount(initialHourlyRate)) }
    var chosenAnimations by remember { mutableStateOf(initialAnimationsEnabled) }
    var chosenHaptics by remember { mutableStateOf(initialHapticsEnabled) }
    var chosenPrivacy by remember { mutableStateOf(initialHideBalance) }
    var chosenAutoUpdates by remember { mutableStateOf(initialAutoCheckUpdates) }

    fun finishSetup() {
        val desiredBalance = if (balanceEdited) {
            if (balanceText.isBlank()) 0.0 else balanceText.toDoubleOrNull()?.coerceIn(0.0, MAX_FINANCE_AMOUNT)
        } else {
            null
        }
        val cap = if (budgetText.isBlank()) 0.0 else (budgetText.toDoubleOrNull() ?: 0.0).coerceIn(0.0, MAX_FINANCE_AMOUNT)
        val hourly = if (hourlyText.isBlank()) 0.0 else (hourlyText.toDoubleOrNull() ?: 0.0).coerceIn(0.0, MAX_FINANCE_AMOUNT)
        onComplete(
            chosenCurrency,
            chosenTheme,
            desiredBalance,
            cap,
            hourly,
            chosenAnimations,
            chosenHaptics,
            chosenPrivacy,
            chosenAutoUpdates
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF07080B))
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Top Step Bar + Skip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(4) { idx ->
                    val active = idx <= step
                    Box(
                        modifier = Modifier
                            .width(if (idx == step) 26.dp else 12.dp)
                            .height(5.dp)
                            .clip(CircleShape)
                            .background(if (active) primary else Color(0xFF202532))
                    )
                }
            }
            Text(
                text = "Skip Setup",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.5.sp),
                color = Color(0xFF8B93A7),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onHaptic()
                        finishSetup()
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    if (chosenAnimations) {
                        fadeIn(tween(200)) togetherWith fadeOut(tween(140))
                    } else {
                        fadeIn(snap()) togetherWith fadeOut(snap())
                    }
                },
                label = "setupStep"
            ) { currentStep ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    when (currentStep) {
                        0 -> {
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                FinVaultBrandMark(
                                    primary = primary,
                                    secondary = secondary,
                                    modifier = Modifier.size(84.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Welcome to $APP_BRAND_NAME v$APP_VERSION_NAME",
                                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "A private, offline-first dual-vault ledger built for disciplined spending, savings milestones, and impulse control.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF9EA7BD),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (isUpgrading) {
                                SurfaceCard {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "Existing Data Safely Preserved",
                                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                                            color = Color(0xFF10B981)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Detected $existingRecordCount existing records from your previous FinVault install. Completing this quick setup migrates them cleanly into $APP_BRAND_NAME with all transactions, goals, and presets intact.",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp),
                                            color = Color(0xFFC7CEDF)
                                        )
                                    }
                                }
                            }

                            SurfaceCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(
                                        text = "WHAT IS NEW IN PRODUCTION",
                                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.1.sp),
                                        color = primary
                                    )
                                    Text(
                                        text = "• Separate Liquid Vault & Savings Goal Reserve with direct Balance Sync and Goal Withdrawals.",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp),
                                        color = Color(0xFFD6DCEB)
                                    )
                                    Text(
                                        text = "• Impulse Cooling-Off Quarantine Vault, Time-Cost work-hours calculator, and Monthly Discipline Heatmap.",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp),
                                        color = Color(0xFFD6DCEB)
                                    )
                                    Text(
                                        text = "• Privacy-safe GitHub Release Updater (stable builds only by default) and customizable Animations.",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp),
                                        color = Color(0xFFD6DCEB)
                                    )
                                }
                            }
                        }

                        1 -> {
                            Text(
                                text = "Currency & Visual Theme",
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 23.sp),
                                color = Color.White
                            )
                            Text(
                                text = "Choose your preferred currency symbol and Matte Titanium palette. You can change both anytime in Settings.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF8B93A7)
                            )

                            SurfaceCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "CURRENCY",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF8B93A7)
                                    )
                                    CurrencyOption.entries.forEach { option ->
                                        val selected = option == chosenCurrency
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(if (selected) primary.copy(alpha = 0.16f) else Color(0xFF151822))
                                                .border(
                                                    0.85.dp,
                                                    if (selected) primary else Color(0xFF232836),
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .clickable {
                                                    onHaptic()
                                                    chosenCurrency = option
                                                }
                                                .padding(horizontal = 14.dp, vertical = 11.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = option.label,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                                ),
                                                color = Color.White
                                            )
                                            Text(
                                                text = option.code,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (selected) primary else Color(0xFF8B93A7)
                                            )
                                        }
                                    }
                                }
                            }

                            SurfaceCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(
                                        text = "ACCENT PALETTE",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF8B93A7)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        AccentTheme.entries.take(2).forEach { theme ->
                                            ThemeChoiceCard(
                                                theme = theme,
                                                selected = theme == chosenTheme,
                                                onClick = {
                                                    onHaptic()
                                                    chosenTheme = theme
                                                    onPreviewTheme(theme)
                                                },
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        AccentTheme.entries.drop(2).forEach { theme ->
                                            ThemeChoiceCard(
                                                theme = theme,
                                                selected = theme == chosenTheme,
                                                onClick = {
                                                    onHaptic()
                                                    chosenTheme = theme
                                                    onPreviewTheme(theme)
                                                },
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        2 -> {
                            Text(
                                text = "Configure Your Vault",
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 23.sp),
                                color = Color.White
                            )
                            Text(
                                text = "All fields below are optional. Set your starting spendable balance, monthly spending cap, or hourly rate for Time-Cost insights.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF8B93A7)
                            )

                            SurfaceCard {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    VaultTextField(
                                        label = "Current Liquid Balance (${chosenCurrency.symbol}, Optional)",
                                        value = balanceText,
                                        placeholder = "Example: 5000",
                                        accent = primary,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                        onValueChange = { input ->
                                            balanceText = sanitizeDecimalInput(input)
                                            balanceEdited = true
                                        }
                                    )
                                    VaultTextField(
                                        label = "Monthly Spending Cap (${chosenCurrency.symbol}, Optional)",
                                        value = budgetText,
                                        placeholder = "Example: 10000",
                                        accent = primary,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                        onValueChange = { input -> budgetText = sanitizeDecimalInput(input) }
                                    )
                                    VaultTextField(
                                        label = "Hourly Work Rate (${chosenCurrency.symbol}/Hr, Optional)",
                                        value = hourlyText,
                                        placeholder = "Example: 150 (For Time-Cost Simulator)",
                                        accent = primary,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                        onValueChange = { input -> hourlyText = sanitizeDecimalInput(input) }
                                    )
                                }
                            }
                        }

                        else -> {
                            Text(
                                text = "Experience & Updates",
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 23.sp),
                                color = Color.White
                            )
                            Text(
                                text = "Tune motion, privacy, and update preferences before entering your vault.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF8B93A7)
                            )

                            SurfaceCard {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    SettingsToggleRow(
                                        title = "Smooth Animations",
                                        subtitle = "Fluid transitions and animated gauges. Turn off for instant zero-motion snaps.",
                                        checked = chosenAnimations,
                                        accent = primary,
                                        onCheckedChange = {
                                            onHaptic()
                                            chosenAnimations = it
                                        }
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                            .height(0.75.dp)
                                            .background(Color(0xFF1D212D))
                                    )
                                    SettingsToggleRow(
                                        title = "Haptic Feedback",
                                        subtitle = "Crisp tactile response on tabs and confirmations.",
                                        checked = chosenHaptics,
                                        accent = primary,
                                        onCheckedChange = { chosenHaptics = it }
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                            .height(0.75.dp)
                                            .background(Color(0xFF1D212D))
                                    )
                                    SettingsToggleRow(
                                        title = "Privacy Masking",
                                        subtitle = "Mask financial amounts across all screens by default.",
                                        checked = chosenPrivacy,
                                        accent = primary,
                                        onCheckedChange = {
                                            onHaptic()
                                            chosenPrivacy = it
                                        }
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                            .height(0.75.dp)
                                            .background(Color(0xFF1D212D))
                                    )
                                    SettingsToggleRow(
                                        title = "Check GitHub for Updates",
                                        subtitle = "Automatically scan stable $APP_BRAND_NAME releases on launch. Sends zero personal data.",
                                        checked = chosenAutoUpdates,
                                        accent = primary,
                                        onCheckedChange = {
                                            onHaptic()
                                            chosenAutoUpdates = it
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (step > 0) {
                OutlinedButton(
                    onClick = {
                        onHaptic()
                        step -= 1
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(0.85.dp, Color(0xFF2B3140)),
                    contentPadding = PaddingValues(vertical = 13.dp)
                ) {
                    Text("Back", color = Color(0xFFB5BCD0))
                }
            }
            Button(
                onClick = {
                    onHaptic()
                    if (step < 3) {
                        step += 1
                    } else {
                        finishSetup()
                    }
                },
                modifier = Modifier.weight(if (step > 0) 1.5f else 1f),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                ),
                contentPadding = PaddingValues(vertical = 13.dp)
            ) {
                Text(
                    text = when (step) {
                        0 -> "Get Started"
                        3 -> "Enter $APP_BRAND_NAME"
                        else -> "Continue"
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Immutable
private data class VaultSummary(
    val totalIncome: Double,
    val totalExpense: Double,
    val monthExpense: Double,
    val totalSaved: Double,
    val netBalance: Double,
    val totalNetWorth: Double,
    val budgetRatio: Float,
    val budgetRemaining: Double,
    val dailySafeToSpend: Double,
    val daysLeftInMonth: Int,
    val estimatedRunwayDays: Int?
)

@Composable
private fun VaultOverviewScreen(
    transactions: List<TransactionItem>,
    goals: List<SavingsGoal>,
    presets: List<QuickPreset>,
    quarantineItems: List<QuarantineItem>,
    monthlyBudget: Double,
    openingBalance: Double,
    hourlyRate: Double,
    currency: CurrencyOption,
    theme: AccentTheme,
    primary: Color,
    secondary: Color,
    hideBalance: Boolean,
    animationsEnabled: Boolean,
    availableUpdate: AppUpdateInfo?,
    onOpenUpdateDialog: () -> Unit,
    onDismissUpdateBanner: () -> Unit,
    onSetBalance: () -> Unit,
    onTogglePrivacy: () -> Unit,
    onAddExpense: () -> Unit,
    onAddIncome: () -> Unit,
    onNewGoal: () -> Unit,
    onOpenSimulator: () -> Unit,
    onEditBudget: () -> Unit,
    onAddPreset: () -> Unit,
    onTriggerPreset: (QuickPreset) -> Unit,
    onDeletePreset: (QuickPreset) -> Unit,
    onEditTransaction: (TransactionItem) -> Unit,
    onDeleteTransaction: (TransactionItem) -> Unit,
    onResistQuarantine: (QuarantineItem) -> Unit,
    onApproveQuarantine: (QuarantineItem) -> Unit,
    onRemoveQuarantine: (QuarantineItem) -> Unit
) {
    var filterMode by rememberSaveable { mutableIntStateOf(0) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var monthOffset by rememberSaveable { mutableIntStateOf(0) }
    var allTimeScope by rememberSaveable { mutableStateOf(false) }

    val summary = remember(transactions, goals, monthlyBudget, openingBalance) {
        val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val totalOutflow = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
        val realExpenses = transactions.filter {
            it.type == TransactionType.EXPENSE && it.category != Category.GOAL_TRANSFER
        }
        val totalSpending = realExpenses.sumOf { it.amount }
        val monthExpense = realExpenses
            .filter { isTimestampInCurrentMonth(it.timestamp) }
            .sumOf { it.amount }
        val totalSaved = goals.sumOf { it.savedAmount }
        val netBalance = openingBalance + totalIncome - totalOutflow
        val totalNetWorth = netBalance + totalSaved
        val budgetRatio = if (monthlyBudget > 0.0) (monthExpense / monthlyBudget).toFloat() else 0f
        val remaining = if (monthlyBudget > 0.0) monthlyBudget - monthExpense else 0.0
        val daysLeft = daysRemainingInCurrentMonth()
        val dailySafe = if (monthlyBudget > 0.0) max(0.0, remaining / daysLeft) else 0.0

        val avgDailyBurn = computeRecentDailyBurn(realExpenses)
        val runway = if (netBalance > 0.0 && avgDailyBurn > 0.0) {
            min(999, (netBalance / avgDailyBurn).roundToInt())
        } else {
            null
        }

        VaultSummary(
            totalIncome = totalIncome,
            totalExpense = totalSpending,
            monthExpense = monthExpense,
            totalSaved = totalSaved,
            netBalance = netBalance,
            totalNetWorth = totalNetWorth,
            budgetRatio = budgetRatio,
            budgetRemaining = remaining,
            dailySafeToSpend = dailySafe,
            daysLeftInMonth = daysLeft,
            estimatedRunwayDays = runway
        )
    }

    val periodTransactions = remember(transactions, monthOffset, allTimeScope) {
        if (allTimeScope) {
            transactions
        } else {
            transactions.filter { isTimestampInMonthOffset(it.timestamp, monthOffset) }
        }
    }

    val periodTotals = remember(periodTransactions) {
        val inc = periodTransactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val exp = periodTransactions
            .filter { it.type == TransactionType.EXPENSE && it.category != Category.GOAL_TRANSFER }
            .sumOf { it.amount }
        Triple(inc, exp, inc - exp)
    }

    val filteredTransactions = remember(periodTransactions, filterMode, searchQuery) {
        val byType = when (filterMode) {
            1 -> periodTransactions.filter { it.type == TransactionType.EXPENSE }
            2 -> periodTransactions.filter { it.type == TransactionType.INCOME }
            else -> periodTransactions
        }
        val query = searchQuery.trim().lowercase(Locale.US)
        if (query.isEmpty()) {
            byType
        } else {
            byType.filter { item ->
                item.title.lowercase(Locale.US).contains(query) ||
                    item.category.title.lowercase(Locale.US).contains(query) ||
                    item.note.lowercase(Locale.US).contains(query)
            }
        }
    }

    val animatedBurnRatio by animateFloatAsState(
        targetValue = summary.budgetRatio.coerceIn(0f, 1f),
        animationSpec = if (animationsEnabled) tween(400, easing = FastOutSlowInEasing) else snap(),
        label = "burnProgress"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 14.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Branded Header Row
        item(key = "header", contentType = "header") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    FinVaultBrandMark(
                        primary = primary,
                        secondary = secondary,
                        modifier = Modifier.size(38.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = APP_BRAND_NAME,
                            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 23.sp),
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Dual-Vault Ledger & Savings",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = Color(0xFF8B93A7),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF12151E))
                        .border(0.75.dp, Color(0xFF232836), RoundedCornerShape(12.dp))
                        .clickable(onClick = onTogglePrivacy),
                    contentAlignment = Alignment.Center
                ) {
                    EyeGlyph(
                        hidden = hideBalance,
                        color = if (hideBalance) primary else Color(0xFF9EA7BD),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Update Available Notification Banner (when GitHub Release Scanner finds newer build)
        if (availableUpdate != null) {
            item(key = "update_banner", contentType = "update_banner") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(primary.copy(alpha = 0.14f))
                        .border(0.85.dp, primary.copy(alpha = 0.45f), RoundedCornerShape(16.dp))
                        .clickable(onClick = onOpenUpdateDialog)
                        .padding(horizontal = 14.dp, vertical = 11.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (availableUpdate.isPreRelease) {
                                "Pre-Release Available: ${availableUpdate.tagName}"
                            } else {
                                "Update Available: ${availableUpdate.tagName}"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Tap to view release notes and download from GitHub.",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                            color = Color(0xFFC7CEDF),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.28f))
                            .clickable(onClick = onDismissUpdateBanner),
                        contentAlignment = Alignment.Center
                    ) {
                        CloseGlyph(color = Color(0xFFC7CEDF), modifier = Modifier.size(9.dp))
                    }
                }
            }
        }

        // Signature Matte Titanium Dual-Vault Ledger Card
        item(key = "ledger_card", contentType = "hero") {
            MatteLedgerCard(
                summary = summary,
                monthlyBudget = monthlyBudget,
                burnRatio = animatedBurnRatio,
                currency = currency,
                theme = theme,
                primary = primary,
                secondary = secondary,
                hideBalance = hideBalance,
                animationsEnabled = animationsEnabled,
                onSetBalance = onSetBalance,
                onEditBudget = onEditBudget
            )
        }

        // Cash Flow Split Cards
        item(key = "cashflow_strip", contentType = "cashflow") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CashFlowMiniCard(
                    title = "Total Income",
                    amount = formatMoney(
                        summary.totalIncome,
                        currency.symbol,
                        hasFractionalCents(summary.totalIncome),
                        hideBalance
                    ),
                    accent = Color(0xFF10B981),
                    isUpward = true,
                    modifier = Modifier.weight(1f)
                )
                CashFlowMiniCard(
                    title = "Total Expenses",
                    amount = formatMoney(
                        summary.totalExpense,
                        currency.symbol,
                        hasFractionalCents(summary.totalExpense),
                        hideBalance
                    ),
                    accent = Color(0xFFF43F5E),
                    isUpward = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 4-Column Stacked Icon Action Grid
        item(key = "action_grid", contentType = "actions") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StackedActionTile(
                    label = "Expense",
                    accent = Color(0xFFF43F5E),
                    onClick = onAddExpense,
                    modifier = Modifier.weight(1f)
                ) {
                    ArrowDownGlyph(color = Color(0xFFF43F5E), modifier = Modifier.size(16.dp))
                }
                StackedActionTile(
                    label = "Income",
                    accent = Color(0xFF10B981),
                    onClick = onAddIncome,
                    modifier = Modifier.weight(1f)
                ) {
                    ArrowUpGlyph(color = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                }
                StackedActionTile(
                    label = "Goal",
                    accent = primary,
                    onClick = onNewGoal,
                    modifier = Modifier.weight(1f)
                ) {
                    TargetGlyph(color = primary, modifier = Modifier.size(16.dp))
                }
                StackedActionTile(
                    label = "Simulate",
                    accent = secondary,
                    onClick = onOpenSimulator,
                    modifier = Modifier.weight(1f)
                ) {
                    ScaleGlyph(color = secondary, modifier = Modifier.size(16.dp))
                }
            }
        }

        // Quick-Log Presets Strip
        item(key = "quick_presets", contentType = "presets") {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quick Presets",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "+ Add Preset",
                        style = MaterialTheme.typography.labelSmall,
                        color = primary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(onClick = onAddPreset)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (presets.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF101219))
                            .border(0.75.dp, Color(0xFF1E222E), RoundedCornerShape(14.dp))
                            .clickable(onClick = onAddPreset)
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Save frequent entries like Daily Commute or Weekly Allowance for one-tap logging.",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = Color(0xFF8B93A7)
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        presets.forEach { preset ->
                            val chipAccent = if (preset.type == TransactionType.EXPENSE) {
                                Color(0xFFF43F5E)
                            } else {
                                Color(0xFF10B981)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF11141C))
                                    .border(0.75.dp, chipAccent.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                                    .clickable { onTriggerPreset(preset) }
                                    .padding(start = 12.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                            ) {
                                Column {
                                    Text(
                                        text = preset.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 12.5.sp,
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        color = Color.White,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = formatMoney(preset.amount, currency.symbol, false, hideBalance),
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                        color = chipAccent,
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.06f))
                                        .clickable { onDeletePreset(preset) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    CloseGlyph(color = Color(0xFF8B93A7), modifier = Modifier.size(9.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Impulse Cooling-Off Quarantine Locker (Exclusive Feature)
        if (quarantineItems.isNotEmpty()) {
            item(key = "quarantine_locker", contentType = "quarantine") {
                val nowMs = System.currentTimeMillis()
                SurfaceCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Impulse Cooling-Off Vault",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Wait out planned buys before spending—or claim them as savings.",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                                    color = Color(0xFF8B93A7),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(secondary.copy(alpha = 0.18f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "${quarantineItems.size} Locked",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = secondary
                                )
                            }
                        }

                        quarantineItems.forEach { qItem ->
                            val countdown = formatRemainingCountdown(qItem.unlockAt, nowMs)
                            val ready = nowMs >= qItem.unlockAt
                            val timeCost = formatWorkHours(qItem.amount, hourlyRate)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFF141824))
                                    .border(
                                        0.75.dp,
                                        if (ready) Color(0xFF10B981).copy(alpha = 0.45f) else Color(0xFF232836),
                                        RoundedCornerShape(14.dp)
                                    )
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = qItem.title,
                                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.5.sp),
                                            color = Color.White,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = buildString {
                                                append(formatMoney(qItem.amount, currency.symbol, hasFractionalCents(qItem.amount), hideBalance))
                                                if (timeCost != null && !hideBalance) append(" • $timeCost")
                                            },
                                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                                            color = Color(0xFF9EA7BD),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = countdown,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = if (ready) Color(0xFF10B981) else Color(0xFFF59E0B),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(7.dp))
                                            .background(
                                                (if (ready) Color(0xFF10B981) else Color(0xFFF59E0B)).copy(alpha = 0.14f)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1.2f)
                                            .clip(RoundedCornerShape(9.dp))
                                            .background(Color(0xFF10B981).copy(alpha = 0.18f))
                                            .border(0.75.dp, Color(0xFF10B981).copy(alpha = 0.45f), RoundedCornerShape(9.dp))
                                            .clickable { onResistQuarantine(qItem) }
                                            .padding(vertical = 7.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Resist & Save",
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                            color = Color(0xFF10B981),
                                            maxLines = 1
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(9.dp))
                                            .background(Color(0xFF1A1F2C))
                                            .border(0.75.dp, Color(0xFF282E3E), RoundedCornerShape(9.dp))
                                            .clickable { onApproveQuarantine(qItem) }
                                            .padding(vertical = 7.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Buy Now",
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                            color = Color.White,
                                            maxLines = 1
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(RoundedCornerShape(9.dp))
                                            .background(Color(0xFF1A1F2C))
                                            .clickable { onRemoveQuarantine(qItem) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CloseGlyph(color = Color(0xFF8B93A7), modifier = Modifier.size(9.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Recent Activity + Month Navigator + Period Summary + Search + Filter
        item(key = "activity_header", contentType = "activity_header") {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Activity",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${filteredTransactions.size} ${if (filteredTransactions.size == 1) "Entry" else "Entries"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF8B93A7)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Month Navigator Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF10131B))
                        .border(0.75.dp, Color(0xFF202532), RoundedCornerShape(12.dp))
                        .padding(horizontal = 6.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF171B26))
                            .clickable {
                                allTimeScope = false
                                monthOffset -= 1
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        ChevronLeftGlyph(color = Color.White, modifier = Modifier.size(12.dp))
                    }

                    Text(
                        text = if (allTimeScope) "All Time History" else formatMonthYearLabel(monthOffset),
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                if (allTimeScope || monthOffset != 0) {
                                    allTimeScope = false
                                    monthOffset = 0
                                } else {
                                    allTimeScope = true
                                }
                            },
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (allTimeScope) primary.copy(alpha = 0.20f) else Color(0xFF171B26)
                                )
                                .clickable { allTimeScope = !allTimeScope }
                                .padding(horizontal = 9.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (allTimeScope) "Month" else "All",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                color = if (allTimeScope) primary else Color(0xFF9EA7BD),
                                maxLines = 1
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF171B26))
                                .clickable(enabled = allTimeScope || monthOffset < 0) {
                                    if (allTimeScope) {
                                        allTimeScope = false
                                        monthOffset = 0
                                    } else if (monthOffset < 0) {
                                        monthOffset += 1
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            ChevronRightGlyph(
                                color = if (allTimeScope || monthOffset < 0) Color.White else Color(0xFF4B5266),
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Period Summary Strip (Inflow / Outflow / Net for Selected Scope)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(11.dp))
                        .background(Color(0xFF0E1118))
                        .border(0.75.dp, Color(0xFF1C202D), RoundedCornerShape(11.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "In: ${formatMoney(periodTotals.first, currency.symbol, false, hideBalance)}",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                        color = Color(0xFF10B981),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Spend: ${formatMoney(periodTotals.second, currency.symbol, false, hideBalance)}",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                        color = Color(0xFFFB7185),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Net: ${formatMoney(periodTotals.third, currency.symbol, false, hideBalance)}",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                        color = Color(0xFFD6DCEB),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Compact Live Search Field
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF10131B))
                        .border(0.75.dp, Color(0xFF202532), RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp)
                ) {
                    SearchGlyph(color = Color(0xFF7B8399), modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(9.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search Title, Category, or Note...",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                color = Color(0xFF5D657A),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        BasicTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            singleLine = true,
                            cursorBrush = SolidColor(primary),
                            textStyle = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 13.sp,
                                color = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (searchQuery.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.08f))
                                .clickable { searchQuery = "" },
                            contentAlignment = Alignment.Center
                        ) {
                            CloseGlyph(color = Color.White, modifier = Modifier.size(9.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                SegmentedFilterBar(
                    options = listOf("All", "Expenses", "Income"),
                    selectedIndex = filterMode,
                    accent = primary,
                    animationsEnabled = animationsEnabled,
                    onSelect = { filterMode = it }
                )
            }
        }

        if (filteredTransactions.isEmpty()) {
            item(key = "empty_state", contentType = "empty") {
                SurfaceCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 28.dp, horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (transactions.isEmpty()) {
                                "Your Ledger Is Ready"
                            } else {
                                "No Entries in This View"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (transactions.isEmpty()) {
                                "Start by recording an Income or Expense entry, or set your Monthly Cap on the card above."
                            } else {
                                "Try switching months, selecting All Time, or clearing your search filter."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF8B93A7),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(
                items = filteredTransactions,
                key = { it.id },
                contentType = { it.type }
            ) { item ->
                TransactionCardRow(
                    item = item,
                    currency = currency,
                    hideBalance = hideBalance,
                    onClick = { onEditTransaction(item) },
                    onDelete = { onDeleteTransaction(item) }
                )
            }
        }
    }
}

@Composable
private fun MatteLedgerCard(
    summary: VaultSummary,
    monthlyBudget: Double,
    burnRatio: Float,
    currency: CurrencyOption,
    theme: AccentTheme,
    primary: Color,
    secondary: Color,
    hideBalance: Boolean,
    animationsEnabled: Boolean,
    onSetBalance: () -> Unit,
    onEditBudget: () -> Unit
) {
    val hasBudget = monthlyBudget > 0.0
    val isWarningActive = hasBudget && summary.budgetRatio >= 0.80f
    val isOverCap = hasBudget && summary.budgetRatio >= 1.0f
    val percentUsedLabel = if (hasBudget) formatCompactPercent(summary.budgetRatio.toDouble()) else "0%"
    val statusColor = when {
        !hasBudget -> primary
        isOverCap -> Color(0xFFF43F5E)
        isWarningActive -> Color(0xFFF59E0B)
        else -> Color(0xFF10B981)
    }
    val statusText = when {
        !hasBudget -> "Set Cap"
        isOverCap -> "Over Cap"
        isWarningActive -> "Near Limit"
        else -> "Healthy"
    }

    val warningPulse = if (isWarningActive && animationsEnabled) {
        val transition = rememberInfiniteTransition(label = "capWarningPulse")
        val anim by transition.animateFloat(
            initialValue = 0.35f,
            targetValue = 0.92f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = if (isOverCap) 850 else 1250, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "capWarningAlpha"
        )
        anim
    } else if (isWarningActive) {
        0.72f
    } else {
        0f
    }

    val cardBorderBrush = if (isWarningActive) {
        Brush.linearGradient(
            colors = listOf(
                statusColor.copy(alpha = warningPulse),
                Color.White.copy(alpha = 0.12f),
                statusColor.copy(alpha = warningPulse * 0.72f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                primary.copy(alpha = 0.42f),
                Color.White.copy(alpha = 0.08f),
                secondary.copy(alpha = 0.28f)
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(theme.cardTop, theme.cardMid, theme.cardBottom)
                )
            )
            .border(
                width = if (isWarningActive) 1.25.dp else 0.85.dp,
                brush = cardBorderBrush,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawLine(
                brush = Brush.linearGradient(
                    listOf(Color.Transparent, Color.White.copy(alpha = 0.045f), Color.Transparent)
                ),
                start = Offset(size.width * 0.52f, 0f),
                end = Offset(size.width * 0.96f, size.height),
                strokeWidth = 60.dp.toPx()
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(primary.copy(alpha = 0.13f), Color.Transparent),
                    center = Offset(size.width * 0.92f, size.height * 0.14f),
                    radius = size.width * 0.48f
                ),
                radius = size.width * 0.48f,
                center = Offset(size.width * 0.92f, size.height * 0.14f)
            )
        }

        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LIQUID VAULT BALANCE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        letterSpacing = 1.3.sp
                    ),
                    color = Color(0xFF9EA7BD),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.width(6.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.08f))
                        .border(0.75.dp, Color.White.copy(alpha = 0.14f), RoundedCornerShape(8.dp))
                        .clickable(onClick = onSetBalance)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Set Balance",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = Color(0xFFE2E8F0),
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .border(0.75.dp, statusColor.copy(alpha = 0.42f), RoundedCornerShape(8.dp))
                        .clickable(onClick = onEditBudget)
                        .padding(horizontal = 9.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .graphicsLayer {
                                val s = if (isWarningActive && animationsEnabled) 0.85f + (warningPulse * 0.38f) else 1f
                                scaleX = s
                                scaleY = s
                            }
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = statusColor,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = formatMoney(summary.netBalance, currency.symbol, true, hideBalance),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 33.sp,
                    letterSpacing = (-1.0).sp
                ),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable(onClick = onSetBalance)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Goal Reserve Vault: ${formatMoney(summary.totalSaved, currency.symbol, hasFractionalCents(summary.totalSaved), hideBalance)} • Total Worth: ${formatMoney(summary.totalNetWorth, currency.symbol, false, hideBalance)}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                color = Color(0xFF9EA7BD),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Safe-to-Spend & Cash Runway Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.Black.copy(alpha = 0.30f))
                    .border(0.75.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "SAFE TO SPEND TODAY",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                        color = Color(0xFF8B93A7),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (hasBudget) {
                            "${formatMoney(summary.dailySafeToSpend, currency.symbol, false, hideBalance)} / Day"
                        } else {
                            "Set Monthly Cap"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .width(0.75.dp)
                        .height(28.dp)
                        .background(Color.White.copy(alpha = 0.10f))
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CASH RUNWAY",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                        color = Color(0xFF8B93A7),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = when {
                            hideBalance -> "•••• Days"
                            summary.estimatedRunwayDays != null -> "${summary.estimatedRunwayDays} Days Est."
                            summary.netBalance > 0.0 -> "No Spend Pace"
                            else -> "Awaiting Data"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (hasBudget) {
                        "Monthly Cap (${formatMoney(monthlyBudget, currency.symbol, false, hideBalance)})"
                    } else {
                        "No Monthly Cap Configured"
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color(0xFFC7CEDF),
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onEditBudget),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (hasBudget) "$percentUsedLabel Used" else "Tap to Set",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = primary,
                    modifier = Modifier.clickable(onClick = onEditBudget),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.09f))
            ) {
                if (hasBudget) {
                    val barStartColor = when {
                        isOverCap -> Color(0xFFF59E0B)
                        isWarningActive -> primary
                        else -> primary
                    }
                    val barEndColor = when {
                        isOverCap -> Color(0xFFF43F5E)
                        isWarningActive -> Color(0xFFF59E0B)
                        else -> secondary
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(burnRatio)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        barStartColor,
                                        if (isWarningActive) barEndColor.copy(alpha = 0.72f + (warningPulse * 0.28f)) else barEndColor
                                    )
                                )
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when {
                        !hasBudget -> "${summary.daysLeftInMonth} Days Remaining in Month"
                        summary.budgetRemaining >= 0 ->
                            "Left: ${formatMoney(summary.budgetRemaining, currency.symbol, false, hideBalance)} • ${summary.daysLeftInMonth}d Left"
                        else ->
                            "Exceeded By: ${formatMoney(abs(summary.budgetRemaining), currency.symbol, false, hideBalance)}"
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                    color = if (hasBudget && summary.budgetRemaining < 0) Color(0xFFF43F5E) else Color(0xFF9EA7BD),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit Cap",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = primary,
                    modifier = Modifier.clickable(onClick = onEditBudget),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun CashFlowMiniCard(
    title: String,
    amount: String,
    accent: Color,
    isUpward: Boolean,
    modifier: Modifier = Modifier
) {
    SurfaceCard(modifier = modifier) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = Color(0xFF8B93A7),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(6.dp))
                SparklineGlyph(
                    color = accent,
                    upward = isUpward,
                    modifier = Modifier.size(width = 24.dp, height = 12.dp)
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = amount,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun StackedActionTile(
    label: String,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF11141C))
            .border(0.75.dp, Color(0xFF232836), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(accent.copy(alpha = 0.16f)),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.1.sp
            ),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SegmentedFilterBar(
    options: List<String>,
    selectedIndex: Int,
    accent: Color,
    animationsEnabled: Boolean = true,
    onSelect: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF10131B))
            .border(0.75.dp, Color(0xFF202532), RoundedCornerShape(12.dp))
            .padding(3.dp)
    ) {
        val segmentWidth = maxWidth / options.size
        val pillOffset by animateDpAsState(
            targetValue = segmentWidth * selectedIndex,
            animationSpec = if (animationsEnabled) tween(200, easing = FastOutSlowInEasing) else snap(),
            label = "filterPill"
        )

        Box(
            modifier = Modifier
                .offset(x = pillOffset)
                .width(segmentWidth)
                .fillMaxHeight()
                .clip(RoundedCornerShape(9.dp))
                .background(accent.copy(alpha = 0.18f))
                .border(0.75.dp, accent.copy(alpha = 0.45f), RoundedCornerShape(9.dp))
        )

        Row(modifier = Modifier.fillMaxSize()) {
            options.forEachIndexed { index, title ->
                val isSelected = index == selectedIndex
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(9.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onSelect(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (isSelected) Color.White else Color(0xFF8B93A7),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionCardRow(
    item: TransactionItem,
    currency: CurrencyOption,
    hideBalance: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val isExpense = item.type == TransactionType.EXPENSE
    val amountColor = if (isExpense) Color(0xFFFB7185) else Color(0xFF10B981)
    val signedAmount = if (hideBalance) {
        "${currency.symbol}••••"
    } else if (isExpense) {
        "-${formatMoney(item.amount, currency.symbol)}"
    } else {
        "+${formatMoney(item.amount, currency.symbol)}"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF101219))
            .border(0.75.dp, Color(0xFF1E222E), RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(34.dp)
                .clip(CircleShape)
                .background(item.category.color)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.5.sp),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${item.category.title} • ${formatDateLabel(item.timestamp)}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                color = Color(0xFF8B93A7),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (item.note.isNotBlank()) {
                Text(
                    text = item.note,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = Color(0xFF666E82),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = signedAmount,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = amountColor,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Remove",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = Color(0xFF747C91),
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(onClick = onDelete)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
    }
}

@Immutable
private data class AnalyticsSnapshot(
    val totalIncome: Double,
    val totalExpense: Double,
    val monthExpense: Double,
    val savingsRate: Int,
    val dailyAverage: Double,
    val noSpendStreak: Int,
    val largestExpenseTitle: String?,
    val largestExpenseAmount: Double,
    val categoryTotals: List<Pair<Category, Double>>,
    val sevenDayPoints: List<Pair<String, Double>>,
    val monthDaySpends: List<Double>,
    val currentDayOfMonth: Int,
    val heatmapStartDayOfMonth: Int,
    val healthScore: Int?,
    val healthGrade: String,
    val healthGuidance: String
)

@Composable
private fun InsightsScreen(
    transactions: List<TransactionItem>,
    goals: List<SavingsGoal>,
    currency: CurrencyOption,
    primary: Color,
    secondary: Color,
    monthlyBudget: Double,
    openingBalance: Double,
    trackingStartMillis: Long,
    hourlyRate: Double,
    impulseResistedTotal: Double,
    impulseResistedCount: Int,
    hideBalance: Boolean,
    animationsEnabled: Boolean,
    onHaptic: () -> Unit
) {
    val analytics = remember(transactions, goals, monthlyBudget, openingBalance, trackingStartMillis) {
        val expenses = transactions.filter {
            it.type == TransactionType.EXPENSE && it.category != Category.GOAL_TRANSFER
        }
        val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
        val totalExpense = expenses.sumOf { it.amount }
        val monthExpense = expenses.filter { isTimestampInCurrentMonth(it.timestamp) }.sumOf { it.amount }

        val effectiveTrackingStart = resolveEffectiveTrackingStartMillis(
            storedStartMillis = trackingStartMillis,
            transactions = transactions,
            goals = goals,
            monthlyBudget = monthlyBudget,
            openingBalance = openingBalance
        )
        val largest = expenses.maxByOrNull { it.amount }
        val streak = computeNoSpendStreakDays(transactions, effectiveTrackingStart)

        val categoryTotals = expenses
            .groupBy { it.category }
            .mapValues { (_, list) -> list.sumOf { it.amount } }
            .entries
            .sortedByDescending { it.value }
            .map { Pair(it.key, it.value) }

        val savingsRate = if (totalIncome > 0.0) {
            (((totalIncome - totalExpense) / totalIncome) * 100.0).roundToInt().coerceIn(-100, 100)
        } else {
            0
        }

        val dailyAverage = computeAverageDailySpend(expenses)

        val labelFmt = SimpleDateFormat("EEE", Locale.US)
        val keyFmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val dailyMap = expenses.groupBy { keyFmt.format(Date(it.timestamp)) }
            .mapValues { (_, items) -> items.sumOf { it.amount } }
        val sevenDays = (6 downTo 0).map { daysAgo ->
            val dayDate = calendarDaysAgo(daysAgo).time
            val key = keyFmt.format(dayDate)
            Pair(labelFmt.format(dayDate), dailyMap[key] ?: 0.0)
        }

        // Current Month Discipline Heatmap Data
        val monthCal = Calendar.getInstance()
        val daysInMonth = monthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val todayDayOfMonth = monthCal.get(Calendar.DAY_OF_MONTH)
        val currentYear = monthCal.get(Calendar.YEAR)
        val currentMonth = monthCal.get(Calendar.MONTH)
        val dayBuckets = DoubleArray(daysInMonth)
        val itemCal = Calendar.getInstance()
        expenses.forEach { tx ->
            itemCal.timeInMillis = tx.timestamp
            if (itemCal.get(Calendar.YEAR) == currentYear && itemCal.get(Calendar.MONTH) == currentMonth) {
                val idx = itemCal.get(Calendar.DAY_OF_MONTH) - 1
                if (idx in 0 until daysInMonth) {
                    dayBuckets[idx] += tx.amount
                }
            }
        }
        val monthDaySpends = dayBuckets.toList()
        val firstOfCurrentMonth = Calendar.getInstance().apply {
            set(Calendar.YEAR, currentYear)
            set(Calendar.MONTH, currentMonth)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val heatmapStartDayOfMonth = if (effectiveTrackingStart == null) {
            daysInMonth + 1
        } else if (effectiveTrackingStart <= firstOfCurrentMonth) {
            1
        } else {
            val startCal = Calendar.getInstance().apply { timeInMillis = effectiveTrackingStart }
            if (startCal.get(Calendar.YEAR) == currentYear && startCal.get(Calendar.MONTH) == currentMonth) {
                startCal.get(Calendar.DAY_OF_MONTH).coerceIn(1, daysInMonth)
            } else {
                daysInMonth + 1
            }
        }

        val score: Int?
        val grade: String
        val guidance: String
        if (transactions.isEmpty() && goals.isEmpty()) {
            score = null
            grade = "Awaiting Data"
            guidance = "Record income, expenses, or savings goals to compute your live financial health score."
        } else {
            val savingsPts = when {
                totalIncome <= 0.0 -> 15
                savingsRate >= 30 -> 40
                savingsRate >= 15 -> 32
                savingsRate >= 0 -> 22
                else -> 6
            }
            val budgetPts = when {
                monthlyBudget <= 0.0 -> 20
                monthExpense <= monthlyBudget * 0.75 -> 35
                monthExpense <= monthlyBudget -> 26
                monthExpense <= monthlyBudget * 1.15 -> 14
                else -> 5
            }
            val totalTarget = goals.sumOf { it.targetAmount }
            val totalSaved = goals.sumOf { it.savedAmount }
            val goalRatio = if (totalTarget > 0.0) (totalSaved / totalTarget).coerceIn(0.0, 1.0) else 0.0
            val goalPts = if (goals.isEmpty()) 10 else (10 + (goalRatio * 15.0).roundToInt())

            val computed = (savingsPts + budgetPts + goalPts).coerceIn(0, 100)
            score = computed
            grade = when {
                computed >= 85 -> "Excellent"
                computed >= 70 -> "Strong"
                computed >= 50 -> "Balanced"
                else -> "Needs Attention"
            }
            guidance = when {
                monthlyBudget <= 0.0 -> "Set a Monthly Cap on the Vault tab to unlock full budget discipline scoring."
                monthExpense > monthlyBudget -> "Monthly expenses exceeded your cap. Consider running the Purchase Simulator before new buys."
                savingsRate < 15 && totalIncome > 0.0 -> "Try moving at least 15% of new income into an active Savings Goal."
                else -> "Your cash flow and spending pace are disciplined. Keep funding your active goals."
            }
        }

        AnalyticsSnapshot(
            totalIncome = totalIncome,
            totalExpense = totalExpense,
            monthExpense = monthExpense,
            savingsRate = savingsRate,
            dailyAverage = dailyAverage,
            noSpendStreak = streak,
            largestExpenseTitle = largest?.title,
            largestExpenseAmount = largest?.amount ?: 0.0,
            categoryTotals = categoryTotals,
            sevenDayPoints = sevenDays,
            monthDaySpends = monthDaySpends,
            currentDayOfMonth = todayDayOfMonth,
            heatmapStartDayOfMonth = heatmapStartDayOfMonth,
            healthScore = score,
            healthGrade = grade,
            healthGuidance = guidance
        )
    }

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedHeatmapDay by remember { mutableIntStateOf(-1) }
    LaunchedEffect(analytics.categoryTotals) {
        if (selectedCategory != null && analytics.categoryTotals.none { it.first == selectedCategory }) {
            selectedCategory = null
        }
    }
    var chartReady by remember { mutableStateOf(!animationsEnabled) }
    LaunchedEffect(animationsEnabled) { chartReady = true }

    val animProgress by animateFloatAsState(
        targetValue = if (chartReady) 1f else 0f,
        animationSpec = if (animationsEnabled) tween(480, easing = FastOutSlowInEasing) else snap(),
        label = "donutReveal"
    )

    val activeEntry = analytics.categoryTotals.firstOrNull { it.first == selectedCategory }
    val centerLabel = activeEntry?.first?.title ?: "Total Expenses"
    val centerValue = activeEntry?.second ?: analytics.totalExpense
    val centerShare = if (analytics.totalExpense > 0.0) {
        ((centerValue / analytics.totalExpense) * 100.0).roundToInt()
    } else {
        0
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 14.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "insights_header") {
            Column {
                Text(
                    text = "Financial Insights",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Text(
                    text = "Health score, spending habits, and category share.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                    color = Color(0xFF8B93A7)
                )
            }
        }

        // Vault Health Score Card
        item(key = "health_score") {
            val scoreVal = analytics.healthScore
            val scoreColor = when {
                scoreVal == null -> primary
                scoreVal >= 80 -> Color(0xFF10B981)
                scoreVal >= 55 -> Color(0xFFF59E0B)
                else -> Color(0xFFF43F5E)
            }
            val scoreSweep = if (scoreVal != null) (scoreVal / 100f) * 360f * animProgress else 0f

            SurfaceCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(76.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val strokeW = 7.dp.toPx()
                            val inset = strokeW / 2f
                            val arcSize = Size(size.width - strokeW, size.height - strokeW)
                            drawArc(
                                color = Color(0xFF1D212D),
                                startAngle = 0f,
                                sweepAngle = 360f,
                                useCenter = false,
                                topLeft = Offset(inset, inset),
                                size = arcSize,
                                style = Stroke(width = strokeW, cap = StrokeCap.Round)
                            )
                            if (scoreSweep > 0f) {
                                drawArc(
                                    color = scoreColor,
                                    startAngle = -90f,
                                    sweepAngle = scoreSweep,
                                    useCenter = false,
                                    topLeft = Offset(inset, inset),
                                    size = arcSize,
                                    style = Stroke(width = strokeW, cap = StrokeCap.Round)
                                )
                            }
                        }
                        Text(
                            text = scoreVal?.toString() ?: "--",
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Vault Health Score",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.weight(1f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = analytics.healthGrade,
                                style = MaterialTheme.typography.labelSmall,
                                color = scoreColor,
                                maxLines = 1
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = analytics.healthGuidance,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = Color(0xFF8B93A7)
                        )
                    }
                }
            }
        }

        // Habit Highlights: No-Spend Streak & Largest Expense
        item(key = "habit_highlights") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SurfaceCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "NO-SPEND STREAK",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = Color(0xFF8B93A7),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${analytics.noSpendStreak} ${if (analytics.noSpendStreak == 1) "Day" else "Days"}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color(0xFF10B981),
                            maxLines = 1
                        )
                        Text(
                            text = "Consecutive Zero Spend",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                            color = Color(0xFF7B8399),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                SurfaceCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "LARGEST EXPENSE",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = Color(0xFF8B93A7),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (analytics.largestExpenseTitle != null) {
                                formatMoney(analytics.largestExpenseAmount, currency.symbol, false, hideBalance)
                            } else {
                                "None"
                            },
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color(0xFFFB7185),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = analytics.largestExpenseTitle ?: "No Expenses Logged",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                            color = Color(0xFF7B8399),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Exclusive Behavioral Cards: Time-Cost & Impulse Resisted
        if (hourlyRate > 0.0 || impulseResistedTotal > 0.0) {
            item(key = "behavioral_exclusive") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (hourlyRate > 0.0) {
                        val monthWorkTime = formatWorkHours(analytics.monthExpense, hourlyRate) ?: "0 Mins"
                        SurfaceCard(modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "TIME-COST THIS MONTH",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = Color(0xFF8B93A7),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (hideBalance) "•••• Hrs" else monthWorkTime,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = primary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Work Hours Spent",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                                    color = Color(0xFF7B8399),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    if (impulseResistedTotal > 0.0) {
                        SurfaceCard(modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "IMPULSE RESISTED",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = Color(0xFF8B93A7),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = formatMoney(impulseResistedTotal, currency.symbol, false, hideBalance),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color(0xFF10B981),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "$impulseResistedCount ${if (impulseResistedCount == 1) "Buy" else "Buys"} Defeated",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                                    color = Color(0xFF7B8399),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // Exclusive Monthly Spending Discipline Heatmap
        item(key = "discipline_heatmap") {
            val dailyPace = if (monthlyBudget > 0.0) {
                monthlyBudget / max(1, analytics.monthDaySpends.size)
            } else {
                max(1.0, analytics.dailyAverage)
            }
            val hasTrackedDays = analytics.heatmapStartDayOfMonth <= analytics.currentDayOfMonth
            val zeroSpendDaysCount = if (hasTrackedDays) {
                (analytics.heatmapStartDayOfMonth..analytics.currentDayOfMonth).count { dayNum ->
                    analytics.monthDaySpends[dayNum - 1] <= 0.0
                }
            } else {
                0
            }
            val heatmapSubtitle = when {
                !hasTrackedDays -> "${formatMonthYearLabel(0)} • Record a Value to Start"
                analytics.heatmapStartDayOfMonth > 1 ->
                    "${formatMonthYearLabel(0)} • From Day ${analytics.heatmapStartDayOfMonth} • $zeroSpendDaysCount Zero-Spend"
                else -> "${formatMonthYearLabel(0)} • $zeroSpendDaysCount Zero-Spend Days"
            }

            SurfaceCard {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Monthly Discipline Heatmap",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = heatmapSubtitle,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                                color = Color(0xFF8B93A7),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (selectedHeatmapDay in analytics.heatmapStartDayOfMonth..analytics.currentDayOfMonth &&
                            selectedHeatmapDay in 1..analytics.monthDaySpends.size
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))
                            val spendVal = analytics.monthDaySpends[selectedHeatmapDay - 1]
                            Text(
                                text = "Day $selectedHeatmapDay: ${formatMoney(spendVal, currency.symbol, false, hideBalance)}",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                color = primary,
                                maxLines = 1
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val rows = analytics.monthDaySpends.chunked(7)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        rows.forEachIndexed { rowIdx, weekDays ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                for (col in 0 until 7) {
                                    val dayNumber = rowIdx * 7 + col + 1
                                    if (col < weekDays.size) {
                                        val amount = weekDays[col]
                                        val isFuture = dayNumber > analytics.currentDayOfMonth
                                        val isBeforeStart = dayNumber < analytics.heatmapStartDayOfMonth
                                        val isUntracked = isFuture || isBeforeStart
                                        val isSelected = !isUntracked && selectedHeatmapDay == dayNumber
                                        val cellColor = when {
                                            isBeforeStart -> Color(0xFF12151E)
                                            isFuture -> Color(0xFF151822)
                                            amount <= 0.0 -> Color(0xFF10B981).copy(alpha = 0.28f)
                                            amount <= dailyPace -> primary.copy(alpha = 0.30f)
                                            amount <= dailyPace * 1.75 -> Color(0xFFF59E0B).copy(alpha = 0.42f)
                                            else -> Color(0xFFF43F5E).copy(alpha = 0.52f)
                                        }
                                        val borderColor = when {
                                            isSelected -> Color.White
                                            dayNumber == analytics.currentDayOfMonth -> primary
                                            isBeforeStart -> Color.White.copy(alpha = 0.03f)
                                            else -> Color.White.copy(alpha = 0.06f)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(30.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(cellColor)
                                                .border(
                                                    width = if (isSelected || dayNumber == analytics.currentDayOfMonth) 1.dp else 0.6.dp,
                                                    color = borderColor,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .clickable(enabled = !isUntracked) {
                                                    onHaptic()
                                                    selectedHeatmapDay = if (isSelected) -1 else dayNumber
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = dayNumber.toString(),
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                                color = if (isUntracked) Color(0xFF474E62) else Color.White
                                            )
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Zero", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp), color = Color(0xFF10B981))
                        Text("On Pace", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp), color = primary)
                        Text("High", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp), color = Color(0xFFF59E0B))
                        Text("Peak", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp), color = Color(0xFFF43F5E))
                        Text("Untracked", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp), color = Color(0xFF636B80))
                    }
                }
            }
        }

        // Cash Flow Balance Card
        item(key = "cashflow_ratio") {
            val maxFlow = max(1.0, max(analytics.totalIncome, analytics.totalExpense))
            val incomeRatio = (analytics.totalIncome / maxFlow).toFloat().coerceIn(0f, 1f)
            val expenseRatio = (analytics.totalExpense / maxFlow).toFloat().coerceIn(0f, 1f)

            SurfaceCard {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cash Flow Balance",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(primary.copy(alpha = 0.16f))
                                .padding(horizontal = 9.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Savings Rate: ${analytics.savingsRate}%",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = primary,
                                maxLines = 1
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ComparisonBarRow(
                        label = "Inflow",
                        valueText = formatMoney(analytics.totalIncome, currency.symbol, false, hideBalance),
                        fraction = incomeRatio * animProgress,
                        barColor = Color(0xFF10B981)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ComparisonBarRow(
                        label = "Outflow",
                        valueText = formatMoney(analytics.totalExpense, currency.symbol, false, hideBalance),
                        fraction = expenseRatio * animProgress,
                        barColor = Color(0xFFF43F5E)
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.75.dp)
                            .background(Color(0xFF1E222E))
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Average Daily Spend",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                            color = Color(0xFF8B93A7),
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = formatMoney(analytics.dailyAverage, currency.symbol, false, hideBalance),
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                            color = Color.White,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // Seven-Day Area Curve Chart
        item(key = "trend_curve") {
            SurfaceCard {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Seven-Day Spend Trend",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Daily Curve",
                            style = MaterialTheme.typography.labelSmall,
                            color = primary,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val maxVal = max(1.0, analytics.sevenDayPoints.maxOfOrNull { it.second } ?: 1.0)
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(108.dp)
                    ) {
                        val pts = analytics.sevenDayPoints
                        if (pts.size >= 2) {
                            val topPad = 12.dp.toPx()
                            val bottomPad = 10.dp.toPx()
                            val usableH = size.height - topPad - bottomPad
                            val stepX = size.width / (pts.size - 1)

                            for (g in 0..2) {
                                val gy = topPad + usableH * (g / 2f)
                                drawLine(
                                    color = Color.White.copy(alpha = 0.05f),
                                    start = Offset(0f, gy),
                                    end = Offset(size.width, gy),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }

                            val offsets = pts.mapIndexed { idx, (_, value) ->
                                val norm = (value / maxVal).toFloat().coerceIn(0f, 1f) * animProgress
                                Offset(
                                    x = idx * stepX,
                                    y = topPad + usableH * (1f - norm)
                                )
                            }

                            val linePath = Path().apply {
                                moveTo(offsets.first().x, offsets.first().y)
                                for (i in 0 until offsets.lastIndex) {
                                    val p0 = offsets[i]
                                    val p1 = offsets[i + 1]
                                    val midX = (p0.x + p1.x) / 2f
                                    cubicTo(midX, p0.y, midX, p1.y, p1.x, p1.y)
                                }
                            }

                            val fillPath = Path().apply {
                                addPath(linePath)
                                lineTo(offsets.last().x, size.height)
                                lineTo(offsets.first().x, size.height)
                                close()
                            }

                            drawPath(
                                path = fillPath,
                                brush = Brush.verticalGradient(
                                    colors = listOf(primary.copy(alpha = 0.25f), Color.Transparent)
                                )
                            )
                            drawPath(
                                path = linePath,
                                brush = Brush.horizontalGradient(listOf(primary, secondary)),
                                style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
                            )

                            offsets.forEachIndexed { idx, pt ->
                                if (pts[idx].second > 0.0) {
                                    drawCircle(
                                        color = Color(0xFF101219),
                                        radius = 4.5.dp.toPx(),
                                        center = pt
                                    )
                                    drawCircle(
                                        color = primary,
                                        radius = 3.dp.toPx(),
                                        center = pt
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        analytics.sevenDayPoints.forEach { (day, _) ->
                            Text(
                                text = day,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = Color(0xFF8B93A7)
                            )
                        }
                    }
                }
            }
        }

        // Category Allocation Ring
        item(key = "category_donut") {
            SurfaceCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Category Allocation",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (selectedCategory != null) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Reset",
                                style = MaterialTheme.typography.labelSmall,
                                color = primary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        onHaptic()
                                        selectedCategory = null
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.size(184.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val strokeW = 15.dp.toPx()
                            val inset = strokeW / 2f
                            val arcSize = Size(size.width - strokeW, size.height - strokeW)

                            if (analytics.categoryTotals.isEmpty() || analytics.totalExpense <= 0.0) {
                                drawArc(
                                    color = Color(0xFF1D212D),
                                    startAngle = 0f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    topLeft = Offset(inset, inset),
                                    size = arcSize,
                                    style = Stroke(width = strokeW, cap = StrokeCap.Round)
                                )
                            } else if (animProgress > 0.01f) {
                                var startAngle = -90f
                                val multiSlice = analytics.categoryTotals.size > 1
                                val gap = if (multiSlice) 3f else 0f
                                val sliceCap = if (multiSlice) StrokeCap.Butt else StrokeCap.Round
                                analytics.categoryTotals.forEach { (cat, value) ->
                                    val rawSweep = ((value / analytics.totalExpense) * 360.0).toFloat() * animProgress
                                    val effectiveGap = if (multiSlice) min(gap, rawSweep * 0.35f) else 0f
                                    val sweep = max(0.8f, rawSweep - effectiveGap)
                                    val dimmed = selectedCategory != null && selectedCategory != cat
                                    drawArc(
                                        color = if (dimmed) cat.color.copy(alpha = 0.22f) else cat.color,
                                        startAngle = startAngle + (effectiveGap / 2f),
                                        sweepAngle = sweep,
                                        useCenter = false,
                                        topLeft = Offset(inset, inset),
                                        size = arcSize,
                                        style = Stroke(width = strokeW, cap = sliceCap)
                                    )
                                    startAngle += rawSweep
                                }
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                            Text(
                                text = centerLabel,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = Color(0xFF8B93A7),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = formatMoney(centerValue, currency.symbol, false, hideBalance),
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "$centerShare% Share",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = activeEntry?.first?.color ?: primary,
                                maxLines = 1
                            )
                        }
                    }

                    if (analytics.categoryTotals.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            analytics.categoryTotals.forEach { (cat, _) ->
                                val isSelected = selectedCategory == cat
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            if (isSelected) cat.color.copy(alpha = 0.20f) else Color(0xFF161922)
                                        )
                                        .border(
                                            0.75.dp,
                                            if (isSelected) cat.color else Color(0xFF232836),
                                            RoundedCornerShape(10.dp)
                                        )
                                        .clickable {
                                            onHaptic()
                                            selectedCategory = if (isSelected) null else cat
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .clip(CircleShape)
                                            .background(cat.color)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = cat.title,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                        color = Color.White,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (analytics.categoryTotals.isNotEmpty()) {
            item(key = "category_breakdown") {
                SurfaceCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Category Breakdown",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        analytics.categoryTotals.forEach { (cat, value) ->
                            val share = if (analytics.totalExpense > 0.0) {
                                (value / analytics.totalExpense).toFloat().coerceIn(0f, 1f)
                            } else {
                                0f
                            }
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = cat.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium
                                        ),
                                        color = Color.White,
                                        modifier = Modifier.weight(1f),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${formatMoney(value, currency.symbol, true, hideBalance)} (${(share * 100).roundToInt()}%)",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp),
                                        color = cat.color,
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF1A1E29))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(share * animProgress)
                                            .fillMaxHeight()
                                            .clip(CircleShape)
                                            .background(cat.color)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ComparisonBarRow(
    label: String,
    valueText: String,
    fraction: Float,
    barColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                color = Color(0xFF9EA7BD),
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = valueText,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                color = Color.White,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1E29))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(barColor)
            )
        }
    }
}

@Composable
private fun GoalsScreen(
    goals: List<SavingsGoal>,
    liquidBalance: Double,
    impulseResistedTotal: Double,
    impulseResistedCount: Int,
    currency: CurrencyOption,
    theme: AccentTheme,
    primary: Color,
    secondary: Color,
    hideBalance: Boolean,
    animationsEnabled: Boolean,
    onAddGoal: () -> Unit,
    onEditGoal: (SavingsGoal) -> Unit,
    onDepositGoal: (SavingsGoal) -> Unit,
    onWithdrawGoal: (SavingsGoal) -> Unit,
    onDeleteGoal: (SavingsGoal) -> Unit
) {
    val totals = remember(goals) {
        val target = goals.sumOf { it.targetAmount }
        val saved = goals.sumOf { it.savedAmount }
        val ratio = if (target > 0.0) (saved / target).toFloat().coerceIn(0f, 1f) else 0f
        Triple(saved, target, ratio)
    }

    val animatedOverall by animateFloatAsState(
        targetValue = totals.third,
        animationSpec = if (animationsEnabled) tween(420, easing = FastOutSlowInEasing) else snap(),
        label = "goalsProgress"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 14.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(key = "goals_header") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Goal Reserve Vault",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Dedicated savings reserve separate from Liquid Balance.",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                        color = Color(0xFF8B93A7),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(primary.copy(alpha = 0.16f))
                        .border(0.8.dp, primary.copy(alpha = 0.45f), RoundedCornerShape(12.dp))
                        .clickable(onClick = onAddGoal)
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                ) {
                    PlusGlyph(color = primary, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Add Goal",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                        color = primary,
                        maxLines = 1
                    )
                }
            }
        }

        item(key = "reserve_banner") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(theme.cardTop, theme.cardMid, theme.cardBottom)
                        )
                    )
                    .border(0.8.dp, Color(0xFF232836), RoundedCornerShape(22.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SAVINGS RESERVE VAULT",
                            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.2.sp),
                            color = Color(0xFF9EA7BD),
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${(totals.third * 100).roundToInt()}% Funded",
                            style = MaterialTheme.typography.labelSmall,
                            color = primary,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatMoney(totals.first, currency.symbol, true, hideBalance),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Combined Target: ${formatMoney(totals.second, currency.symbol, false, hideBalance)} • Liquid Vault: ${formatMoney(liquidBalance, currency.symbol, false, hideBalance)}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                        color = Color(0xFF9EA7BD),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (impulseResistedTotal > 0.0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF10B981).copy(alpha = 0.14f))
                                .border(0.75.dp, Color(0xFF10B981).copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "Impulse Resisted ($impulseResistedCount): ${formatMoney(impulseResistedTotal, currency.symbol, false, hideBalance)}",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                color = Color(0xFF10B981),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.09f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(animatedOverall)
                                .fillMaxHeight()
                                .clip(CircleShape)
                                .background(Brush.horizontalGradient(listOf(primary, secondary)))
                        )
                    }
                }
            }
        }

        if (goals.isEmpty()) {
            item(key = "no_goals") {
                SurfaceCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp, horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No Savings Goals Yet",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Tap Add Goal above to create a dedicated milestone reserve separate from your Liquid Vault.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF8B93A7),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(items = goals, key = { it.id }) { goal ->
                MilestoneGoalCard(
                    goal = goal,
                    currency = currency,
                    hideBalance = hideBalance,
                    animationsEnabled = animationsEnabled,
                    onEdit = { onEditGoal(goal) },
                    onDeposit = { onDepositGoal(goal) },
                    onWithdraw = { onWithdrawGoal(goal) },
                    onDelete = { onDeleteGoal(goal) }
                )
            }
        }
    }
}

@Composable
private fun MilestoneGoalCard(
    goal: SavingsGoal,
    currency: CurrencyOption,
    hideBalance: Boolean,
    animationsEnabled: Boolean,
    onEdit: () -> Unit,
    onDeposit: () -> Unit,
    onWithdraw: () -> Unit,
    onDelete: () -> Unit
) {
    val accent = Color(goal.colorHex)
    val ratio = if (goal.targetAmount > 0.0) {
        (goal.savedAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f)
    } else {
        0f
    }
    val remaining = max(0.0, goal.targetAmount - goal.savedAmount)
    val animatedRatio by animateFloatAsState(
        targetValue = ratio,
        animationSpec = if (animationsEnabled) tween(380, easing = FastOutSlowInEasing) else snap(),
        label = "goalBar_${goal.id}"
    )

    SurfaceCard {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onEdit),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(accent)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = goal.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(accent.copy(alpha = 0.16f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${(ratio * 100).roundToInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = accent,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${formatMoney(goal.savedAmount, currency.symbol, hasFractionalCents(goal.savedAmount), hideBalance)} / ${formatMoney(goal.targetAmount, currency.symbol, hasFractionalCents(goal.targetAmount), hideBalance)}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color(0xFFD6DCEB),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (remaining <= 0.0) {
                        "Completed"
                    } else {
                        "${formatMoney(remaining, currency.symbol, false, hideBalance)} Left"
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = Color(0xFF8B93A7),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1A1E29))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedRatio)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(accent)
                )
                Canvas(modifier = Modifier.fillMaxSize()) {
                    listOf(0.25f, 0.50f, 0.75f).forEach { mark ->
                        drawLine(
                            color = Color(0xFF07080B).copy(alpha = 0.55f),
                            start = Offset(size.width * mark, 0f),
                            end = Offset(size.width * mark, size.height),
                            strokeWidth = 1.5.dp.toPx()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onDeposit,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accent.copy(alpha = 0.18f),
                        contentColor = accent
                    ),
                    border = BorderStroke(0.8.dp, accent.copy(alpha = 0.45f)),
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "Deposit",
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }

                OutlinedButton(
                    onClick = onWithdraw,
                    enabled = goal.savedAmount > 0.0,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        0.8.dp,
                        if (goal.savedAmount > 0.0) Color(0xFF2DD4BF).copy(alpha = 0.45f) else Color(0xFF222735)
                    ),
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "Withdraw",
                        color = if (goal.savedAmount > 0.0) Color(0xFF2DD4BF) else Color(0xFF555D70),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(0.8.dp, Color(0xFF262B38)),
                    contentPadding = PaddingValues(vertical = 9.dp)
                ) {
                    Text(
                        text = "Edit Goal",
                        color = Color.White,
                        maxLines = 1
                    )
                }

                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(0.8.dp, Color(0xFF262B38)),
                    contentPadding = PaddingValues(vertical = 9.dp)
                ) {
                    Text(
                        text = "Delete",
                        color = Color(0xFF9EA7BD),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    monthlyBudget: Double,
    currentLiquidBalance: Double,
    hourlyRate: Double,
    currency: CurrencyOption,
    activeTheme: AccentTheme,
    hapticsEnabled: Boolean,
    hideBalance: Boolean,
    animationsEnabled: Boolean,
    autoCheckUpdates: Boolean,
    isCheckingUpdates: Boolean,
    updateStatusText: String,
    availableUpdate: AppUpdateInfo?,
    devSettingsUnlocked: Boolean,
    includePreReleases: Boolean,
    primary: Color,
    hasCrashReport: Boolean,
    onSetBalance: () -> Unit,
    onEditBudget: () -> Unit,
    onEditHourlyRate: () -> Unit,
    onSelectCurrency: (CurrencyOption) -> Unit,
    onSelectTheme: (AccentTheme) -> Unit,
    onToggleAnimations: (Boolean) -> Unit,
    onToggleHaptics: (Boolean) -> Unit,
    onTogglePrivacy: (Boolean) -> Unit,
    onToggleAutoCheckUpdates: (Boolean) -> Unit,
    onCheckForUpdates: () -> Unit,
    onOpenUpdateDialog: () -> Unit,
    onToggleIncludePreReleases: (Boolean) -> Unit,
    onReplaySetup: () -> Unit,
    onHideDevSettings: () -> Unit,
    onTapVersionSecret: () -> Unit,
    onExportBackupJson: () -> Unit,
    onOpenImportBackup: () -> Unit,
    onExportCsv: () -> Unit,
    onOpenSupportHub: () -> Unit,
    onOpenDiagnosticLogs: () -> Unit,
    onOpenCredits: () -> Unit,
    onRequestClearAll: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 14.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "settings_header") {
            Column {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Text(
                    text = "Manage vaults, caps, themes, updates, backups, and diagnostics.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                    color = Color(0xFF8B93A7)
                )
            }
        }

        item(key = "budget_setting") {
            Column {
                SettingsSectionTitle("Vault, Budget & Currency")
                Spacer(modifier = Modifier.height(8.dp))
                SurfaceCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onSetBalance)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Liquid Vault Balance",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                    color = Color.White
                                )
                                Text(
                                    text = "Sync your exact spendable cash balance independently from Goals.",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = Color(0xFF8B93A7)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(primary.copy(alpha = 0.16f))
                                    .border(0.75.dp, primary.copy(alpha = 0.45f), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 11.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = formatMoney(
                                        currentLiquidBalance,
                                        currency.symbol,
                                        hasFractionalCents(currentLiquidBalance),
                                        hideBalance
                                    ),
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                                    color = primary,
                                    maxLines = 1
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(0.75.dp)
                                .background(Color(0xFF1D212D))
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onEditBudget)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Monthly Spending Cap",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                    color = Color.White
                                )
                                Text(
                                    text = "Controls your Safe-to-Spend daily limit and health score.",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = Color(0xFF8B93A7)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(primary.copy(alpha = 0.16f))
                                    .border(0.75.dp, primary.copy(alpha = 0.45f), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 11.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (monthlyBudget > 0.0) {
                                        formatMoney(
                                            monthlyBudget,
                                            currency.symbol,
                                            hasFractionalCents(monthlyBudget),
                                            hideBalance
                                        )
                                    } else {
                                        "Not Set"
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
                                    color = primary,
                                    maxLines = 1
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(0.75.dp)
                                .background(Color(0xFF1D212D))
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onEditHourlyRate)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Hourly Work Rate (Time-Cost)",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                    color = Color.White
                                )
                                Text(
                                    text = "Converts purchases into work hours in Simulator & Insights.",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = Color(0xFF8B93A7)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFF161922))
                                    .border(0.75.dp, Color(0xFF282E3E), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 11.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (hourlyRate > 0.0) {
                                        "${formatMoney(hourlyRate, currency.symbol, hasFractionalCents(hourlyRate), hideBalance)}/Hr"
                                    } else {
                                        "Off"
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.5.sp),
                                    color = if (hourlyRate > 0.0) Color(0xFFF59E0B) else Color(0xFF8B93A7),
                                    maxLines = 1
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(0.75.dp)
                                .background(Color(0xFF1D212D))
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CurrencyOption.entries.forEach { option ->
                                val selected = option == currency
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            if (selected) primary.copy(alpha = 0.20f) else Color(0xFF161922)
                                        )
                                        .border(
                                            0.8.dp,
                                            if (selected) primary else Color(0xFF242937),
                                            RoundedCornerShape(10.dp)
                                        )
                                        .clickable { onSelectCurrency(option) }
                                        .padding(horizontal = 13.dp, vertical = 9.dp)
                                ) {
                                    Text(
                                        text = "${option.symbol} ${option.code}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 13.sp,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                        ),
                                        color = if (selected) primary else Color(0xFFC7CEDF),
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item(key = "theme_setting") {
            Column {
                SettingsSectionTitle("Visual Theme")
                Spacer(modifier = Modifier.height(8.dp))
                SurfaceCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AccentTheme.entries.take(2).forEach { theme ->
                                ThemeChoiceCard(
                                    theme = theme,
                                    selected = theme == activeTheme,
                                    onClick = { onSelectTheme(theme) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            AccentTheme.entries.drop(2).forEach { theme ->
                                ThemeChoiceCard(
                                    theme = theme,
                                    selected = theme == activeTheme,
                                    onClick = { onSelectTheme(theme) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // GitHub Release Update Scanner
        item(key = "app_updates") {
            Column {
                SettingsSectionTitle("App Updates")
                Spacer(modifier = Modifier.height(8.dp))
                SurfaceCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !isCheckingUpdates) {
                                    if (availableUpdate != null) onOpenUpdateDialog() else onCheckForUpdates()
                                }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Scan GitHub for Updates",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                    color = Color.White
                                )
                                Text(
                                    text = updateStatusText,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = if (availableUpdate != null) Color(0xFF10B981) else Color(0xFF8B93A7)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(primary.copy(alpha = 0.16f))
                                    .border(0.75.dp, primary.copy(alpha = 0.45f), RoundedCornerShape(10.dp))
                                    .clickable(enabled = !isCheckingUpdates, onClick = onCheckForUpdates)
                                    .padding(horizontal = 11.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (isCheckingUpdates) "Scanning..." else "Check Now",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = primary,
                                    maxLines = 1
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(0.75.dp)
                                .background(Color(0xFF1D212D))
                        )

                        SettingsToggleRow(
                            title = "Auto-Check on Launch",
                            subtitle = if (devSettingsUnlocked && includePreReleases) {
                                "Checks GitHub Releases on launch (Pre-Release Channel Active)."
                            } else {
                                "Checks stable GitHub Releases only. Sends zero personal data."
                            },
                            checked = autoCheckUpdates,
                            accent = primary,
                            onCheckedChange = onToggleAutoCheckUpdates
                        )
                    }
                }
            }
        }

        // Support, Bug Reports & Diagnostic Logs Hub
        item(key = "support_diagnostics") {
            Column {
                SettingsSectionTitle("Support & Diagnostics")
                Spacer(modifier = Modifier.height(8.dp))
                SurfaceCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onOpenSupportHub)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Contact Developer & Bug Report",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                    color = Color.White
                                )
                                Text(
                                    text = "Send a message, feature request, or privacy-safe logs to $DEVELOPER_NAME.",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = Color(0xFF8B93A7)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(primary.copy(alpha = 0.16f))
                                    .border(0.75.dp, primary.copy(alpha = 0.45f), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 11.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Compose",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = primary,
                                    maxLines = 1
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(0.75.dp)
                                .background(Color(0xFF1D212D))
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onOpenDiagnosticLogs)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Diagnostic & Crash Logs",
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                    color = Color.White
                                )
                                Text(
                                    text = if (hasCrashReport) {
                                        "Last crash trace captured and ready for troubleshooting."
                                    } else {
                                        "Inspect local event logs without exposing financial amounts."
                                    },
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = if (hasCrashReport) Color(0xFFF59E0B) else Color(0xFF8B93A7)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "View",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (hasCrashReport) Color(0xFFF59E0B) else Color(0xFF9EA7BD),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Offline Backup & CSV Export
        item(key = "backup_export") {
            Column {
                SettingsSectionTitle("Offline Backup & Export")
                Spacer(modifier = Modifier.height(8.dp))
                SurfaceCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = onExportBackupJson,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(0.8.dp, Color(0xFF282D3B)),
                                contentPadding = PaddingValues(vertical = 11.dp, horizontal = 8.dp)
                            ) {
                                Text("Copy Backup", color = Color.White, maxLines = 1)
                            }
                            OutlinedButton(
                                onClick = onOpenImportBackup,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(0.8.dp, primary.copy(alpha = 0.45f)),
                                contentPadding = PaddingValues(vertical = 11.dp, horizontal = 8.dp)
                            ) {
                                Text("Restore JSON", color = primary, maxLines = 1)
                            }
                        }
                        OutlinedButton(
                            onClick = onExportCsv,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(0.8.dp, Color(0xFF282D3B)),
                            contentPadding = PaddingValues(vertical = 11.dp)
                        ) {
                            Text("Copy CSV Spreadsheet Statement", color = Color(0xFFD6DCEB), maxLines = 1)
                        }
                    }
                }
            }
        }

        item(key = "preferences_data") {
            Column {
                SettingsSectionTitle("Preferences & Storage")
                Spacer(modifier = Modifier.height(8.dp))
                SurfaceCard {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        SettingsToggleRow(
                            title = "Smooth Animations",
                            subtitle = "Fluid chart reveals and transitions. Disable for instant zero-motion snaps.",
                            checked = animationsEnabled,
                            accent = primary,
                            onCheckedChange = onToggleAnimations
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(0.75.dp)
                                .background(Color(0xFF1D212D))
                        )
                        SettingsToggleRow(
                            title = "Privacy Masking",
                            subtitle = "Hide financial amounts across the application.",
                            checked = hideBalance,
                            accent = primary,
                            onCheckedChange = onTogglePrivacy
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(0.75.dp)
                                .background(Color(0xFF1D212D))
                        )
                        SettingsToggleRow(
                            title = "Haptic Feedback",
                            subtitle = "Crisp tactile response on actions and tabs.",
                            checked = hapticsEnabled,
                            accent = primary,
                            onCheckedChange = onToggleHaptics
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(0.75.dp)
                                .background(Color(0xFF1D212D))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                        ) {
                            Button(
                                onClick = onRequestClearAll,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF43F5E).copy(alpha = 0.16f),
                                    contentColor = Color(0xFFFB7185)
                                ),
                                border = BorderStroke(0.8.dp, Color(0xFFF43F5E).copy(alpha = 0.45f)),
                                contentPadding = PaddingValues(vertical = 11.dp)
                            ) {
                                Text("Clear All Local Data", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // Secret Developer Settings (Unlocked via 7 Taps on Version)
        if (devSettingsUnlocked) {
            item(key = "developer_settings") {
                Column {
                    SettingsSectionTitle("Developer Settings")
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF141219))
                            .border(0.85.dp, Color(0xFFF59E0B).copy(alpha = 0.40f), RoundedCornerShape(20.dp))
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            SettingsToggleRow(
                                title = "Include Pre-Release Updates",
                                subtitle = "Allow the GitHub Update Scanner to detect beta and pre-release builds.",
                                checked = includePreReleases,
                                accent = Color(0xFFF59E0B),
                                onCheckedChange = onToggleIncludePreReleases
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .height(0.75.dp)
                                    .background(Color(0xFF231F29))
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = onReplaySetup,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(0.8.dp, Color(0xFFF59E0B).copy(alpha = 0.45f)),
                                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 8.dp)
                                ) {
                                    Text("Replay Setup", color = Color(0xFFF59E0B), maxLines = 1)
                                }
                                OutlinedButton(
                                    onClick = onHideDevSettings,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(0.8.dp, Color(0xFF2E3342)),
                                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 8.dp)
                                ) {
                                    Text("Lock Dev Menu", color = Color(0xFFB5BCD0), maxLines = 1)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Open-Source Credits & Version Registry Card
        item(key = "credits_section") {
            Column {
                SettingsSectionTitle("Credits & Release Notes")
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    activeTheme.cardTop.copy(alpha = 0.85f),
                                    Color(0xFF101219)
                                )
                            )
                        )
                        .border(0.85.dp, primary.copy(alpha = 0.32f), RoundedCornerShape(20.dp))
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = onTapVersionSecret),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FinVaultBrandMark(
                                primary = primary,
                                secondary = activeTheme.secondary,
                                modifier = Modifier.size(46.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = DEVELOPER_NAME,
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Creator & Lead Developer",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                    color = primary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "$APP_BRAND_NAME v$APP_VERSION_NAME (Build $APP_VERSION_CODE) • $APP_BUILD_TRACK",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                                    color = Color(0xFF8B93A7),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Black.copy(alpha = 0.28f))
                                .border(0.75.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                .clickable(onClick = onOpenCredits)
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "View Credits, What's New & MIT License",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                color = Color(0xFFD6DCEB),
                                modifier = Modifier.weight(1f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Open",
                                style = MaterialTheme.typography.labelSmall,
                                color = primary,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionTitle(text: String) {
    Text(
        text = text.uppercase(Locale.US),
        style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 11.sp,
            letterSpacing = 1.1.sp
        ),
        color = Color(0xFF8B93A7)
    )
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    accent: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                color = Color.White
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFF8B93A7)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF07080B),
                checkedTrackColor = accent,
                uncheckedThumbColor = Color(0xFF9EA7BD),
                uncheckedTrackColor = Color(0xFF191D28),
                uncheckedBorderColor = Color(0xFF2C3242)
            )
        )
    }
}

@Composable
private fun ThemeChoiceCard(
    theme: AccentTheme,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) theme.primary.copy(alpha = 0.18f) else Color(0xFF161922)
            )
            .border(
                0.85.dp,
                if (selected) theme.primary else Color(0xFF242937),
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(11.dp)
                .clip(CircleShape)
                .background(theme.primary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = theme.title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 13.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            ),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun DockedBottomBar(
    selectedTab: Int,
    accent: Color,
    onSelectTab: (Int) -> Unit
) {
    val tabs = listOf("Vault", "Insights", "Goals", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0B0D12))
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.75.dp)
                .background(Color(0xFF1E222E))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, label ->
                val isSelected = index == selectedTab
                val itemColor = if (isSelected) accent else Color(0xFF7B8399)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onSelectTab(index) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(28.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) accent.copy(alpha = 0.16f) else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        when (index) {
                            0 -> VaultGlyph(color = itemColor, modifier = Modifier.size(18.dp))
                            1 -> InsightsGlyph(color = itemColor, modifier = Modifier.size(18.dp))
                            2 -> TargetGlyph(color = itemColor, modifier = Modifier.size(18.dp))
                            else -> SlidersGlyph(color = itemColor, modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            letterSpacing = 0.2.sp
                        ),
                        color = if (isSelected) Color.White else Color(0xFF7B8399),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactAndReportDialog(
    primary: Color,
    diagnosticBundle: String,
    onSendEmail: (String, String) -> Unit,
    onOpenGitHubIssue: (String, String) -> Unit,
    onShareReport: (String, String) -> Unit,
    onCopyReport: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var reportType by remember { mutableStateOf(SupportReportType.BUG) }
    var messageText by remember { mutableStateOf("") }
    var attachDiagnostics by remember { mutableStateOf(true) }

    fun composeSubject(): String {
        return "[$APP_BRAND_NAME v$APP_VERSION_NAME] ${reportType.title}"
    }

    fun composeBody(): String {
        val sb = StringBuilder()
        sb.appendLine("Report Type: ${reportType.title}")
        sb.appendLine()
        sb.appendLine("Message / Details:")
        sb.appendLine(messageText.trim().ifEmpty { "(Describe the issue, suggestion, or message here)" })
        if (attachDiagnostics) {
            sb.appendLine()
            sb.appendLine(diagnosticBundle)
        }
        return sb.toString()
    }

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Contact & Support",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Reach $DEVELOPER_NAME directly with bug reports, feature ideas, or privacy-safe logs.",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFF8B93A7)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SupportReportType.entries.forEach { option ->
                val selected = reportType == option
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selected) primary.copy(alpha = 0.20f) else Color(0xFF151822))
                        .border(
                            0.85.dp,
                            if (selected) primary else Color(0xFF242937),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { reportType = option }
                        .padding(horizontal = 11.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = option.title,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = if (selected) Color.White else Color(0xFF9EA7BD),
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Message / Steps to Reproduce",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF9EA7BD)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF161A24))
                .border(0.85.dp, Color(0xFF282E3E), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            if (messageText.isEmpty()) {
                Text(
                    text = "Describe what happened or what feature you would like added...",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                    color = Color(0xFF5D657A)
                )
            }
            BasicTextField(
                value = messageText,
                onValueChange = { messageText = it },
                cursorBrush = SolidColor(primary),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 13.5.sp,
                    color = Color.White,
                    lineHeight = 19.sp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF151822))
                .border(0.75.dp, Color(0xFF232836), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Attach Diagnostic & Crash Logs",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
                Text(
                    text = "Includes device model, API level & logs. Zero financial values.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = Color(0xFF8B93A7)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = attachDiagnostics,
                onCheckedChange = { attachDiagnostics = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF07080B),
                    checkedTrackColor = primary,
                    uncheckedThumbColor = Color(0xFF9EA7BD),
                    uncheckedTrackColor = Color(0xFF191D28),
                    uncheckedBorderColor = Color(0xFF2C3242)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSendEmail(composeSubject(), composeBody()) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                contentColor = Color(0xFF07080B)
            )
        ) {
            Text("Send via Email", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onOpenGitHubIssue(composeSubject(), composeBody()) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140)),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
            ) {
                Text("GitHub Issue", color = Color.White, maxLines = 1)
            }
            OutlinedButton(
                onClick = { onShareReport(composeSubject(), composeBody()) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140)),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
            ) {
                Text("Share Logs", color = Color.White, maxLines = 1)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { onCopyReport("${composeSubject()}\n\n${composeBody()}") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, primary.copy(alpha = 0.45f)),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
            ) {
                Text("Copy Report", color = primary, maxLines = 1)
            }
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140)),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
            ) {
                Text("Close", color = Color(0xFFB5BCD0), maxLines = 1)
            }
        }
    }
}

@Composable
private fun DiagnosticLogsDialog(
    primary: Color,
    logs: List<String>,
    lastCrash: String?,
    onCopyBundle: () -> Unit,
    onClearLogs: () -> Unit,
    onDismiss: () -> Unit
) {
    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Diagnostic & Crash Logs",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Privacy-safe system timeline recorded locally on your device.",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFF8B93A7)
        )

        if (lastCrash != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "LAST CAPTURED CRASH TRACE",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFFF59E0B)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1A1412))
                    .border(0.8.dp, Color(0xFFF59E0B).copy(alpha = 0.45f), RoundedCornerShape(12.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = lastCrash.take(900),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.5.sp,
                        lineHeight = 15.sp
                    ),
                    color = Color(0xFFFDE68A)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "RECENT EVENTS (${logs.size})",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF8B93A7)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0B0D13))
                .border(0.75.dp, Color(0xFF202532), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            if (logs.isEmpty()) {
                Text(
                    text = "No diagnostic events recorded yet.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = Color(0xFF7B8399)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    logs.take(18).forEach { entry ->
                        Text(
                            text = entry,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.5.sp,
                                lineHeight = 14.sp
                            ),
                            color = Color(0xFFAAB3C7)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onClearLogs,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140)),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
            ) {
                Text("Clear Logs", color = Color(0xFFFB7185), maxLines = 1)
            }
            Button(
                onClick = onCopyBundle,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                ),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
            ) {
                Text("Copy Bundle", fontWeight = FontWeight.Bold, maxLines = 1)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.85.dp, Color(0xFF2B3140))
        ) {
            Text("Close", color = Color(0xFFB5BCD0))
        }
    }
}

@Composable
private fun PurchaseImpactSimulatorDialog(
    transactions: List<TransactionItem>,
    goals: List<SavingsGoal>,
    openingBalance: Double,
    monthlyBudget: Double,
    hourlyRate: Double,
    currency: CurrencyOption,
    primary: Color,
    onDismiss: () -> Unit,
    onRecordPurchase: (String, Double, Category) -> Unit,
    onSaveInstead: (Long, Double, String) -> Unit,
    onQuarantinePurchase: (String, Double, Category, Long?, Int) -> Unit
) {
    val expenseCategories = remember {
        Category.entries.filter { !it.isIncomeCategory && it != Category.GOAL_TRANSFER }
    }
    var itemTitle by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(Category.SHOPPING) }
    var selectedGoalId by remember { mutableStateOf(goals.firstOrNull()?.id) }
    var quarantineHours by remember { mutableIntStateOf(72) }

    val totalIncome = remember(transactions) {
        transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    }
    val totalOutflow = remember(transactions) {
        transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    }
    val monthExpense = remember(transactions) {
        transactions
            .filter {
                it.type == TransactionType.EXPENSE &&
                    it.category != Category.GOAL_TRANSFER &&
                    isTimestampInCurrentMonth(it.timestamp)
            }
            .sumOf { it.amount }
    }

    val currentBalance = openingBalance + totalIncome - totalOutflow
    val price = (priceText.toDoubleOrNull() ?: 0.0).let {
        if (it.isFinite() && it >= 0.0) it.coerceAtMost(MAX_FINANCE_AMOUNT) else 0.0
    }
    val timeCostLabel = formatWorkHours(price, hourlyRate)
    val projectedBalance = currentBalance - price
    val daysLeft = daysRemainingInCurrentMonth()
    val hasBudget = monthlyBudget > 0.0
    val projectedMonthExpense = monthExpense + price
    val projectedCapRatio = if (hasBudget) projectedMonthExpense / monthlyBudget else 0.0
    val projectedCapPctLabel = if (hasBudget) formatCompactPercent(projectedCapRatio) else "0%"
    val projectedDailySafe = if (hasBudget) max(0.0, (monthlyBudget - projectedMonthExpense) / daysLeft) else 0.0

    val verdictLabel: String
    val verdictColor: Color
    when {
        price <= 0.0 -> {
            verdictLabel = "Enter a Price"
            verdictColor = primary
        }
        projectedBalance < 0.0 -> {
            verdictLabel = "Over Liquid Balance"
            verdictColor = Color(0xFFF43F5E)
        }
        hasBudget && projectedMonthExpense > monthlyBudget -> {
            verdictLabel = "Exceeds Monthly Cap"
            verdictColor = Color(0xFFF43F5E)
        }
        hasBudget && projectedMonthExpense > monthlyBudget * 0.85 -> {
            verdictLabel = "Tight Margin"
            verdictColor = Color(0xFFF59E0B)
        }
        else -> {
            verdictLabel = "Safe Purchase"
            verdictColor = Color(0xFF10B981)
        }
    }

    val targetGoal = goals.firstOrNull { it.id == selectedGoalId } ?: goals.firstOrNull()
    val goalImpactPctLabel = if (targetGoal != null && targetGoal.targetAmount > 0.0 && price > 0.0) {
        formatCompactPercent(price / targetGoal.targetAmount)
    } else {
        null
    }

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Purchase Impact Simulator",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Preview how a planned purchase affects your balance, cap, and goals before spending.",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFF8B93A7)
        )

        Spacer(modifier = Modifier.height(14.dp))

        VaultTextField(
            label = "Planned Item",
            value = itemTitle,
            placeholder = "Example: Mechanical Keyboard",
            accent = primary,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            onValueChange = { itemTitle = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        VaultTextField(
            label = "Estimated Price (${currency.symbol})",
            value = priceText,
            placeholder = "0.00",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input -> priceText = sanitizeDecimalInput(input) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            expenseCategories.forEach { cat ->
                val isSelected = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) cat.color.copy(alpha = 0.20f) else Color(0xFF151822))
                        .border(
                            0.8.dp,
                            if (isSelected) cat.color else Color(0xFF242937),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = cat.title,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = if (isSelected) Color.White else Color(0xFF9EA7BD),
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF141824))
                .border(0.85.dp, verdictColor.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VERDICT",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = Color(0xFF8B93A7)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = verdictLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = verdictColor,
                    maxLines = 1
                )
            }

            CreditDetailRow(
                label = "Projected Balance",
                value = formatMoney(projectedBalance, currency.symbol),
                highlight = if (projectedBalance < 0.0) Color(0xFFF43F5E) else Color.White
            )
            if (hasBudget) {
                CreditDetailRow(
                    label = "Projected Cap",
                    value = "$projectedCapPctLabel Used",
                    highlight = if (projectedCapRatio > 1.0) Color(0xFFF43F5E) else primary
                )
                CreditDetailRow(
                    label = "New Daily Limit",
                    value = "${formatMoney(projectedDailySafe, currency.symbol, false)} / Day"
                )
            }
            if (targetGoal != null && goalImpactPctLabel != null) {
                CreditDetailRow(
                    label = "Goal Impact",
                    value = "$goalImpactPctLabel of ${targetGoal.title}",
                    highlight = Color(targetGoal.colorHex)
                )
            }
            if (timeCostLabel != null) {
                CreditDetailRow(
                    label = "Time-Cost",
                    value = "$timeCostLabel of Work",
                    highlight = Color(0xFFF59E0B)
                )
            }
        }

        if (goals.size > 1) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Compare Against Goal",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF9EA7BD)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                goals.forEach { g ->
                    val gAccent = Color(g.colorHex)
                    val active = g.id == targetGoal?.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (active) gAccent.copy(alpha = 0.20f) else Color(0xFF151822))
                            .border(
                                0.8.dp,
                                if (active) gAccent else Color(0xFF242937),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedGoalId = g.id }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = g.title,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = if (active) Color.White else Color(0xFF9EA7BD),
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (price > 0.0) {
            Text(
                text = "Cooling-Off Quarantine Duration",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF9EA7BD)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(Pair(24, "24 Hours"), Pair(72, "72 Hours"), Pair(168, "7 Days")).forEach { (hrs, lbl) ->
                    val active = quarantineHours == hrs
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (active) Color(0xFFF59E0B).copy(alpha = 0.20f) else Color(0xFF151822))
                            .border(
                                0.8.dp,
                                if (active) Color(0xFFF59E0B) else Color(0xFF242937),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { quarantineHours = hrs }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = lbl,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = if (active) Color.White else Color(0xFF9EA7BD),
                            maxLines = 1
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = {
                    val label = itemTitle.trim().ifEmpty { selectedCategory.title }
                    onQuarantinePurchase(label, price, selectedCategory, targetGoal?.id, quarantineHours)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFFF59E0B).copy(alpha = 0.50f))
            ) {
                Text(
                    text = "Quarantine for ${if (quarantineHours == 168) "7 Days" else "${quarantineHours}h"}",
                    color = Color(0xFFF59E0B),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (price > 0.0 && targetGoal != null) {
            Button(
                onClick = {
                    val label = itemTitle.trim().ifEmpty { "Planned Item" }
                    onSaveInstead(targetGoal.id, price, label)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF10B981).copy(alpha = 0.20f),
                    contentColor = Color(0xFF10B981)
                ),
                border = BorderStroke(0.85.dp, Color(0xFF10B981).copy(alpha = 0.50f))
            ) {
                Text(
                    text = "Skip Purchase & Save to ${targetGoal.title}",
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Close", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    if (price > 0.0) {
                        val label = itemTitle.trim().ifEmpty { selectedCategory.title }
                        onRecordPurchase(label, price, selectedCategory)
                    }
                },
                enabled = price > 0.0,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Buy & Record", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun AddQuickPresetDialog(
    currency: CurrencyOption,
    primary: Color,
    onDismiss: () -> Unit,
    onSave: (String, Double, TransactionType, Category) -> Unit
) {
    var type by remember { mutableStateOf(TransactionType.EXPENSE) }
    val availableCategories = remember(type) {
        if (type == TransactionType.EXPENSE) {
            Category.entries.filter { !it.isIncomeCategory }
        } else {
            Category.entries.filter { it.isIncomeCategory }
        }
    }
    var selectedCategory by remember(type) { mutableStateOf(availableCategories.first()) }
    var titleText by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Create Quick Preset",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Log recurring expenses or allowances in one tap from the Vault screen.",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFF8B93A7)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF151822))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(
                Pair(TransactionType.EXPENSE, "Expense"),
                Pair(TransactionType.INCOME, "Income")
            ).forEach { (option, label) ->
                val active = type == option
                val chipColor = if (option == TransactionType.EXPENSE) Color(0xFFF43F5E) else Color(0xFF10B981)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(9.dp))
                        .background(if (active) chipColor.copy(alpha = 0.20f) else Color.Transparent)
                        .border(
                            0.8.dp,
                            if (active) chipColor.copy(alpha = 0.55f) else Color.Transparent,
                            RoundedCornerShape(9.dp)
                        )
                        .clickable { type = option }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = if (active) chipColor else Color(0xFF8B93A7)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        VaultTextField(
            label = "Preset Name",
            value = titleText,
            placeholder = "Example: Daily Commute",
            accent = primary,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            onValueChange = {
                titleText = it
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        VaultTextField(
            label = "Fixed Amount (${currency.symbol})",
            value = amountText,
            placeholder = "0.00",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                amountText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            availableCategories.forEach { cat ->
                val isSelected = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) cat.color.copy(alpha = 0.20f) else Color(0xFF151822))
                        .border(
                            0.85.dp,
                            if (isSelected) cat.color else Color(0xFF242937),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = cat.title,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = if (isSelected) Color.White else Color(0xFF9EA7BD),
                        maxLines = 1
                    )
                }
            }
        }

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val value = amountText.toDoubleOrNull()
                    if (titleText.isBlank()) {
                        errorMessage = "Please enter a preset name."
                    } else if (value == null || !value.isFinite() || value <= 0.0 || value > MAX_FINANCE_AMOUNT) {
                        errorMessage = "Please enter a valid amount greater than zero."
                    } else {
                        onSave(titleText.trim(), value, type, selectedCategory)
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Save Preset", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ImportBackupDialog(
    primary: Color,
    onDismiss: () -> Unit,
    onImport: (BackupPayload) -> Unit
) {
    val context = LocalContext.current
    var jsonText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val parsedPreview = remember(jsonText) {
        if (jsonText.isBlank()) null else VaultStorage.parseFullBackupJson(jsonText)
    }

    VaultDialogShell(onDismiss = onDismiss) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Restore JSON Backup",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                color = Color.White,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Paste",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.5.sp),
                color = primary,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(primary.copy(alpha = 0.16f))
                    .border(0.75.dp, primary.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
                    .clickable {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = clipboard.primaryClip
                        val pasted = if (clip != null && clip.itemCount > 0) {
                            clip.getItemAt(0).coerceToText(context)?.toString().orEmpty()
                        } else {
                            ""
                        }
                        if (pasted.isBlank()) {
                            errorMessage = "Clipboard is empty."
                        } else {
                            jsonText = pasted
                            errorMessage = null
                        }
                    }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Paste a JSON backup previously exported from $APP_BRAND_NAME to restore your ledger and goals.",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFF8B93A7)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(148.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF161A24))
                .border(
                    0.85.dp,
                    if (parsedPreview != null) Color(0xFF10B981).copy(alpha = 0.55f) else Color(0xFF282E3E),
                    RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            if (jsonText.isEmpty()) {
                Text(
                    text = "Paste $APP_BRAND_NAME JSON Backup Here...",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    ),
                    color = Color(0xFF5D657A)
                )
            }
            BasicTextField(
                value = jsonText,
                onValueChange = {
                    jsonText = it
                    errorMessage = null
                },
                cursorBrush = SolidColor(primary),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = Color.White
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }

        if (parsedPreview != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Verified $APP_BRAND_NAME Backup: ${parsedPreview.transactions.size} Entries, ${parsedPreview.goals.size} Goals, ${parsedPreview.presets.size} Presets (${parsedPreview.currency.code})",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                color = Color(0xFF10B981)
            )
        }

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val parsed = VaultStorage.parseFullBackupJson(jsonText)
                    if (parsed == null) {
                        errorMessage = "Invalid $APP_BRAND_NAME JSON backup. Ensure it was exported from $APP_BRAND_NAME."
                    } else {
                        onImport(parsed)
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Restore Data", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun CreditsAndLicenseDialog(
    primary: Color,
    secondary: Color,
    onTapVersionSecret: () -> Unit,
    onOpenChangelog: () -> Unit,
    onOpenGitHub: () -> Unit,
    onCopyAttribution: () -> Unit,
    onDismiss: () -> Unit
) {
    val tierColor = when {
        APP_BUILD_TRACK.contains("Major") -> secondary
        APP_BUILD_TRACK.contains("Patch") -> Color(0xFF10B981)
        else -> primary
    }

    VaultDialogShell(onDismiss = onDismiss) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onTapVersionSecret),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FinVaultBrandMark(
                primary = primary,
                secondary = secondary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$APP_BRAND_NAME v$APP_VERSION_NAME",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Open-Source Personal Finance",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF151822))
                .border(0.75.dp, Color(0xFF242937), RoundedCornerShape(14.dp))
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            CreditDetailRow(label = "Author", value = DEVELOPER_NAME, highlight = primary)
            CreditDetailRow(label = "Role", value = "Creator & Lead Developer")
            Box(modifier = Modifier.fillMaxWidth().clickable(onClick = onTapVersionSecret)) {
                CreditDetailRow(label = "Version", value = "v$APP_VERSION_NAME ($APP_BUILD_TRACK)")
            }
            CreditDetailRow(label = "Stack", value = "Kotlin • Compose • Material 3")
            CreditDetailRow(label = "License", value = "MIT Open-Source License")
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "LATEST RELEASE NOTES",
            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.0.sp),
            color = Color(0xFF8B93A7)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF141721))
                .border(0.75.dp, Color(0xFF222735), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "v$APP_VERSION_NAME (Build $APP_VERSION_CODE)",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(tierColor.copy(alpha = 0.16f))
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = APP_BUILD_TRACK,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = tierColor,
                        maxLines = 1
                    )
                }
            }

            LatestReleaseHighlights.forEach { section ->
                val secColor = Color(section.accentHex)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = section.label.uppercase(Locale.US),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            letterSpacing = 0.8.sp
                        ),
                        color = secColor
                    )
                    section.items.forEach { bullet ->
                        Text(
                            text = "• $bullet",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 11.5.sp,
                                lineHeight = 16.5.sp
                            ),
                            color = Color(0xFFB8C0D4)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(primary.copy(alpha = 0.12f))
                .border(0.8.dp, primary.copy(alpha = 0.42f), RoundedCornerShape(12.dp))
                .clickable(onClick = onOpenChangelog)
                .padding(horizontal = 14.dp, vertical = 11.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "View Full Changelog on GitHub",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Read all earlier versions in CHANGELOG.md",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = Color(0xFF9EA7BD),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            ChevronRightGlyph(color = primary, modifier = Modifier.size(13.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "MIT OPEN-SOURCE LICENSE",
            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.0.sp),
            color = Color(0xFF8B93A7)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0B0D13))
                .border(0.75.dp, Color(0xFF202532), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "Copyright (c) 2026 $DEVELOPER_NAME\n\nPermission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files, to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, subject to the inclusion of the above copyright notice.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                ),
                color = Color(0xFF9EA7BD)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onOpenGitHub,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.85.dp, primary.copy(alpha = 0.45f))
        ) {
            Text("View Repository on GitHub", color = primary, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onCopyAttribution,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Copy Credit", color = Color.White)
            }
            Button(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Close", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Fixed: Label takes intrinsic width only so values never truncate prematurely
@Composable
private fun CreditDetailRow(
    label: String,
    value: String,
    highlight: Color = Color.White
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = Color(0xFF8B93A7),
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = highlight,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TransactionEditorDialog(
    existingItem: TransactionItem?,
    initialType: TransactionType,
    goals: List<SavingsGoal>,
    currency: CurrencyOption,
    primary: Color,
    onDismiss: () -> Unit,
    onDelete: (() -> Unit)?,
    onDuplicate: (() -> Unit)?,
    onSave: (String, Double, TransactionType, Category, String, Long, Long?) -> Unit
) {
    var type by remember(existingItem?.id, initialType) {
        mutableStateOf(existingItem?.type ?: initialType)
    }
    val availableCategories = remember(type) {
        if (type == TransactionType.EXPENSE) {
            Category.entries.filter { !it.isIncomeCategory }
        } else {
            Category.entries.filter { it.isIncomeCategory }
        }
    }
    var selectedCategory by remember(existingItem?.id, type) {
        mutableStateOf(
            if (existingItem != null && existingItem.type == type) {
                existingItem.category
            } else {
                availableCategories.first()
            }
        )
    }
    var selectedDepositGoalId by remember(existingItem?.id, type) { mutableStateOf<Long?>(null) }
    var titleText by remember(existingItem?.id) { mutableStateOf(existingItem?.title ?: "") }
    var amountText by remember(existingItem?.id) {
        mutableStateOf(
            if (existingItem != null) formatEditableAmount(existingItem.amount) else ""
        )
    }
    var noteText by remember(existingItem?.id) { mutableStateOf(existingItem?.note ?: "") }
    // Date selector: -1 = keep original (when editing), 0 = Today, 1 = Yesterday, 2 = 2 Days Ago
    var datePreset by remember(existingItem?.id) {
        mutableIntStateOf(if (existingItem != null) -1 else 0)
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val resolvedTimestamp = remember(datePreset, existingItem?.id) {
        when (datePreset) {
            -1 -> existingItem?.timestamp ?: System.currentTimeMillis()
            0 -> System.currentTimeMillis()
            1 -> calendarDaysAgo(1).timeInMillis
            else -> calendarDaysAgo(2).timeInMillis
        }
    }

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = when {
                existingItem != null -> "Edit Transaction"
                type == TransactionType.EXPENSE && selectedDepositGoalId != null -> "Deposit to Savings Goal"
                type == TransactionType.EXPENSE -> "Record Expense"
                else -> "Record Income"
            },
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF151822))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(
                Pair(TransactionType.EXPENSE, "Expense"),
                Pair(TransactionType.INCOME, "Income")
            ).forEach { (option, label) ->
                val active = type == option
                val chipColor = if (option == TransactionType.EXPENSE) Color(0xFFF43F5E) else Color(0xFF10B981)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(9.dp))
                        .background(if (active) chipColor.copy(alpha = 0.20f) else Color.Transparent)
                        .border(
                            0.8.dp,
                            if (active) chipColor.copy(alpha = 0.55f) else Color.Transparent,
                            RoundedCornerShape(9.dp)
                        )
                        .clickable { type = option }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = if (active) chipColor else Color(0xFF8B93A7)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        VaultTextField(
            label = "Title",
            value = titleText,
            placeholder = if (type == TransactionType.EXPENSE) "Example: Dinner Meal" else "Example: Weekly Allowance",
            accent = primary,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            onValueChange = {
                titleText = it
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        VaultTextField(
            label = "Amount (${currency.symbol})",
            value = amountText,
            placeholder = "0.00",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                amountText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(50, 100, 250, 500).forEach { stepAmount ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color(0xFF151822))
                        .border(0.75.dp, Color(0xFF242937), RoundedCornerShape(9.dp))
                        .clickable {
                            val current = amountText.toDoubleOrNull() ?: 0.0
                            amountText = formatEditableAmount((current + stepAmount).coerceAtMost(MAX_FINANCE_AMOUNT))
                            errorMessage = null
                        }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${currency.symbol}$stepAmount",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                        color = Color(0xFFC7CEDF),
                        maxLines = 1
                    )
                }
            }
        }

        if (existingItem == null && type == TransactionType.EXPENSE && goals.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Deposit to Savings Goal (Optional)",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF9EA7BD)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val noneActive = selectedDepositGoalId == null
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (noneActive) primary.copy(alpha = 0.18f) else Color(0xFF151822))
                        .border(
                            0.8.dp,
                            if (noneActive) primary else Color(0xFF242937),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedDepositGoalId = null }
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = "Regular Expense",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = if (noneActive) Color.White else Color(0xFF9EA7BD),
                        maxLines = 1
                    )
                }
                goals.forEach { goal ->
                    val gAccent = Color(goal.colorHex)
                    val active = selectedDepositGoalId == goal.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (active) gAccent.copy(alpha = 0.20f) else Color(0xFF151822))
                            .border(
                                0.85.dp,
                                if (active) gAccent else Color(0xFF242937),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                selectedDepositGoalId = goal.id
                                selectedCategory = Category.GOAL_TRANSFER
                                if (titleText.isBlank()) {
                                    titleText = "Deposit to ${goal.title}"
                                }
                            }
                            .padding(horizontal = 10.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = "+ ${goal.title}",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = if (active) Color.White else gAccent,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Date (${formatDateLabel(resolvedTimestamp)})",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF9EA7BD)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val dateChoices = buildList {
                if (existingItem != null) add(Pair(-1, "Keep Original"))
                add(Pair(0, "Today"))
                add(Pair(1, "Yesterday"))
                add(Pair(2, "2 Days Ago"))
            }
            dateChoices.forEach { (key, label) ->
                val active = datePreset == key
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (active) primary.copy(alpha = 0.20f) else Color(0xFF151822))
                        .border(
                            0.8.dp,
                            if (active) primary else Color(0xFF242937),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { datePreset = key }
                        .padding(horizontal = 11.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = if (active) Color.White else Color(0xFF9EA7BD),
                        maxLines = 1
                    )
                }
            }
        }

        if (selectedDepositGoalId == null) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Category",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF9EA7BD)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                availableCategories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) cat.color.copy(alpha = 0.20f) else Color(0xFF151822))
                            .border(
                                0.85.dp,
                                if (isSelected) cat.color else Color(0xFF242937),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = cat.title,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = if (isSelected) Color.White else Color(0xFF9EA7BD),
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        VaultTextField(
            label = "Note (Optional)",
            value = noteText,
            placeholder = "Add a short detail",
            accent = primary,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            onValueChange = { noteText = it }
        )

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val parsedAmount = amountText.toDoubleOrNull()
                    if (titleText.isBlank()) {
                        errorMessage = "Please enter a transaction title."
                    } else if (parsedAmount == null || !parsedAmount.isFinite() || parsedAmount <= 0.0 || parsedAmount > MAX_FINANCE_AMOUNT) {
                        errorMessage = "Please enter a valid amount greater than zero."
                    } else {
                        onSave(
                            titleText.trim(),
                            parsedAmount,
                            type,
                            selectedCategory,
                            noteText.trim(),
                            resolvedTimestamp,
                            if (existingItem == null && type == TransactionType.EXPENSE) selectedDepositGoalId else null
                        )
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text(
                    text = when {
                        existingItem != null -> "Update"
                        selectedDepositGoalId != null -> "Deposit"
                        else -> "Save Entry"
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (onDuplicate != null || onDelete != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (onDuplicate != null) {
                    OutlinedButton(
                        onClick = onDuplicate,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.85.dp, primary.copy(alpha = 0.45f)),
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
                    ) {
                        Text("Duplicate", color = primary, maxLines = 1)
                    }
                }
                if (onDelete != null) {
                    OutlinedButton(
                        onClick = onDelete,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.85.dp, Color(0xFFF43F5E).copy(alpha = 0.45f)),
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
                    ) {
                        Text("Delete", color = Color(0xFFFB7185), maxLines = 1)
                    }
                }
            }
        }
    }
}

@Composable
private fun GoalEditorDialog(
    existingGoal: SavingsGoal?,
    currency: CurrencyOption,
    primary: Color,
    onDismiss: () -> Unit,
    onSave: (String, Double, Double, Long) -> Unit
) {
    val paletteColors = listOf(0xFF38BDF8L, 0xFF10B981L, 0xFF818CF8L, 0xFFF59E0BL, 0xFFFB7185L)
    var titleText by remember(existingGoal?.id) { mutableStateOf(existingGoal?.title ?: "") }
    var targetText by remember(existingGoal?.id) {
        mutableStateOf(
            if (existingGoal != null) formatEditableAmount(existingGoal.targetAmount) else ""
        )
    }
    var savedText by remember(existingGoal?.id) {
        mutableStateOf(
            if (existingGoal != null) formatEditableAmount(existingGoal.savedAmount) else ""
        )
    }
    var selectedColorHex by remember(existingGoal?.id) {
        mutableStateOf(existingGoal?.colorHex ?: paletteColors.first())
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = if (existingGoal != null) "Edit Savings Goal" else "Create Savings Goal",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(14.dp))

        VaultTextField(
            label = "Goal Name",
            value = titleText,
            placeholder = "Example: New Laptop Fund",
            accent = primary,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            onValueChange = {
                titleText = it
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        VaultTextField(
            label = "Target Amount (${currency.symbol})",
            value = targetText,
            placeholder = "10000",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                targetText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        VaultTextField(
            label = if (existingGoal != null) {
                "Saved Amount (${currency.symbol})"
            } else {
                "Starting Deposit (${currency.symbol}, Optional)"
            },
            value = savedText,
            placeholder = "0",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                savedText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Accent Color",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF9EA7BD)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            paletteColors.forEach { hex ->
                val color = Color(hex)
                val selected = hex == selectedColorHex
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (selected) 2.5.dp else 0.dp,
                            color = if (selected) Color.White else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable { selectedColorHex = hex }
                )
            }
        }

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val target = targetText.toDoubleOrNull()
                    val parsedSaved = if (savedText.isBlank()) 0.0 else savedText.toDoubleOrNull()
                    if (titleText.isBlank()) {
                        errorMessage = "Please enter a name for your goal."
                    } else if (target == null || !target.isFinite() || target <= 0.0 || target > MAX_FINANCE_AMOUNT) {
                        errorMessage = "Please enter a valid target amount greater than zero."
                    } else if (parsedSaved == null || !parsedSaved.isFinite() || parsedSaved < 0.0 || parsedSaved > MAX_FINANCE_AMOUNT) {
                        errorMessage = "Please enter a valid saved amount."
                    } else {
                        onSave(
                            titleText.trim(),
                            target,
                            parsedSaved,
                            selectedColorHex
                        )
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text(if (existingGoal != null) "Save Changes" else "Create Goal", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun DepositGoalDialog(
    goal: SavingsGoal,
    liquidBalance: Double,
    currency: CurrencyOption,
    primary: Color,
    hideBalance: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Double, Boolean) -> Unit
) {
    var amountText by remember(goal.id) { mutableStateOf("") }
    var transferFromLiquid by remember(goal.id) { mutableStateOf(liquidBalance > 0.0) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val accent = Color(goal.colorHex)

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Deposit to Goal",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${goal.title} • Saved ${formatMoney(goal.savedAmount, currency.symbol, hasFractionalCents(goal.savedAmount), hideBalance)}",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
            color = accent
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(100, 250, 500, 1000).forEach { preset ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(accent.copy(alpha = 0.14f))
                        .border(0.8.dp, accent.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                        .clickable {
                            amountText = preset.toString()
                            errorMessage = null
                        }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${currency.symbol}$preset",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = accent,
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        VaultTextField(
            label = "Deposit Amount (${currency.symbol})",
            value = amountText,
            placeholder = "Enter amount",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                amountText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF151822))
                .border(0.75.dp, Color(0xFF232836), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Transfer from Liquid Vault",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
                Text(
                    text = "Deducts from Liquid Balance (${formatMoney(liquidBalance, currency.symbol, false, hideBalance)}). Turn off for external savings.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = Color(0xFF8B93A7)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = transferFromLiquid,
                onCheckedChange = { transferFromLiquid = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF07080B),
                    checkedTrackColor = accent,
                    uncheckedThumbColor = Color(0xFF9EA7BD),
                    uncheckedTrackColor = Color(0xFF191D28),
                    uncheckedBorderColor = Color(0xFF2C3242)
                )
            )
        }

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val value = amountText.toDoubleOrNull()
                    if (value == null || !value.isFinite() || value <= 0.0 || value > MAX_FINANCE_AMOUNT) {
                        errorMessage = "Please enter a valid deposit amount."
                    } else {
                        onConfirm(value, transferFromLiquid)
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accent,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Confirm", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun WithdrawGoalDialog(
    goal: SavingsGoal,
    currency: CurrencyOption,
    primary: Color,
    hideBalance: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Double, Boolean) -> Unit
) {
    var amountText by remember(goal.id) { mutableStateOf("") }
    var returnToLiquid by remember(goal.id) { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val tealAccent = Color(0xFF2DD4BF)

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Withdraw from Goal",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${goal.title} • Available ${formatMoney(goal.savedAmount, currency.symbol, hasFractionalCents(goal.savedAmount), hideBalance)}",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
            color = tealAccent
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Pair("25%", 0.25),
                Pair("50%", 0.50),
                Pair("All", 1.0)
            ).forEach { (label, fraction) ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(tealAccent.copy(alpha = 0.14f))
                        .border(0.8.dp, tealAccent.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                        .clickable {
                            amountText = formatEditableAmount(goal.savedAmount * fraction)
                            errorMessage = null
                        }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.5.sp),
                        color = tealAccent,
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        VaultTextField(
            label = "Withdraw Amount (${currency.symbol})",
            value = amountText,
            placeholder = "Enter amount",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                amountText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF151822))
                .border(0.75.dp, Color(0xFF232836), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Return to Liquid Vault",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
                Text(
                    text = "Adds withdrawn amount back into your spendable Liquid Balance.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = Color(0xFF8B93A7)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = returnToLiquid,
                onCheckedChange = { returnToLiquid = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF07080B),
                    checkedTrackColor = tealAccent,
                    uncheckedThumbColor = Color(0xFF9EA7BD),
                    uncheckedTrackColor = Color(0xFF191D28),
                    uncheckedBorderColor = Color(0xFF2C3242)
                )
            )
        }

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val value = amountText.toDoubleOrNull()
                    if (value == null || !value.isFinite() || value <= 0.0) {
                        errorMessage = "Please enter a valid withdrawal amount."
                    } else if (value > goal.savedAmount + 0.005) {
                        errorMessage = "Amount exceeds this goal's saved reserve."
                    } else {
                        onConfirm(min(value, goal.savedAmount), returnToLiquid)
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = tealAccent,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Withdraw", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SetBalanceDialog(
    currentBalance: Double,
    currency: CurrencyOption,
    primary: Color,
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    var isNegative by remember(currentBalance) { mutableStateOf(currentBalance < 0.0) }
    var balanceText by remember(currentBalance) {
        mutableStateOf(formatEditableAmount(abs(currentBalance)))
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Set Liquid Vault Balance",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Sync your exact spendable cash or e-wallet balance without altering your transaction history or Goal Reserve Vault.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF9EA7BD)
        )
        Spacer(modifier = Modifier.height(14.dp))

        VaultTextField(
            label = "Current Spendable Balance (${currency.symbol})",
            value = balanceText,
            placeholder = "0.00",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                balanceText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF151822))
                .padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf(Pair(false, "Positive (+)"), Pair(true, "Deficit (-)")).forEach { (neg, label) ->
                val active = isNegative == neg
                val chipColor = if (neg) Color(0xFFF43F5E) else Color(0xFF10B981)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (active) chipColor.copy(alpha = 0.18f) else Color.Transparent)
                        .clickable { isNegative = neg }
                        .padding(vertical = 7.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (active) chipColor else Color(0xFF8B93A7)
                    )
                }
            }
        }

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val value = if (balanceText.isBlank()) 0.0 else balanceText.toDoubleOrNull()
                    if (value == null || !value.isFinite() || value < 0.0 || value > MAX_FINANCE_AMOUNT) {
                        errorMessage = "Please enter a valid balance amount."
                    } else {
                        onSave(if (isNegative && value > 0.0) -value else value)
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Sync Balance", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun EditHourlyRateDialog(
    currentRate: Double,
    currency: CurrencyOption,
    primary: Color,
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    var rateText by remember(currentRate) { mutableStateOf(formatEditableAmount(currentRate)) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Hourly Work Rate",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Powers $APP_BRAND_NAME's exclusive Time-Cost Converter—showing how many hours of work a purchase costs. Enter 0 to disable.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF9EA7BD)
        )
        Spacer(modifier = Modifier.height(14.dp))

        VaultTextField(
            label = "Hourly Rate (${currency.symbol} / Hour)",
            value = rateText,
            placeholder = "Example: 150",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                rateText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val value = if (rateText.isBlank()) 0.0 else rateText.toDoubleOrNull()
                    if (value == null || !value.isFinite() || value < 0.0 || value > MAX_FINANCE_AMOUNT) {
                        errorMessage = "Please enter 0 or a valid hourly rate."
                    } else {
                        onSave(value)
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Save Rate", fontWeight = FontWeight.Bold)
            }
        }
    }
}

private sealed class MarkdownBlock {
    data class Heading(val level: Int, val text: String) : MarkdownBlock()
    data class Paragraph(val text: String) : MarkdownBlock()
    data class Bullet(val indent: Int, val marker: String, val text: String) : MarkdownBlock()
    data class Numbered(val indent: Int, val number: String, val text: String) : MarkdownBlock()
    data class Quote(val text: String) : MarkdownBlock()
    data class Callout(val kind: String, val lines: List<String>) : MarkdownBlock()
    data class CodeBlock(val lines: List<String>) : MarkdownBlock()
    data object Divider : MarkdownBlock()
}

private fun parseGitHubMarkdownBlocks(rawMarkdown: String): List<MarkdownBlock> {
    val cleaned = rawMarkdown
        .replace("\r\n", "\n")
        .replace('\r', '\n')
        .replace(Regex("<!--.*?-->", setOf(RegexOption.DOT_MATCHES_ALL)), "")
        .replace(Regex("<br\\s*/?>", RegexOption.IGNORE_CASE), "\n")

    val rawLines = cleaned.split('\n')
    val blocks = mutableListOf<MarkdownBlock>()
    val headingRegex = Regex("^\\s{0,3}(#{1,6})\\s+(.+?)\\s*#*\\s*$")
    val dividerRegex = Regex("^\\s{0,3}([-*_])(?:\\s*\\1){2,}\\s*$")
    val checkboxRegex = Regex("^(\\s*)[-*+]\\s+\\[([ xX])]\\s+(.+)$")
    val bulletRegex = Regex("^(\\s*)[-*+]\\s+(.+)$")
    val numberedRegex = Regex("^(\\s*)(\\d+)\\.\\s+(.+)$")
    val calloutRegex = Regex("^\\s*>\\s*\\[!([A-Za-z]+)]\\s*(.*)$")

    var i = 0
    while (i < rawLines.size) {
        val line = rawLines[i]
        val trimmed = line.trim()
        if (trimmed.isEmpty()) {
            i += 1
            continue
        }

        // Fenced code blocks (``` or ~~~)
        if (trimmed.startsWith("```") || trimmed.startsWith("~~~")) {
            val fence = trimmed.take(3)
            val codeLines = mutableListOf<String>()
            i += 1
            while (i < rawLines.size && !rawLines[i].trim().startsWith(fence)) {
                codeLines.add(rawLines[i])
                i += 1
            }
            if (i < rawLines.size) i += 1
            if (codeLines.isNotEmpty()) {
                blocks.add(MarkdownBlock.CodeBlock(codeLines))
            }
            continue
        }

        // Horizontal divider (---, ***, ___)
        if (dividerRegex.matches(line)) {
            blocks.add(MarkdownBlock.Divider)
            i += 1
            continue
        }

        // Headings (# .. ######)
        val headingMatch = headingRegex.matchEntire(line)
        if (headingMatch != null) {
            val level = headingMatch.groupValues[1].length
            val text = headingMatch.groupValues[2].trim()
            if (text.isNotEmpty()) {
                blocks.add(MarkdownBlock.Heading(level, text))
            }
            i += 1
            continue
        }

        // GitHub Alert Callouts (> [!IMPORTANT], > [!NOTE], > [!WARNING], etc.)
        val calloutMatch = calloutRegex.matchEntire(line)
        if (calloutMatch != null) {
            val kind = calloutMatch.groupValues[1].uppercase(Locale.US)
            val bodyLines = mutableListOf<String>()
            val firstRest = calloutMatch.groupValues[2].trim()
            if (firstRest.isNotEmpty()) bodyLines.add(firstRest)
            i += 1
            while (i < rawLines.size && rawLines[i].trimStart().startsWith(">")) {
                val qLine = rawLines[i].trimStart().removePrefix(">").trim()
                if (qLine.isNotEmpty()) bodyLines.add(qLine)
                i += 1
            }
            blocks.add(MarkdownBlock.Callout(kind, bodyLines))
            continue
        }

        // Standard Blockquotes (> ...)
        if (trimmed.startsWith(">")) {
            val quoteParts = mutableListOf<String>()
            while (i < rawLines.size && rawLines[i].trimStart().startsWith(">")) {
                val qLine = rawLines[i].trimStart().removePrefix(">").trim()
                if (qLine.isNotEmpty()) quoteParts.add(qLine)
                i += 1
            }
            if (quoteParts.isNotEmpty()) {
                blocks.add(MarkdownBlock.Quote(quoteParts.joinToString("\n")))
            }
            continue
        }

        // Task checkbox list items (- [x] / - [ ])
        val checkboxMatch = checkboxRegex.matchEntire(line)
        if (checkboxMatch != null) {
            val indent = (checkboxMatch.groupValues[1].length / 2).coerceIn(0, 2)
            val checked = checkboxMatch.groupValues[2].equals("x", ignoreCase = true)
            val text = checkboxMatch.groupValues[3].trim()
            blocks.add(MarkdownBlock.Bullet(indent, if (checked) "✓" else "○", text))
            i += 1
            continue
        }

        // Unordered bullet list items (- , * , + )
        val bulletMatch = bulletRegex.matchEntire(line)
        if (bulletMatch != null) {
            val indent = (bulletMatch.groupValues[1].length / 2).coerceIn(0, 2)
            val text = bulletMatch.groupValues[2].trim()
            blocks.add(MarkdownBlock.Bullet(indent, if (indent == 0) "•" else "◦", text))
            i += 1
            continue
        }

        // Numbered list items (1. , 2. )
        val numberedMatch = numberedRegex.matchEntire(line)
        if (numberedMatch != null) {
            val indent = (numberedMatch.groupValues[1].length / 2).coerceIn(0, 2)
            val num = numberedMatch.groupValues[2]
            val text = numberedMatch.groupValues[3].trim()
            blocks.add(MarkdownBlock.Numbered(indent, num, text))
            i += 1
            continue
        }

        // Normal paragraph text
        blocks.add(MarkdownBlock.Paragraph(trimmed))
        i += 1
    }

    return blocks
}

private fun AnnotatedString.Builder.appendMarkdownInline(
    text: String,
    primary: Color,
    boldColor: Color
) {
    var i = 0
    val len = text.length
    while (i < len) {
        val ch = text[i]

        // Escaped Markdown symbol (\*, \#, \_, \`, etc.)
        if (ch == '\\' && i + 1 < len && text[i + 1] in "*_`~#[]()>+-!") {
            append(text[i + 1])
            i += 2
            continue
        }

        // Inline code (`code`)
        if (ch == '`') {
            val end = text.indexOf('`', i + 1)
            if (end > i + 1) {
                withStyle(
                    SpanStyle(
                        fontFamily = FontFamily.Monospace,
                        color = primary,
                        background = Color(0xFF171E2E),
                        fontSize = 11.sp
                    )
                ) {
                    append(" ${text.substring(i + 1, end)} ")
                }
                i = end + 1
                continue
            }
        }

        // Markdown Image (![alt](url)) -> show alt text cleanly or skip raw syntax
        if (ch == '!' && i + 1 < len && text[i + 1] == '[') {
            val closeBracket = text.indexOf(']', i + 2)
            if (closeBracket != -1 && closeBracket + 1 < len && text[closeBracket + 1] == '(') {
                val closeParen = text.indexOf(')', closeBracket + 2)
                if (closeParen != -1) {
                    val alt = text.substring(i + 2, closeBracket).trim()
                    if (alt.isNotEmpty()) append(alt)
                    i = closeParen + 1
                    continue
                }
            }
        }

        // Markdown Link ([Label](https://...)) -> render styled Label without raw brackets/URL
        if (ch == '[') {
            val closeBracket = text.indexOf(']', i + 1)
            if (closeBracket > i + 1 && closeBracket + 1 < len && text[closeBracket + 1] == '(') {
                val closeParen = text.indexOf(')', closeBracket + 2)
                if (closeParen != -1) {
                    val label = text.substring(i + 1, closeBracket)
                    withStyle(
                        SpanStyle(
                            color = primary,
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        appendMarkdownInline(label, primary, primary)
                    }
                    i = closeParen + 1
                    continue
                }
            }
        }

        // Bold + Italic (***text***)
        if (text.startsWith("***", i)) {
            val end = text.indexOf("***", i + 3)
            if (end > i + 3) {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = boldColor
                    )
                ) {
                    appendMarkdownInline(text.substring(i + 3, end), primary, boldColor)
                }
                i = end + 3
                continue
            }
        }

        // Bold (**text**)
        if (text.startsWith("**", i)) {
            val end = text.indexOf("**", i + 2)
            if (end > i + 2) {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = boldColor)) {
                    appendMarkdownInline(text.substring(i + 2, end), primary, boldColor)
                }
                i = end + 2
                continue
            }
        }

        // Bold (__text__) at word boundary
        if (text.startsWith("__", i) && (i == 0 || !text[i - 1].isLetterOrDigit())) {
            val end = text.indexOf("__", i + 2)
            if (end > i + 2 && (end + 2 == len || !text[end + 2].isLetterOrDigit())) {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = boldColor)) {
                    appendMarkdownInline(text.substring(i + 2, end), primary, boldColor)
                }
                i = end + 2
                continue
            }
        }

        // Strikethrough (~~text~~)
        if (text.startsWith("~~", i)) {
            val end = text.indexOf("~~", i + 2)
            if (end > i + 2) {
                withStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.LineThrough,
                        color = Color(0xFF7B8399)
                    )
                ) {
                    appendMarkdownInline(text.substring(i + 2, end), primary, Color(0xFF7B8399))
                }
                i = end + 2
                continue
            }
        }

        // Italic (*text*)
        if (ch == '*' && i + 1 < len && text[i + 1] != ' ' && text[i + 1] != '*') {
            val end = text.indexOf('*', i + 1)
            if (end > i + 1 && text[end - 1] != ' ') {
                withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = Color(0xFFD6DCEB))) {
                    appendMarkdownInline(text.substring(i + 1, end), primary, boldColor)
                }
                i = end + 1
                continue
            }
        }

        // Italic (_text_) at word boundary (preserves snake_case identifiers)
        if (ch == '_' && (i == 0 || !text[i - 1].isLetterOrDigit()) && i + 1 < len && text[i + 1] != ' ' && text[i + 1] != '_') {
            val end = text.indexOf('_', i + 1)
            if (end > i + 1 && text[end - 1] != ' ' && (end + 1 == len || !text[end + 1].isLetterOrDigit())) {
                withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = Color(0xFFD6DCEB))) {
                    appendMarkdownInline(text.substring(i + 1, end), primary, boldColor)
                }
                i = end + 1
                continue
            }
        }

        append(ch)
        i += 1
    }
}

private fun parseMarkdownInline(
    text: String,
    primary: Color,
    boldColor: Color = Color.White
): AnnotatedString {
    return buildAnnotatedString {
        appendMarkdownInline(text, primary, boldColor)
    }
}

@Composable
private fun GitHubMarkdownNotes(
    markdown: String,
    primary: Color,
    modifier: Modifier = Modifier
) {
    val blocks = remember(markdown) { parseGitHubMarkdownBlocks(markdown) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        blocks.forEach { block ->
            when (block) {
                is MarkdownBlock.Heading -> {
                    val fontSize = when (block.level) {
                        1 -> 15.5.sp
                        2 -> 14.5.sp
                        3 -> 13.5.sp
                        else -> 12.5.sp
                    }
                    val headingColor = when (block.level) {
                        1, 2 -> Color.White
                        3 -> Color(0xFFF1F5F9)
                        else -> primary
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = if (block.level <= 3) 4.dp else 2.dp)
                    ) {
                        Text(
                            text = parseMarkdownInline(block.text, primary, headingColor),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = fontSize,
                                fontWeight = FontWeight.Bold,
                                lineHeight = (fontSize.value + 5f).sp
                            ),
                            color = headingColor
                        )
                        if (block.level <= 2) {
                            Spacer(modifier = Modifier.height(5.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.75.dp)
                                    .background(Color(0xFF232836))
                            )
                        }
                    }
                }

                is MarkdownBlock.Bullet -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = (block.indent * 12).dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = block.marker,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 18.sp
                            ),
                            color = if (block.marker == "✓") Color(0xFF10B981) else primary,
                            modifier = Modifier.width(14.dp)
                        )
                        Text(
                            text = parseMarkdownInline(block.text, primary),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            ),
                            color = Color(0xFFC7CEDF),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                is MarkdownBlock.Numbered -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = (block.indent * 12).dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "${block.number}.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 18.sp
                            ),
                            color = primary,
                            modifier = Modifier.width(20.dp)
                        )
                        Text(
                            text = parseMarkdownInline(block.text, primary),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            ),
                            color = Color(0xFFC7CEDF),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                is MarkdownBlock.Callout -> {
                    val calloutColor = when (block.kind) {
                        "WARNING", "CAUTION" -> Color(0xFFF59E0B)
                        "IMPORTANT" -> Color(0xFF818CF8)
                        "TIP" -> Color(0xFF10B981)
                        else -> primary
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(calloutColor.copy(alpha = 0.10f))
                            .border(0.75.dp, calloutColor.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = block.kind,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            ),
                            color = calloutColor
                        )
                        block.lines.forEach { cLine ->
                            Text(
                                text = parseMarkdownInline(cLine, primary),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 11.5.sp,
                                    lineHeight = 17.sp
                                ),
                                color = Color(0xFFD6DCEB)
                            )
                        }
                    }
                }

                is MarkdownBlock.Quote -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF121622))
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(2.5.dp)
                                .height(16.dp)
                                .clip(CircleShape)
                                .background(primary.copy(alpha = 0.70f))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = parseMarkdownInline(block.text, primary),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 11.5.sp,
                                lineHeight = 17.sp
                            ),
                            color = Color(0xFFA8B1C7),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                is MarkdownBlock.CodeBlock -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF06080D))
                            .border(0.75.dp, Color(0xFF1D2230), RoundedCornerShape(8.dp))
                            .horizontalScroll(rememberScrollState())
                            .padding(10.dp)
                    ) {
                        Text(
                            text = block.lines.joinToString("\n"),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            ),
                            color = Color(0xFFC7CEDF)
                        )
                    }
                }

                is MarkdownBlock.Divider -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .height(0.75.dp)
                            .background(Color(0xFF232836))
                    )
                }

                is MarkdownBlock.Paragraph -> {
                    Text(
                        text = parseMarkdownInline(block.text, primary),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        ),
                        color = Color(0xFFC7CEDF)
                    )
                }
            }
        }
    }
}

@Composable
private fun UpdateAvailableDialog(
    update: AppUpdateInfo,
    primary: Color,
    onDownloadApk: (String) -> Unit,
    onOpenReleasePage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFF10131B))
                    .border(0.85.dp, Color(0xFF252A38), RoundedCornerShape(22.dp))
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Update Available",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (update.isPreRelease) Color(0xFFF59E0B).copy(alpha = 0.18f) else primary.copy(alpha = 0.18f)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (update.isPreRelease) "Pre-Release" else "Stable",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = if (update.isPreRelease) Color(0xFFF59E0B) else primary,
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${update.releaseName} (${update.tagName})${if (update.publishedAt.isNotBlank()) " • ${update.publishedAt}" else ""}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = primary
                )
                Text(
                    text = "Installed: v$APP_VERSION_NAME (Build $APP_VERSION_CODE)",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = Color(0xFF8B93A7)
                )

                if (update.notes.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 320.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF0B0D13))
                            .border(0.75.dp, Color(0xFF202532), RoundedCornerShape(12.dp))
                            .verticalScroll(rememberScrollState())
                            .padding(14.dp)
                    ) {
                        GitHubMarkdownNotes(
                            markdown = update.notes,
                            primary = primary,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (update.apkDownloadUrl != null) {
                    Button(
                        onClick = { onDownloadApk(update.apkDownloadUrl) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            contentColor = Color(0xFF07080B)
                        )
                    ) {
                        Text("Download APK", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.85.dp, Color(0xFF2B3140))
                    ) {
                        Text("Later", color = Color(0xFFB5BCD0))
                    }
                    OutlinedButton(
                        onClick = { onOpenReleasePage(update.htmlUrl) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.85.dp, primary.copy(alpha = 0.45f))
                    ) {
                        Text("Open GitHub", color = primary, maxLines = 1)
                    }
                }
            }
        }
    }
}

@Composable
private fun EditBudgetDialog(
    currentBudget: Double,
    currency: CurrencyOption,
    primary: Color,
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    var budgetText by remember(currentBudget) {
        mutableStateOf(formatEditableAmount(currentBudget))
    }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = "Monthly Spending Cap",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Set your target spending limit for the month, or enter 0 to disable the cap.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF9EA7BD)
        )
        Spacer(modifier = Modifier.height(14.dp))

        VaultTextField(
            label = "Monthly Cap (${currency.symbol})",
            value = budgetText,
            placeholder = "Example: 5000",
            accent = primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onValueChange = { input ->
                budgetText = sanitizeDecimalInput(input)
                errorMessage = null
            }
        )

        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = Color(0xFFFB7185)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = {
                    val value = if (budgetText.isBlank()) 0.0 else budgetText.toDoubleOrNull()
                    if (value == null || !value.isFinite() || value < 0.0 || value > MAX_FINANCE_AMOUNT) {
                        errorMessage = "Please enter 0 or a valid positive limit."
                    } else {
                        onSave(value)
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color(0xFF07080B)
                )
            ) {
                Text("Save Cap", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ConfirmActionDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    VaultDialogShell(onDismiss = onDismiss) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF9EA7BD)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.85.dp, Color(0xFF2B3140))
            ) {
                Text("Cancel", color = Color(0xFFB5BCD0))
            }
            Button(
                onClick = onConfirm,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF43F5E),
                    contentColor = Color.White
                )
            ) {
                Text(confirmLabel, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun VaultDialogShell(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFF10131B))
                    .border(0.85.dp, Color(0xFF252A38), RoundedCornerShape(22.dp))
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun VaultTextField(
    label: String,
    value: String,
    placeholder: String,
    accent: Color,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF9EA7BD)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF161A24))
                .border(0.85.dp, Color(0xFF282E3E), RoundedCornerShape(12.dp))
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF5D657A)
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = keyboardOptions,
                cursorBrush = SolidColor(accent),
                textStyle = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SurfaceCard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF101219))
            .border(0.75.dp, Color(0xFF1E222E), RoundedCornerShape(20.dp)),
        content = content
    )
}

@Composable
private fun GoalMilestoneCelebrationOverlay(
    event: GoalCelebrationEvent,
    currency: CurrencyOption,
    primary: Color,
    secondary: Color,
    hideBalance: Boolean,
    animationsEnabled: Boolean,
    onDismiss: () -> Unit
) {
    val goalAccent = Color(event.accentColorHex)
    val burstProgress = remember(event.id, animationsEnabled) {
        Animatable(if (animationsEnabled) 0f else 1f)
    }
    val cardScale = remember(event.id, animationsEnabled) {
        Animatable(if (animationsEnabled) 0.84f else 1f)
    }

    LaunchedEffect(event.id, animationsEnabled) {
        if (animationsEnabled) {
            launch {
                cardScale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
            }
            launch {
                burstProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1900, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    val haloPulse = if (animationsEnabled) {
        val transition = rememberInfiniteTransition(label = "goalHalo")
        val p by transition.animateFloat(
            initialValue = 0.90f,
            targetValue = 1.12f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "goalHaloScale"
        )
        p
    } else {
        1f
    }

    val confettiPalette = remember(primary, secondary, goalAccent) {
        listOf(
            goalAccent,
            primary,
            secondary,
            Color(0xFF10B981),
            Color(0xFFF59E0B),
            Color(0xFFEAF7FF)
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC05070B))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss
                ),
            contentAlignment = Alignment.Center
        ) {
            if (animationsEnabled) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val t = burstProgress.value
                    val fade = (1f - ((t - 0.65f) / 0.35f).coerceIn(0f, 1f))
                    if (fade > 0.01f) {
                        val originX = size.width * 0.5f
                        val originY = size.height * 0.42f
                        val maxRadius = min(size.width, size.height) * 0.56f
                        val particleCount = 38
                        for (i in 0 until particleCount) {
                            val angleDeg = (i * 360.0 / particleCount) + ((i % 3) * 7.0)
                            val rad = angleDeg * PI / 180.0
                            val speedFactor = 0.52f + ((i * 37) % 100) / 150f
                            val dist = maxRadius * speedFactor * t
                            val gravity = (size.height * 0.24f) * t * t
                            val px = originX + (cos(rad) * dist).toFloat()
                            val py = originY + (sin(rad) * dist).toFloat() + gravity
                            val pColor = confettiPalette[i % confettiPalette.size].copy(alpha = fade * 0.92f)
                            val w = (6f + (i % 4) * 2.5f).dp.toPx()
                            val h = (3.5f + (i % 3) * 2f).dp.toPx()
                            val spin = (i * 42f) + (t * 420f * if (i % 2 == 0) 1f else -1f)
                            if (i % 4 == 0) {
                                drawCircle(
                                    color = pColor,
                                    radius = (3.2f + (i % 3)).dp.toPx(),
                                    center = Offset(px, py)
                                )
                            } else {
                                rotate(degrees = spin, pivot = Offset(px, py)) {
                                    drawRoundRect(
                                        color = pColor,
                                        topLeft = Offset(px - w / 2f, py - h / 2f),
                                        size = Size(w, h),
                                        cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .graphicsLayer {
                        scaleX = cardScale.value
                        scaleY = cardScale.value
                    }
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF151C2C), Color(0xFF0E121B))
                        )
                    )
                    .border(
                        width = 1.2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                goalAccent.copy(alpha = 0.85f),
                                Color(0xFF10B981).copy(alpha = 0.65f),
                                primary.copy(alpha = 0.75f)
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
                    .padding(horizontal = 22.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(86.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val r = max(1f, (size.minDimension * 0.46f) * haloPulse)
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    goalAccent.copy(alpha = 0.36f),
                                    Color(0xFF10B981).copy(alpha = 0.14f),
                                    Color.Transparent
                                ),
                                center = center,
                                radius = r
                            ),
                            center = center,
                            radius = r
                        )
                        drawCircle(
                            color = goalAccent.copy(alpha = 0.55f),
                            radius = size.minDimension * 0.40f,
                            style = Stroke(width = 1.6.dp.toPx())
                        )
                    }
                    FinVaultBrandMark(
                        primary = goalAccent,
                        secondary = Color(0xFF10B981),
                        modifier = Modifier.size(54.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF10B981).copy(alpha = 0.18f))
                        .border(0.75.dp, Color(0xFF10B981).copy(alpha = 0.50f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "MILESTONE ACHIEVED",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            letterSpacing = 1.1.sp
                        ),
                        color = Color(0xFF10B981),
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Congratulations!",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 25.sp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "You reached 100% of your \"${event.goalTitle}\" savings target.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.5.sp),
                    color = Color(0xFFC7CEDF),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.Black.copy(alpha = 0.30f))
                        .border(0.75.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "GOAL RESERVE FUNDED",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                        color = Color(0xFF8B93A7)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${formatMoney(event.savedAmount, currency.symbol, hasFractionalCents(event.savedAmount), hideBalance)} / ${formatMoney(event.targetAmount, currency.symbol, hasFractionalCents(event.targetAmount), hideBalance)}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = goalAccent,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = goalAccent,
                        contentColor = Color(0xFF07080B)
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text("Continue", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun CapWarningFloatingBanner(
    event: CapWarningEvent,
    currency: CurrencyOption,
    hideBalance: Boolean,
    animationsEnabled: Boolean,
    onDismiss: () -> Unit
) {
    val accent = if (event.isExceeded) Color(0xFFF43F5E) else Color(0xFFF59E0B)
    val shakeOffset = remember(event.id, animationsEnabled) { Animatable(0f) }

    LaunchedEffect(event.id, animationsEnabled) {
        if (animationsEnabled) {
            listOf(-8f, 8f, -6f, 6f, -3f, 3f, 0f).forEach { target ->
                shakeOffset.animateTo(
                    targetValue = target,
                    animationSpec = tween(durationMillis = 52, easing = LinearEasing)
                )
            }
        }
    }

    val pulseAlpha = if (animationsEnabled) {
        val transition = rememberInfiniteTransition(label = "bannerWarningPulse")
        val a by transition.animateFloat(
            initialValue = 0.45f,
            targetValue = 0.95f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 750, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bannerPulseAlpha"
        )
        a
    } else {
        0.75f
    }

    val pctLabel = formatCompactPercent(event.ratio)
    val diffAmount = abs(event.monthlyBudget - event.monthExpense)
    val detailText = if (event.isExceeded) {
        "$pctLabel used • Over cap by ${formatMoney(diffAmount, currency.symbol, false, hideBalance)}"
    } else {
        "$pctLabel used • ${formatMoney(diffAmount, currency.symbol, false, hideBalance)} remaining this month"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { translationX = shakeOffset.value }
            .clip(RoundedCornerShape(16.dp))
            .background(if (event.isExceeded) Color(0xFF211017) else Color(0xFF211A0E))
            .border(
                width = 1.15.dp,
                color = accent.copy(alpha = pulseAlpha),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(accent.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            AlertTriangleGlyph(color = accent, modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (event.isExceeded) "Monthly Cap Exceeded" else "Approaching Monthly Cap",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = detailText,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.5.sp),
                color = accent,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.28f))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            CloseGlyph(color = Color(0xFFD6DCEB), modifier = Modifier.size(9.dp))
        }
    }
}

@Composable
private fun AlertTriangleGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val tri = Path().apply {
            moveTo(size.width * 0.50f, size.height * 0.10f)
            lineTo(size.width * 0.92f, size.height * 0.86f)
            lineTo(size.width * 0.08f, size.height * 0.86f)
            close()
        }
        drawPath(
            path = tri,
            color = color,
            style = Stroke(width = 1.9.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.50f, size.height * 0.38f),
            end = Offset(size.width * 0.50f, size.height * 0.62f),
            strokeWidth = 1.9.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawCircle(
            color = color,
            radius = 1.3.dp.toPx(),
            center = Offset(size.width * 0.50f, size.height * 0.74f)
        )
    }
}

// Matching FinVault Hexagonal Shield & FV Monogram Brand Emblem
@Composable
private fun FinVaultBrandMark(
    primary: Color,
    secondary: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w * 0.5f
        val cy = h * 0.5f
        val outerR = min(w, h) * 0.48f
        val innerR = outerR * 0.89f

        fun hexPath(r: Float): Path {
            val path = Path()
            for (i in 0 until 6) {
                val rad = (-90.0 + i * 60.0) * PI / 180.0
                val x = cx + (r * cos(rad)).toFloat()
                val y = cy + (r * sin(rad)).toFloat()
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()
            return path
        }

        drawPath(
            path = hexPath(outerR),
            brush = Brush.linearGradient(listOf(primary, secondary))
        )
        drawPath(
            path = hexPath(innerR),
            color = Color(0xFF0D121E)
        )

        fun pt(nx: Float, ny: Float) = Offset(w * nx, h * ny)

        val fPath = Path().apply {
            moveTo(pt(0.32f, 0.30f).x, pt(0.32f, 0.30f).y)
            lineTo(pt(0.68f, 0.30f).x, pt(0.68f, 0.30f).y)
            lineTo(pt(0.63f, 0.39f).x, pt(0.63f, 0.39f).y)
            lineTo(pt(0.41f, 0.39f).x, pt(0.41f, 0.39f).y)
            lineTo(pt(0.41f, 0.46f).x, pt(0.41f, 0.46f).y)
            lineTo(pt(0.59f, 0.46f).x, pt(0.59f, 0.46f).y)
            lineTo(pt(0.55f, 0.54f).x, pt(0.55f, 0.54f).y)
            lineTo(pt(0.41f, 0.54f).x, pt(0.41f, 0.54f).y)
            lineTo(pt(0.41f, 0.57f).x, pt(0.41f, 0.57f).y)
            lineTo(pt(0.50f, 0.70f).x, pt(0.50f, 0.70f).y)
            lineTo(pt(0.50f, 0.79f).x, pt(0.50f, 0.79f).y)
            lineTo(pt(0.32f, 0.59f).x, pt(0.32f, 0.59f).y)
            close()
        }
        drawPath(path = fPath, color = Color(0xFFEAF7FF))

        val vWing = Path().apply {
            moveTo(pt(0.50f, 0.70f).x, pt(0.50f, 0.70f).y)
            lineTo(pt(0.61f, 0.50f).x, pt(0.61f, 0.50f).y)
            lineTo(pt(0.69f, 0.50f).x, pt(0.69f, 0.50f).y)
            lineTo(pt(0.50f, 0.79f).x, pt(0.50f, 0.79f).y)
            close()
        }
        drawPath(
            path = vWing,
            brush = Brush.verticalGradient(listOf(primary, Color(0xFF10B981)))
        )
    }
}

@Composable
private fun ChevronLeftGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(size.width * 0.68f, size.height * 0.16f)
            lineTo(size.width * 0.30f, size.height * 0.50f)
            lineTo(size.width * 0.68f, size.height * 0.84f)
        }
        drawPath(path, color = color, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
private fun ChevronRightGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(size.width * 0.32f, size.height * 0.16f)
            lineTo(size.width * 0.70f, size.height * 0.50f)
            lineTo(size.width * 0.32f, size.height * 0.84f)
        }
        drawPath(path, color = color, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
private fun VaultGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.7.dp.toPx()
        drawRoundRect(
            color = color,
            topLeft = Offset(size.width * 0.10f, size.height * 0.18f),
            size = Size(size.width * 0.80f, size.height * 0.64f),
            cornerRadius = CornerRadius(3.5.dp.toPx(), 3.5.dp.toPx()),
            style = Stroke(width = stroke)
        )
        drawLine(
            color = color,
            start = Offset(size.width * 0.10f, size.height * 0.40f),
            end = Offset(size.width * 0.90f, size.height * 0.40f),
            strokeWidth = stroke
        )
        drawCircle(
            color = color,
            radius = 1.8.dp.toPx(),
            center = Offset(size.width * 0.72f, size.height * 0.60f)
        )
    }
}

@Composable
private fun InsightsGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.8.dp.toPx()
        val path = Path().apply {
            moveTo(size.width * 0.10f, size.height * 0.74f)
            lineTo(size.width * 0.36f, size.height * 0.48f)
            lineTo(size.width * 0.58f, size.height * 0.60f)
            lineTo(size.width * 0.90f, size.height * 0.24f)
        }
        drawPath(path, color = color, style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
private fun TargetGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.6.dp.toPx()
        drawCircle(color = color, radius = size.minDimension * 0.42f, style = Stroke(width = stroke))
        drawCircle(color = color, radius = size.minDimension * 0.18f, style = Stroke(width = stroke))
    }
}

@Composable
private fun SlidersGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.6.dp.toPx()
        drawLine(color, Offset(size.width * 0.12f, size.height * 0.32f), Offset(size.width * 0.88f, size.height * 0.32f), stroke, StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.12f, size.height * 0.68f), Offset(size.width * 0.88f, size.height * 0.68f), stroke, StrokeCap.Round)
        drawCircle(color = color, radius = 2.6.dp.toPx(), center = Offset(size.width * 0.64f, size.height * 0.32f))
        drawCircle(color = color, radius = 2.6.dp.toPx(), center = Offset(size.width * 0.36f, size.height * 0.68f))
    }
}

@Composable
private fun ScaleGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.6.dp.toPx()
        drawLine(color, Offset(size.width * 0.5f, size.height * 0.16f), Offset(size.width * 0.5f, size.height * 0.84f), stroke, StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.18f, size.height * 0.34f), Offset(size.width * 0.82f, size.height * 0.34f), stroke, StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.30f, size.height * 0.84f), Offset(size.width * 0.70f, size.height * 0.84f), stroke, StrokeCap.Round)
        drawCircle(color = color, radius = 2.3.dp.toPx(), center = Offset(size.width * 0.20f, size.height * 0.54f), style = Stroke(width = stroke))
        drawCircle(color = color, radius = 2.3.dp.toPx(), center = Offset(size.width * 0.80f, size.height * 0.54f), style = Stroke(width = stroke))
    }
}

@Composable
private fun SearchGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.7.dp.toPx()
        val r = size.minDimension * 0.34f
        val c = Offset(size.width * 0.42f, size.height * 0.42f)
        drawCircle(color = color, radius = r, center = c, style = Stroke(width = stroke))
        drawLine(
            color = color,
            start = Offset(c.x + r * 0.72f, c.y + r * 0.72f),
            end = Offset(size.width * 0.88f, size.height * 0.88f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun CloseGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.7.dp.toPx()
        drawLine(color, Offset(0f, 0f), Offset(size.width, size.height), stroke, StrokeCap.Round)
        drawLine(color, Offset(size.width, 0f), Offset(0f, size.height), stroke, StrokeCap.Round)
    }
}

@Composable
private fun EyeGlyph(hidden: Boolean, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.6.dp.toPx()
        val path = Path().apply {
            moveTo(size.width * 0.08f, size.height * 0.5f)
            cubicTo(size.width * 0.30f, size.height * 0.14f, size.width * 0.70f, size.height * 0.14f, size.width * 0.92f, size.height * 0.5f)
            cubicTo(size.width * 0.70f, size.height * 0.86f, size.width * 0.30f, size.height * 0.86f, size.width * 0.08f, size.height * 0.5f)
        }
        drawPath(path, color = color, style = Stroke(width = stroke, join = StrokeJoin.Round))
        drawCircle(color = color, radius = size.minDimension * 0.14f)
        if (hidden) {
            drawLine(
                color = color,
                start = Offset(size.width * 0.16f, size.height * 0.84f),
                end = Offset(size.width * 0.84f, size.height * 0.16f),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun SparklineGlyph(color: Color, upward: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.7.dp.toPx()
        val path = Path().apply {
            if (upward) {
                moveTo(0f, size.height * 0.80f)
                lineTo(size.width * 0.35f, size.height * 0.55f)
                lineTo(size.width * 0.62f, size.height * 0.68f)
                lineTo(size.width, size.height * 0.18f)
            } else {
                moveTo(0f, size.height * 0.22f)
                lineTo(size.width * 0.35f, size.height * 0.52f)
                lineTo(size.width * 0.62f, size.height * 0.38f)
                lineTo(size.width, size.height * 0.82f)
            }
        }
        drawPath(path, color = color, style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
private fun PlusGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.9.dp.toPx()
        drawLine(color, Offset(size.width * 0.5f, 0f), Offset(size.width * 0.5f, size.height), stroke, StrokeCap.Round)
        drawLine(color, Offset(0f, size.height * 0.5f), Offset(size.width, size.height * 0.5f), stroke, StrokeCap.Round)
    }
}

@Composable
private fun ArrowDownGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.8.dp.toPx()
        drawLine(color, Offset(size.width * 0.5f, size.height * 0.14f), Offset(size.width * 0.5f, size.height * 0.84f), stroke, StrokeCap.Round)
        val path = Path().apply {
            moveTo(size.width * 0.22f, size.height * 0.56f)
            lineTo(size.width * 0.5f, size.height * 0.86f)
            lineTo(size.width * 0.78f, size.height * 0.56f)
        }
        drawPath(path, color = color, style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
private fun ArrowUpGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 1.8.dp.toPx()
        drawLine(color, Offset(size.width * 0.5f, size.height * 0.86f), Offset(size.width * 0.5f, size.height * 0.16f), stroke, StrokeCap.Round)
        val path = Path().apply {
            moveTo(size.width * 0.22f, size.height * 0.44f)
            lineTo(size.width * 0.5f, size.height * 0.14f)
            lineTo(size.width * 0.78f, size.height * 0.44f)
        }
        drawPath(path, color = color, style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}
