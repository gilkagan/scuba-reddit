package com.gilka.scubareddit.listing

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.gilka.scubareddit.models.AdapterViewBase
import com.gilka.scubareddit.models.LoadingEntry
import com.gilka.scubareddit.models.RedditEntry
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import java.util.ArrayList


class ListingAdapter(activity: Activity, private val clickListener: OnItemClickListener, val usePaging : Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val filterResults = Filter.FilterResults()
                if (constraint != null) {
                    val filtered = getFilteredEntries(constraint)
                    filterResults.values = filtered
                    filterResults.count = filtered.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?,
                                        results: Filter.FilterResults?) = when {
                results?.count ?: -1 > 0 -> notifyDataSetChanged()
                else -> notifyDataSetChanged()
            }
        }
    }

    fun setInitialEntries(initialItems: List<RedditEntry>) {
        val lastIndex = entries.size
        entries.clear()
        notifyItemRangeRemoved(0, lastIndex)

        addEntries(initialItems)
    }

    fun loadMoreEntries(newEntries: List<RedditEntry>) {
        val lastIndex = entries.size

        if (usePaging) {
            removeLoading()
            notifyItemRemoved(lastIndex)
        }

        addEntries(newEntries)
    }

    private fun addEntries(newEntries: List<RedditEntry>) {
        val lastIndex = entries.size
        entries.addAll(newEntries)

        var newLast = entries.size
        if (usePaging) {
            addLoading()
            newLast++
        }
        notifyItemRangeInserted(lastIndex, newLast)
    }

    fun getEntries() : List<RedditEntry> {
        return entries
                .filter { it -> it is RedditEntry }
                .map { it as RedditEntry }
    }

    private fun getFilteredEntries(constraint : CharSequence) : List<RedditEntry> {
        return entries
                .filter { it -> it is RedditEntry && it.title.contains(constraint, true) }
                .map { it as RedditEntry }
    }
}