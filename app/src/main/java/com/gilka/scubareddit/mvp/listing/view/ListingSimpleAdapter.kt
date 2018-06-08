package com.gilka.scubareddit.mvp.listing.view

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.gilka.scubareddit.listing.EntryAdapterDelegate
import com.gilka.scubareddit.listing.LoadingAdapterDelegate
import com.gilka.scubareddit.models.AdapterItemBase
import com.gilka.scubareddit.models.RedditEntry
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import java.util.ArrayList

class ListingSimpleAdapter(activity: Activity,
                           private var entries: ArrayList<RedditEntry>,
                           private val clickListener: OnItemClickListener) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: RedditEntry)
    }

    private var delegatesManager: AdapterDelegatesManager<List<AdapterItemBase>> = AdapterDelegatesManager()
    private var allEntries: ArrayList<RedditEntry> = ArrayList()

    init {
        delegatesManager.addDelegate(EntryAdapterDelegate(activity))
        delegatesManager.addDelegate(LoadingAdapterDelegate(activity))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(entries, position, holder)
        holder.itemView.setOnClickListener({
            clickListener.onItemClick(entries[position])
        })
    }
}
