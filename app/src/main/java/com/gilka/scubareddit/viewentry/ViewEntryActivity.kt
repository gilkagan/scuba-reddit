package com.gilka.scubareddit.viewentry

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebViewClient
import com.gilka.scubareddit.R
import kotlinx.android.synthetic.main.activity_view_entry.*
import com.gilka.scubareddit.data.DataManager
import com.gilka.scubareddit.models.RedditEntry
import android.content.Intent




class ViewEntryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ENTRY = "extra.entry"

        fun newIntent(context: Context, entry: RedditEntry): Intent {
            val intent = Intent(context, ViewEntryActivity::class.java)
            intent.putExtra(EXTRA_ENTRY, entry)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_entry)

        val entry = intent.getParcelableExtra<RedditEntry>(EXTRA_ENTRY)

        fabFavorite.setOnClickListener({ view ->
           DataManager.favoritesManager.toggleFavorite(entry)
            syncFabIcon(entry)
        })

        syncFabIcon(entry)

        wvContent.webViewClient = WebViewClient()
        wvContent.settings.javaScriptEnabled = true
        wvContent.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        wvContent.loadUrl(entry.url)
    }

    fun syncFabIcon(entry : RedditEntry) {
        var fabResource = R.drawable.ic_favorite_border_black_24dp
        if (DataManager.favoritesManager.checkFavorite(entry)) fabResource = R.drawable.ic_favorite_black_24dp
        fabFavorite.setImageDrawable(getResources().getDrawable(fabResource, theme))
    }
}
