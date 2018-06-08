package com.gilka.scubareddit.listing

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.gilka.scubareddit.models.AdapterItemBase
import com.gilka.scubareddit.models.LoadingEntry
import com.gilka.scubareddit.models.RedditEntry
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import java.util.ArrayList


class ListingAdapter(activity: Activity, private val clickListener: OnItemClickListener, val usePaging : Boolean) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: AdapterItemBase)
    }

    private var delegatesManager: AdapterDelegatesManager<List<AdapterItemBase>> = AdapterDelegatesManager()
    private var entries: ArrayList<AdapterItemBase> = ArrayList()
    private var allEntries: ArrayList<AdapterItemBase> = ArrayList()
    private var filtered: Boolean = false

    init {
        delegatesManager.addDelegate(EntryAdapterDelegate(activity))
        delegatesManager.addDelegate(LoadingAdapterDelegate(activity))

        entries = ArrayList()
        addLoading()
    }

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

    private fun addLoading() {
        if (!usePaging) return
        entries.add(LoadingEntry())
        notifyItemInserted(itemCount - 1)
    }

    private fun removeLoading() {
        if (!usePaging || itemCount == 0) return
        val lastIndex = itemCount - 1
        entries.removeAt(lastIndex)
        notifyItemRemoved(lastIndex)
    }

    fun clearEntries() {
        if (itemCount == 0) return
        val count = itemCount
        entries.clear()
        notifyItemRangeRemoved(0, count)
    }

    fun loadEntries(newEntries: List<RedditEntry>) {
        removeLoading()
        val firstNewIndex = itemCount
        entries.addAll(newEntries)
        notifyItemRangeInserted(firstNewIndex, newEntries.size)
        addLoading()
    }

    fun getEntries() : ArrayList<RedditEntry> {
        return entries
                .filter { it -> it is RedditEntry }
                .map { it as RedditEntry }
        as ArrayList<RedditEntry>
    }

    fun applyFilter(constraint : CharSequence) {
        if (constraint.isBlank()) {
            entries.clear()
            entries.addAll(allEntries)
            filtered = false
        } else {
            if (!filtered) {
                allEntries.clear()
                allEntries.addAll(entries)
            }
            filtered = true
            entries = allEntries.filter { it -> it is RedditEntry && it.title.contains(constraint, true) }
                    .map { it } as ArrayList<AdapterItemBase>
        }
        notifyDataSetChanged()
    }

}