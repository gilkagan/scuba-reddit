package com.gilka.scubareddit.viewentry

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gilka.scubareddit.R
import kotlinx.android.synthetic.main.activity_view_entry.*
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebSettings





class ViewEntryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra.url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_entry)

        val url= intent.getStringExtra(EXTRA_URL)
        title = getString(R.string.activity_view)

        Log.w("xx", url)

        wvContent.webViewClient = WebViewClient(

        )

        wvContent.settings.javaScriptEnabled = true
        wvContent.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        wvContent.loadUrl(url)
    }
}
