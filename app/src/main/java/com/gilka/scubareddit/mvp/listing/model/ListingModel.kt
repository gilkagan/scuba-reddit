package com.gilka.scubareddit.mvp.listing.model

import com.gilka.scubareddit.api.RedditRestAPI
import com.gilka.scubareddit.api.RedditListingResponse
import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.mvp.listing.contracts.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListingModel : Model {

    private var savedEntries = ArrayList<RedditEntry>()
    private var savedAfter : String = ""
    private var savedFilter : String = ""

    override fun getRedditEntries(onFinishedListener: Model.OnGetEntriesFinishedListener, channel: String, afterTag: String) {

        val api = RedditRestAPI()
        val call = api.getListing(channel, afterTag, "25")
        call.enqueue(object : Callback<RedditListingResponse> {
            override fun onResponse(call: Call<RedditListingResponse>?, response: Response<RedditListingResponse>) {
                val entries = response.body().data.children.map {
                    val item = it.data
                    RedditEntry(item.title, item.thumbnail, item.url)
                }

                val after = response.body().data.after
                savedEntries.addAll(entries)
                savedAfter = after

                val filtered = filterList(entries, savedFilter)
                onFinishedListener.onFinished(filtered, after)
            }

            override fun onFailure(call: Call<RedditListingResponse>?, t: Throwable) {
                onFinishedListener.onFailure(t)
            }

        })
    }

    override fun applyFilter(onFilterFinishedListener: Model.OnFilterFinishedListener, filter: String) {
        savedFilter = filter
        val filtered = filterList(savedEntries, savedFilter)
        onFilterFinishedListener.onFinished(filtered)
    }

    private fun filterList(original : List<RedditEntry>, filter: String) : ArrayList<RedditEntry> {
        return original.filter { it -> it.title.contains(filter, true) } as ArrayList<RedditEntry>
    }
}