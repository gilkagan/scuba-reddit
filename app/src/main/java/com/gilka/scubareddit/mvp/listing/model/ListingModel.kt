package com.gilka.scubareddit.mvp.listing.model

import com.gilka.scubareddit.api.RedditRestAPI
import com.gilka.scubareddit.api.RedditListingResponse
import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.mvp.listing.contracts.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListingModel(private val channel: String) : Model {


    private var savedEntries = ArrayList<RedditEntry>()
    private var savedAfter : String = ""
    private var savedFilter : String = ""

    override fun getRedditEntries(onEntriesReadyListener: Model.OnEntriesReadyListener) {

        val api = RedditRestAPI()
        val call = api.getListing(channel, savedAfter, "25")
        call.enqueue(object : Callback<RedditListingResponse> {
            override fun onResponse(call: Call<RedditListingResponse>?, response: Response<RedditListingResponse>) {
                val entries = response.body().data.children.map {
                    val item = it.data
                    RedditEntry(item.title, item.thumbnail, item.url)
                }

                val after = response.body().data.after
                savedEntries.addAll(entries)
                savedAfter = after

                val filtered = filterList(savedEntries, savedFilter)
                onEntriesReadyListener.onEntriesReady(filtered)
            }

            override fun onFailure(call: Call<RedditListingResponse>?, t: Throwable) {
                onEntriesReadyListener.onFailure(t)
            }

        })
    }

    override fun refresh(onEntriesReadyListener: Model.OnEntriesReadyListener) {
        savedEntries.clear()
        savedAfter = ""
        getRedditEntries(onEntriesReadyListener)
    }

    override fun applyFilter(onEntriesReadyListener: Model.OnEntriesReadyListener, filter: String) {
        savedFilter = filter
        val filtered = filterList(savedEntries, savedFilter)
        onEntriesReadyListener.onEntriesReady(filtered)
    }

    private fun filterList(original : List<RedditEntry>, filter: String) : ArrayList<RedditEntry> {
        return original.filter { it -> it.title.contains(filter, true) } as ArrayList<RedditEntry>
    }
}