package com.gilka.scubareddit.listing

import android.app.Activity
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gilka.scubareddit.R
import com.gilka.scubareddit.models.AdapterViewBase
import com.gilka.scubareddit.models.LoadingEntry
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate


class LoadingAdapterDelegate(activity: Activity) : AdapterDelegate<List<AdapterViewBase>>() {

    private val inflater: LayoutInflater

    init {
        inflater = activity.layoutInflater
    }

    override fun isForViewType(items: List<AdapterViewBase>, position: Int): Boolean {
        return items[position] is LoadingEntry
    }

    public override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(inflater.inflate(R.layout.row_item_loading, parent, false))
    }

    override fun onBindViewHolder(items: List<AdapterViewBase>, position: Int,
                                  holder: RecyclerView.ViewHolder, @Nullable payloads: List<Any>) {
    }

    internal class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}