package com.gilka.scubareddit.listing

import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.app.Activity
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.gilka.scubareddit.R
import com.gilka.scubareddit.loadImg
import com.gilka.scubareddit.models.AdapterViewBase
import com.gilka.scubareddit.models.RedditEntry
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate


class EntryAdapterDelegate(activity: Activity) : AdapterDelegate<List<AdapterViewBase>>() {

    private val inflater: LayoutInflater

    init {
        inflater = activity.layoutInflater
    }

    override fun isForViewType(items: List<AdapterViewBase>, position: Int): Boolean {
        return items[position] is RedditEntry
    }

    public override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return EntryViewHolder(inflater.inflate(R.layout.row_item_entry, parent, false))
    }

    override fun onBindViewHolder(items: List<AdapterViewBase>, position: Int,
                                  holder: RecyclerView.ViewHolder, @Nullable payloads: List<Any>) {

        val vh = holder as EntryViewHolder
        val entry = items[position] as RedditEntry

        vh.title.text = entry.title
        when (entry.thumbnail) {
            "self" -> vh.thumbnail.visibility = View.GONE
            else -> {
                vh.thumbnail.visibility = View.VISIBLE
                vh.thumbnail.loadImg(entry.thumbnail)
            }
        }
    }

    internal class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var thumbnail: ImageView

        init {
            title = itemView.findViewById(R.id.tvTitle)
            thumbnail = itemView.findViewById(R.id.ivThumb)
        }
    }
}