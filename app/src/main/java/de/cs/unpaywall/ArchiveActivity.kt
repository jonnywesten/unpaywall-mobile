package de.cs.unpaywall;

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.Request
import org.jsoup.Jsoup
import java.net.URL

class ArchiveActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            handleSendText(intent)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let { sharedText ->
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val url = URL(sharedText)
                    val strippedUrl = "${url.protocol}://${url.host}${url.path}"
                    val archiveApiUrl = "https://archive.is/${Uri.encode(strippedUrl)}"
                    val request = Request.Builder()
                        .url(archiveApiUrl)
                        .build()

                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            val responseBody = response.body?.string()
                            responseBody?.let {
                                val linkUrls = extractArchiveLinks(it, strippedUrl)
                                if (linkUrls.isNotEmpty()) {
                                    openInBrowser(linkUrls[0])
                                } else {
                                    showError("No archived link found.")
                                }
                            }
                        } else {
                            showError("Failed to fetch the archived link. Status: ${response.code}")
                        }
                    }
                } catch (e: Exception) {
                    showError("Error fetching the archived link: ${e.message}")
                }
            }
        }
    }

    private fun extractArchiveLinks(responseBody: String, originalUrl: String): List<String> {
        val doc = Jsoup.parse(responseBody)
        val links = doc.select("a[href]")
        val originalHost = URL(originalUrl).host.replace("www.", "")

        return links.map { it.attr("href") }
            .filter { link ->
                link.startsWith("https://archive.is") &&
                        !link.contains(originalHost) &&
                        !link.contains("search/") &&
                        !link.contains(".gif") &&
                        !link.contains(".png")
            }
    }

    private fun openInBrowser(url: String) {
        runOnUiThread {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            finish()
        }
    }

    private fun showError(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            finish()
        }
    }
}