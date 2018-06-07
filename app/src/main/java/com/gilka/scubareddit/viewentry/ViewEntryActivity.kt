package com.gilka.scubareddit.viewentry

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebViewClient
import com.gilka.scubareddit.R
import kotlinx.android.synthetic.main.activity_view_entry.*


class ViewEntryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra.url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_entry)

        val url = intent.getStringExtra(EXTRA_URL)

        wvContent.webViewClient = WebViewClient()
        wvContent.settings.javaScriptEnabled = true
        wvContent.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        wvContent.loadUrl(url)
    }
}
