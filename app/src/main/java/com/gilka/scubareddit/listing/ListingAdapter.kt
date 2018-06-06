package com.gilka.scubareddit.listing

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.gilka.scubareddit.models.AdapterViewBase
import com.gilka.scubareddit.models.LoadingEntry
import com.gilka.scubareddit.models.RedditEntry
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import java.util.ArrayList


class ListingAdapter(activity: Activity, private val clickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: AdapterViewBase)
    }

    private var delegatesManager: AdapterDelegatesManager<List<AdapterViewBase>> = AdapterDelegatesManager()
    private var entries: ArrayList<AdapterViewBase>

    init {
        delegatesManager.addDelegate(EntryAdapterDelegate(activity))
        delegatesManager.addDelegate(LoadingAdapterDelegate(activity))

        entries = ArrayList()
        addLoading()
    }

    private fun addLoading() = entries.add(LoadingEntry())

    private fun removeLoading() = entries.removeAt(entries.size - 1)

    override fun getItemCount() = entries.size

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(entries, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(entries, position, holder)
        holder.itemView.setOnClickListener({
            clickListener.onItemClick(entries[position])
        })
    }


    fun setInitialEntries(initialItems: List<RedditEntry>) {
        val lastIndex = entries.size - 1
        entries.clear()
        notifyItemRangeRemoved(0, lastIndex)

        addEntries(initialItems)
    }

    fun loadMoreEntries(newEntries: List<RedditEntry>) {
        val lastIndex = entries.size - 1 // loading index
        removeLoading()
        notifyItemRemoved(lastIndex)

        addEntries(newEntries)
    }

    private fun addEntries(newEntries: List<RedditEntry>) {
        val lastIndex = entries.size - 1
        entries.addAll(newEntries)
        addLoading()
        notifyItemRangeChanged(lastIndex, entries.size + 1)
    }

    fun getCurrentEntries() : List<RedditEntry> {
        return entries
                .filter { it -> it is RedditEntry }
                .map { it as RedditEntry }
    }
}